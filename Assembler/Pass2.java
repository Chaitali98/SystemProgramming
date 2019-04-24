import java.io.*;
import java.util.*;

public class Pass2 {
	private ArrayList<outputLine> IC = new ArrayList<outputLine>();
	private ArrayList<Literal> Littab = new ArrayList<Literal>();
	private HashMap<String, Integer> Symtab = new HashMap<String, Integer>();
	
	String machine_code = "out.text";
	private String outputfile = "/home/agarwal/workspace/Pass1/output.txt";
	private String Lit = "LITTAB.csv";
	private String Sym = "SYMTAB.csv";
	
	
	public void readfiles(){
		try {
			BufferedReader bin = new BufferedReader(new FileReader(Lit));
			String line;
			while((line = bin.readLine())!= null ){
				String values[] = line.split(",");
				Literal l = new Literal(values[0] , values[1]);
				Littab.add(l);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Literal i : Littab){
			System.out.println(i);
		}
		
		try {
			BufferedReader bin = new BufferedReader(new FileReader(Sym));
			String line;
			while((line = bin.readLine())!= null ){
				String values[] = line.split(",");
				Symtab.put(values[0], Integer.parseInt(values[1]));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s : Symtab.keySet() ){
			System.out.println(s + "" + Symtab.get(s));
		}
	}
	
	public void generate_machine_code(){
		try{
			FileWriter fout = new FileWriter(machine_code);
			BufferedReader bin = new BufferedReader(new FileReader(outputfile));
			String s ;
			MachineCodeLine mcode = new MachineCodeLine();
			while((s = bin.readLine())!= null){
				
				String values[] = s.split(" ");
				
				 String opcode[] = values[1].split(",");
				 opcode[0] = opcode[0].substring(1,opcode[0].length());
				 opcode[1] = opcode[1].substring(0, opcode[1].length() -1);
				 System.out.println(" " + opcode[0] + "  " + opcode[1]);
				 
				 values[2] = values[2].substring(1,values[1].length() - 1);
				 String operands[] = values[3].split(",");
				 operands[0] = operands[0].substring(1, operands[0].length());
				 operands[1] = operands[1].substring(operands[1].length() - 1);
				if(opcode[0] == "IS"){
					mcode.setaddress(values[0]);
					mcode.setopcode(opcode[1]);
					if(values[2].compareTo("null") != 0)
						mcode.setreg(values[2]);
					if(operands[0].compareToIgnoreCase("L") == 0){
						
						Literal l = Littab.get(Integer.parseInt(operands[1]));
						mcode.setLit(l.address);
					}
					else if(operands[0].equalsIgnoreCase("S")){
						mcode.setLit(String.valueOf(Symtab.keySet().toArray()[Integer.parseInt(operands[1])]));
					}
					else if(operands[0].equalsIgnoreCase("C")){
						mcode.setLit(operands[1]);
					}
				}
				else if(opcode[0].equalsIgnoreCase("AD") && opcode[1].equalsIgnoreCase("5")){
					//LTORG
					
				}
				fout.write(mcode.toString());
				fout.append(System.getProperty("line.seperator"));
			}
		}catch(Exception e){
			System.out.println("Error handling file");
		}
	}
	
	public static void main(String[] args){
		Pass2 p2 = new Pass2();
		p2.readfiles();
		p2.generate_machine_code();
		
		
		/*String a = "abc";
		String b = String.format("%4s", a).replace(' ', '0');
      String str4 = String.format("|% d|", 101);    
		
		System.out.println(b);*/
	}
}
