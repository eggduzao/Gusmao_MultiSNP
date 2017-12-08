package basic;

/**
 * 
 * Represents a contingency table:
 * |True Positive (TP)      False Positive (FP)|
 * |False Negative (FN)     True Negative (TN) |
 * 
 * @author egg
 *
 */
public class ContingencyTable {

	// Basic Elements
	private double tp;
	private double fp;
	private double tn;
	private double fn;
	
	/**
	 * Increase Functions
	 */
	public void incTP(){
		this.tp++;
	}
	public void incTP(double value){
		this.tp+=value;
	}
	public void incFP(){
		this.fp++;
	}
	public void incFP(double value){
		this.fp+=value;
	}
	public void incTN(){
		this.tn++;
	}
	public void incTN(double value){
		this.tn+=value;
	}
	public void incFN(){
		this.fn++;
	}
	public void incFN(double value){
		this.fn+=value;
	}
	
	/**
	 * Statistics
	 * @return double
	 */
	public double correctRate(){
		return (tp+tn)/(tp+tn+fp+fn);
	}
	public double sensitivity(){
		return tp/(tp+fn);
	}
	public double specificity(){
		return tn/(fp+tn);
	}
	public double ppv(){
		return tp/(tp+fp);
	}
	public double npv(){
		return tn/(tn+fn);
	}
	public double riskRatio(){
		return (tp*(fp+tn))/(fp*(tp+fn));
	}
	public double oddsRatio(){
		return (tp*tn)/(fp*fn);
	}
	public double balancedAccuracy(){
		return (this.sensitivity()+this.specificity())/2;
	}
	
	/**
	 * Passes all the values of cont to this
	 * @param cont
	 */
	public void accumulate(ContingencyTable cont){
		this.tp += cont.getTp();
		this.tn += cont.getTn();
		this.fp += cont.getFp();
		this.fn += cont.getFn();
	}
	
	public double size(){
		return tp+fp+tn+fn;
	}
	
	/**
	 * Returns the printable version of Contingency Table
	 * @return String
	 */
	@Override
	public String toString(){
		return "Contingency Table:\nTP="+tp+" FP="+fp+"\nFN="+fn+" TN="+tn+"\n";
	}
	
	// Getters and Setters
	
	public double getTp() {
		return tp;
	}
	public double getFp() {
		return fp;
	}
	public double getTn() {
		return tn;
	}
	public double getFn() {
		return fn;
	}
	public void setTp(double tp) {
		this.tp = tp;
	}
	public void setFp(double fp) {
		this.fp = fp;
	}
	public void setTn(double tn) {
		this.tn = tn;
	}
	public void setFn(double fn) {
		this.fn = fn;
	}
	
	/* Testing Main
	public static void main(String[] args) {
		
		ContingencyTable cont = new ContingencyTable();
		cont.incTP(); cont.incTP(); cont.incTP(); cont.incTP(); cont.incTP(); cont.incTP();
		cont.incFP(); cont.incFP(); cont.incFP();
		cont.incTN(); cont.incTN(); cont.incTN(); cont.incTN();
		cont.incFN(); cont.incFN(); cont.incFN(); cont.incFN(); cont.incFN();
		
		System.out.println(cont.toString());
		System.out.println(cont.toString());
		
		System.out.println("%Correct = "+cont.correctRate());
		System.out.println("Sens+Spec = "+(cont.sensitivity()+cont.specificity()));
		System.out.println("Ppv+Npv = "+(cont.ppv()+cont.npv()));
		System.out.println("Risk Ratio = "+cont.riskRatio());
		System.out.println("Odds Ratio = "+cont.oddsRatio());
		
		System.out.println("Balanced Acc = "+cont.balancedAccuracy());
		
	}
	*/
	
}
