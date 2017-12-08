package basic;

/**
 * 
 * Represents the mappings:
 * 	 	a) Snp Combination -> Score
 * 		b) Parameter -> Value
 * To be used in JSP to return a correct vector without nulls and with correct ntop
 * To be used in JSP to write all results
 * 
 * @author egg
 *
 */
public class Tuple {

	// Basic Elements
	private String t1;
	private String t2;
	
	public Tuple(String t1, String t2){
		this.t1 = t1;
		this.t2 = t2;
	}
	
	/**
	 * Clones Tuple Object
	 * @return Tuple clone
	 */
	@Override
	public Tuple clone(){
		return new Tuple(this.t1,this.t2);
	}
	
	// Getters and Setters

	public String getT1() {
		return t1;
	}

	public String getT2() {
		return t2;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}
	
}
