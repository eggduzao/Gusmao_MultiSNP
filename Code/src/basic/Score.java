package basic;

/**
 * 
 * Represents the mapping Snp Combination -> Score for all possible scoring metrics
 * To be used in JSP ranking
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class Score {

	// PIA
	private String piaCorrectSnps;
	private String piaSsSnps;
	private String piaPpvNpvSnps;
	private String piaRiskSnps;
	private String piaOddsSnps;
	private String piaGiniSnps;
	private String piaApdSnps;
	private String piaOverallSnps;
	
	private String piaCorrectScore;
	private String piaSsScore;
	private String piaPpvNpvScore;
	private String piaRiskScore;
	private String piaOddsScore;
	private String piaGiniScore;
	private String piaApdScore;
	private String piaOverallScore;
	
	// MDR
	private String mdrBalancedAccSnps;
	private String mdrBalancedAccScore;
	
	// ESNP2
	private String esnp2DeltaRSnps;
	private String esnp2DeltaRScore;
	
	// MASS
	private String massGainSnps;
	private String massChiSnps;
	private String massGiniSnps;
	private String massApdSnps;
	private String massOverallSnps;
	
	private String massGainScore;
	private String massChiScore;
	private String massGiniScore;
	private String massApdScore;
	private String massOverallScore;
	
	// Getters and Setters
	
	public String getPiaCorrectSnps() {
		return piaCorrectSnps;
	}
	public void setPiaCorrectSnps(String piaCorrectSnps) {
		this.piaCorrectSnps = piaCorrectSnps;
	}
	public String getPiaSsSnps() {
		return piaSsSnps;
	}
	public void setPiaSsSnps(String piaSsSnps) {
		this.piaSsSnps = piaSsSnps;
	}
	public String getPiaPpvNpvSnps() {
		return piaPpvNpvSnps;
	}
	public void setPiaPpvNpvSnps(String piaPpvNpvSnps) {
		this.piaPpvNpvSnps = piaPpvNpvSnps;
	}
	public String getPiaRiskSnps() {
		return piaRiskSnps;
	}
	public void setPiaRiskSnps(String piaRiskSnps) {
		this.piaRiskSnps = piaRiskSnps;
	}
	public String getPiaOddsSnps() {
		return piaOddsSnps;
	}
	public void setPiaOddsSnps(String piaOddsSnps) {
		this.piaOddsSnps = piaOddsSnps;
	}
	public String getPiaGiniSnps() {
		return piaGiniSnps;
	}
	public void setPiaGiniSnps(String piaGiniSnps) {
		this.piaGiniSnps = piaGiniSnps;
	}
	public String getPiaApdSnps() {
		return piaApdSnps;
	}
	public void setPiaApdSnps(String piaApdSnps) {
		this.piaApdSnps = piaApdSnps;
	}
	public String getPiaOverallSnps() {
		return piaOverallSnps;
	}
	public void setPiaOverallSnps(String piaOverallSnps) {
		this.piaOverallSnps = piaOverallSnps;
	}
	public String getPiaCorrectScore() {
		return piaCorrectScore;
	}
	public void setPiaCorrectScore(String piaCorrectScore) {
		this.piaCorrectScore = piaCorrectScore;
	}
	public String getPiaSsScore() {
		return piaSsScore;
	}
	public void setPiaSsScore(String piaSsScore) {
		this.piaSsScore = piaSsScore;
	}
	public String getPiaPpvNpvScore() {
		return piaPpvNpvScore;
	}
	public void setPiaPpvNpvScore(String piaPpvNpvScore) {
		this.piaPpvNpvScore = piaPpvNpvScore;
	}
	public String getPiaRiskScore() {
		return piaRiskScore;
	}
	public void setPiaRiskScore(String piaRiskScore) {
		this.piaRiskScore = piaRiskScore;
	}
	public String getPiaOddsScore() {
		return piaOddsScore;
	}
	public void setPiaOddsScore(String piaOddsScore) {
		this.piaOddsScore = piaOddsScore;
	}
	public String getPiaGiniScore() {
		return piaGiniScore;
	}
	public void setPiaGiniScore(String piaGiniScore) {
		this.piaGiniScore = piaGiniScore;
	}
	public String getPiaApdScore() {
		return piaApdScore;
	}
	public void setPiaApdScore(String piaApdScore) {
		this.piaApdScore = piaApdScore;
	}
	public String getPiaOverallScore() {
		return piaOverallScore;
	}
	public void setPiaOverallScore(String piaOverallScore) {
		this.piaOverallScore = piaOverallScore;
	}
	public String getMdrBalancedAccSnps() {
		return mdrBalancedAccSnps;
	}
	public void setMdrBalancedAccSnps(String mdrBalancedAccSnps) {
		this.mdrBalancedAccSnps = mdrBalancedAccSnps;
	}
	public String getMdrBalancedAccScore() {
		return mdrBalancedAccScore;
	}
	public void setMdrBalancedAccScore(String mdrBalancedAccScore) {
		this.mdrBalancedAccScore = mdrBalancedAccScore;
	}
	public String getEsnp2DeltaRSnps() {
		return esnp2DeltaRSnps;
	}
	public void setEsnp2DeltaRSnps(String esnp2DeltaRSnps) {
		this.esnp2DeltaRSnps = esnp2DeltaRSnps;
	}
	public String getEsnp2DeltaRScore() {
		return esnp2DeltaRScore;
	}
	public void setEsnp2DeltaRScore(String esnp2DeltaRScore) {
		this.esnp2DeltaRScore = esnp2DeltaRScore;
	}
	public String getMassGainSnps() {
		return massGainSnps;
	}
	public void setMassGainSnps(String massGainSnps) {
		this.massGainSnps = massGainSnps;
	}
	public String getMassChiSnps() {
		return massChiSnps;
	}
	public void setMassChiSnps(String massChiSnps) {
		this.massChiSnps = massChiSnps;
	}
	public String getMassGiniSnps() {
		return massGiniSnps;
	}
	public void setMassGiniSnps(String massGiniSnps) {
		this.massGiniSnps = massGiniSnps;
	}
	public String getMassApdSnps() {
		return massApdSnps;
	}
	public void setMassApdSnps(String massApdSnps) {
		this.massApdSnps = massApdSnps;
	}
	public String getMassOverallSnps() {
		return massOverallSnps;
	}
	public void setMassOverallSnps(String massOverallSnps) {
		this.massOverallSnps = massOverallSnps;
	}
	public String getMassGainScore() {
		return massGainScore;
	}
	public void setMassGainScore(String massGainScore) {
		this.massGainScore = massGainScore;
	}
	public String getMassChiScore() {
		return massChiScore;
	}
	public void setMassChiScore(String massChiScore) {
		this.massChiScore = massChiScore;
	}
	public String getMassGiniScore() {
		return massGiniScore;
	}
	public void setMassGiniScore(String massGiniScore) {
		this.massGiniScore = massGiniScore;
	}
	public String getMassApdScore() {
		return massApdScore;
	}
	public void setMassApdScore(String massApdScore) {
		this.massApdScore = massApdScore;
	}
	public String getMassOverallScore() {
		return massOverallScore;
	}
	public void setMassOverallScore(String massOverallScore) {
		this.massOverallScore = massOverallScore;
	}
}
