import java.io.*;
import java.util.*;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import com.google.common.collect.ArrayListMultimap;

public class Driver {
	
	private String motfile = "/home/agarwal/workspace/Pass1/mot.csv";
	private String codefile = "/home/agarwal/workspace/Pass1/code";
	private String outputfile = "/home/agarwal/workspace/Pass1/output.txt";
	
	private LinkedHashMap<String, ArrayList<String>> IS = new LinkedHashMap<String, ArrayList<String>>();
	private ArrayList<String> REG = new ArrayList<String>();
	private ArrayList<Literal> LITTAB = new ArrayList<Literal>();
	
	private ArrayListMultimap<String, Integer> Lit = ArrayListMultimap.create();  //literal-address
	private LinkedHashMap<String , Integer> Sym = new LinkedHashMap();  //symbol-address
	private ArrayList<Integer> Pool = new ArrayList<Integer>(); //indexes
	private ArrayList<String> unassignedLiterals = new ArrayList<String>(); 
	private ArrayList<String> equConditions = new ArrayList<String>();
	
	
	public void Initialize(){
		ReadFile(motfile , IS);
		
		//Initialize REG array
		
		REG.add("AREG");
		REG.add("BREG");
		REG.add("CREG");
		REG.add("DREG");
		
		equConditions.add("LT");
		equConditions.add("LE");
		equConditions.add("EQ");
		equConditions.add("GT");
		equConditions.add("GE");
		equConditions.add("ANY");
	}
	
	
	public void ReadFile(String filename , Map<String, ArrayList<String>> IS){
		//mot table
		try {
			BufferedReader fin = new BufferedReader(new FileReader(filename));
			String line;
			while((line = fin.readLine()) != null){
				String[] cols = line.split(",");
				ArrayList<String> values = new ArrayList<String>();
				values.add(cols[1]);
				values.add(cols[2]);
				values.add(cols[3]);
				IS.put(cols[0] , values);
			}
			
			System.out.println("----------MOT TABLE----------");
			for(String s : IS.keySet()){
				System.out.println(s + " " + IS.get(s));
			}
			System.out.println("-----------------------------");
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
		
	public Integer addLabelAndMnemonic(String[] inputline,outputLine o , Integer loc_ptr){
		int word_counter = 0;
		ArrayList<String> mnemonic = IS.get(inputline[0]);
		if(mnemonic == null){
			//label mnemonic
			//add label in symbol table
			//Sym.computeIfAbsent(, arg1)
			Sym.put(inputline[0], loc_ptr);
			word_counter = 1;
			mnemonic = IS.get(inputline[1]);
		}
		String classtype = mnemonic.get(0);
		//System.out.println(classtype);
		o.setmnemonic(inputline[word_counter]);
		o.setaddress(loc_ptr);
		o.setcode(classtype,Integer.parseInt(mnemonic.get(1)));
		if(classtype.equalsIgnoreCase("IS")){
			loc_ptr += Integer.parseInt(mnemonic.get(2));			
			}
		else if(classtype.equalsIgnoreCase("DL")){
			System.out.println(mnemonic);
			System.out.println("word_counter = " + word_counter);
			if(inputline[word_counter].equalsIgnoreCase("DS")){
				loc_ptr += Integer.parseInt(inputline[word_counter + 1]);
			}
			else
				loc_ptr += Integer.parseInt(mnemonic.get(2));
		}
		else if(classtype.equalsIgnoreCase("AD"))
			o.setaddress(0);
		return loc_ptr;
	}

	public Integer handleLTORG(Integer loc_ptr){
		for( String s: unassignedLiterals){
			Lit.put(s , loc_ptr);
			LITTAB.add(new Literal(s, String.valueOf(loc_ptr++)));
		}
		unassignedLiterals.clear();
		
		Pool.add(Lit.size()+1);
		return loc_ptr;
	}
	public Integer evalExpression(String exp){
		Integer ans = new Integer(0);
			String op[] = {"+" , "-" , "*"};
			int pos = -1;
			int operation = -1;
			int i=0;
			for(;i<3;i++){
				if(exp.contains(op[i])){
					pos = exp.indexOf(op[i]);
					operation = i;
					break;
				}
			}
			//System.out.println("\npos = " + pos + " operation = " + operation);
			if(pos != -1){
				String start = exp.substring(0 , pos);
				String end = exp.substring(pos+1, exp.length());
				int firstOperand, secondOperand;
				if(Sym.containsKey(start)){
					firstOperand = Sym.get(start);
				}else{
					firstOperand = Integer.parseInt(start);
				}
				if(Sym.containsKey(end)){
					secondOperand = Sym.get(end);
				}else{
					secondOperand = Integer.parseInt(end);
				}
				//System.out.println("\noperands = " + firstOperand + " " + secondOperand);
				switch(i){
				case 0: ans = firstOperand + secondOperand;
					break;
				case 1: ans = firstOperand - secondOperand;
					break;
				case 2: ans = firstOperand * secondOperand;
					break;
				}
			}
		return ans;
	}
	public void parsefile() throws IOException{
		BufferedReader code;
		FileWriter fout ;
		String lineseperator = System.getProperty("line.separator");
		
		try {
			code = new BufferedReader(new FileReader(codefile));
			fout = new FileWriter(outputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot open source files");
			return;
		}
		//label mnemonic register mem-operand
		//address (class,code) (reg) (type,value)
		Integer loc_ptr = new Integer(0);
		Integer lit_ptr = 0;
		String line;
		
		Pool.add(1);
		
		while((line = code.readLine()) != null){
			System.out.println();
			
			System.out.println("line = " + line);
			String[] values = line.split(" ");
			outputLine output = new outputLine();
			loc_ptr = addLabelAndMnemonic(values, output , loc_ptr);
			
			if(output.code.equals("AD")){
				
				String directive = output.mnemonic;
				if(directive.equalsIgnoreCase("LTORG")){
					loc_ptr = handleLTORG(loc_ptr);
				}
				else if(directive.equalsIgnoreCase("END")){
					loc_ptr = handleLTORG(loc_ptr);
					System.out.println("IM: " + output.getline());
					fout.write(output.getline());
					fout.flush();
					fout.close();
					printtables();
					return;
				}
				else if(directive.equalsIgnoreCase("ORIGIN") || directive.equalsIgnoreCase("START")){
					String origin_operand = values[1];
					try{
					Integer operand_value = Integer.valueOf(origin_operand);
					loc_ptr = operand_value;
					output.setsymbol("C", operand_value);
					}catch(NumberFormatException e){
						//expression
						 int value = evalExpression(values[1]);
							//System.out.println("operand value for origin = " + value);
						 loc_ptr = value;
						 output.setsymbol("C", value);
					}
				}
				else if(directive.equalsIgnoreCase("EQU")){
					String value_operand = values[2];
					int index = Sym.get(value_operand);
				}
			}else if(output.code.equals("IS")){
				String operand = values[values.length -1];
				//System.out.println(operand);
				
				String[] operands = operand.split(",");
				for(String op : operands ){
					if(REG.contains(op)){
						//register operand 
						output.setregister(REG.indexOf(op) + 1);
					}
					else if(equConditions.contains(op)){
						output.setregister(equConditions.indexOf(op)+1);	
					}
					else if(op.contains("'=")){
						//literals
						unassignedLiterals.add(op);
						//System.out.println("found literal, value = " + op);
						output.setsymbol("L", lit_ptr++);
					}
					else{
						//symbols or constants
						try{
						//constant
						Integer con = Integer.parseInt(op);
						output.setsymbol("C", con);
						}
						catch(NumberFormatException e){
							//symbol
						if(Sym.containsKey(op)){
							int pos = new ArrayList<String>(Sym.keySet()).indexOf(op);
							output.setsymbol("S", pos);
						}else{
						Sym.putIfAbsent(op, 0);
						output.setsymbol("S", Sym.size() -1);
						}	
					}
				}
				
			}
			
		}else if(output.code.equalsIgnoreCase("DL")){
			System.out.println(output.getline());
		}
		System.out.println("IM: " + output.getline());
		fout.write(output.getline());
		fout.append(lineseperator);
	}
}

	public void printtables(){
		System.out.println("\n\n-------SYMTABLE-----------");
		for(String s : Sym.keySet()){
			System.out.println(s + " -- " + Sym.get(s).toString());
		}
		
		System.out.println("\n\n-------LITTABLE-----------");
		
		for (Literal i : LITTAB){
			System.out.println(i.toString());
		}
		
		System.out.println("\n\n-------POOLTABLE-----------");
		for(Integer i : Pool){
			System.out.println(i);
		}
					
		System.out.println("\n\n-------intermediate code-------");
		try {
			BufferedReader fin = new BufferedReader(new FileReader(outputfile));
			String line;
			
			while((line = fin.readLine()) != null){
				System.out.println(line);
			}
			
			fin.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot open output file");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error parsing output file");
		}
	}
	public static void main(String[] args){
		Driver d = new Driver();
		d.Initialize();
		try {
			d.parsefile();
			d.writeToFiles();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private void writeToFiles() {
		// TODO Auto-generated method stub
		String lineseperator = System.getProperty("line.separator");

		try{
		FileWriter fout = new FileWriter("LITTAB.csv");
			for(Literal i: LITTAB){
				fout.write(i.Lit+"," + i.address);
				fout.append(lineseperator);
			}
		fout.close();
		}catch(Exception e){
			System.out.println("error writing to files");
		}
		
		try{
			FileWriter fout = new FileWriter("SYMTAB.csv");
				for(String s: Sym.keySet()){
					fout.write( s +"," + Sym.get(s));
					fout.append(lineseperator);
				}
			fout.close();
			}catch(Exception e){
				System.out.println("error writing to files");
			}

	}
}
