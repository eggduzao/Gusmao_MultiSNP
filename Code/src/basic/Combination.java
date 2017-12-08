package basic;

/**
 * 
 * Represents the mapping: SNP combination -> Score
 * To be used as basic constructive element of priority queue
 * 
 * @author egg
 *
 */
public class Combination {

	// Basic Elements
	private String[] snps;
	private double score;
	private int entry;
	
	public Combination(String[] snps, double score, int entry){
		this.snps = snps;
		this.score = score;
		this.entry = entry;
	}
	
	/**
	 * Creates a new Combination Object.
	 */
	@Override
	public Combination clone(){
		String[] snps = null;
		if(this.snps!=null) snps = this.snps.clone();
		return new Combination(snps,this.score,this.entry);
	}
	
	// Getters and Setters
	
	public String getSnp(int index){
		return this.snps[index];
	}
	
	public String[] getWholeSnps() {
		return snps;
	}

	public double getScore() {
		return score;
	}
	
	public int getEntry(){
		return this.entry;
	}

	public void addSnp(String snp, int index){
		this.snps[index] = snp;
	}
	
	public void setSnps(String[] snps) {
		this.snps = snps;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setEntry(int entry){
		this.entry = entry;
	}
	
}
