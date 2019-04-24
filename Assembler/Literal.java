
public class Literal {
	String Lit;
	String address;
	
	public Literal(String L, String A){
		Lit = L;
		address = A;
	}
	
	public Literal(String L){
		Lit = L;
	}
	
	public void SetAddress(String a){
		address = a;
	}
	
	public String toString(){
		
		return Lit +"   "+ address;
	}
	
	public boolean isequal(String L){
		if(L.equalsIgnoreCase(Lit))
			return true;
		return false;
	}
}
