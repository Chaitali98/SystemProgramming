
public class MachineCodeLine {
	String address;
	String opcode;
	String reg;
	String Litaddress;
	
	public void setaddress(String address){
		this.address = address;
	}
	public void setopcode(String op){
		opcode = op;
	}
	public void setreg(String reg){
		this.reg = reg;
	}
	public void setLit(String Lit){
		Litaddress = Lit;
	}
	
	public String toString(){
		String ret;
		
			ret = address + " " + String.format("%2s", opcode).replace(' ', '0') + " " + reg + " " + String.format("%3s", Litaddress ).replace(' ', '0');
		return ret;
		
	}
}
