
public class outputLine {
	Integer address;
	String code;
	Integer machine_opcode;
	Integer reg_cond;
	String symbol_type;
	Integer symbol_index;
	String mnemonic;
	
	public void setmnemonic(String s){mnemonic = s;}
	public void setaddress(int a){ address = new Integer(a);}
	public void setcode(String S , int machine_c){ code = S; machine_opcode = new Integer( machine_c);}
	public void setregister(int r){ reg_cond = new Integer(r);}
	public void setsymbol(String sym , int index){ symbol_type = sym;symbol_index = new Integer(index);}
	public String getline(){
		String outline ="" +  address + " (" + code + "," + machine_opcode + ") (" + reg_cond + ") (" + symbol_type + "," + symbol_index + ")";
		return outline;
	}
}
