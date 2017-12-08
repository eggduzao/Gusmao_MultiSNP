package bean;

import java.util.ArrayList;

import javax.faces.context.FacesContext;

import constants.MethodConstant;
import constants.SystemConstant;

import basic.Score;
import basic.Tuple;

/**
 * 
 * JSF Interface between JSP code and Java code representing the result screens
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class ResultBean {

	/********************************
	 ******* RESULTS
	 *******************************/

	// General
	private ArrayList<Tuple> generalParamList;

	// PIA
	private ArrayList<Score> piaResultList;
	private ArrayList<Tuple> piaParamList;
	private ArrayList<Tuple> generalPlusPiaParamList;
	private ArrayList<Tuple> piaTimeList;
	private ArrayList<Score> piaPathwayList;

	// MDR
	private ArrayList<Score> mdrResultList;
	private ArrayList<Tuple> mdrParamList;
	private ArrayList<Tuple> generalPlusMdrParamList;
	private ArrayList<Tuple> balancedAccTest;
	private double meanBalancedAccTrain;
	private double meanBalancedAccTest;
	private ArrayList<Tuple> mdrTimeList;
	private ArrayList<Score> mdrPathwayList;

	// ESNP2
	private ArrayList<Score> esnp2ResultList;
	private ArrayList<Tuple> esnp2ParamList;
	private ArrayList<Tuple> generalPlusEsnp2ParamList;
	private ArrayList<Tuple> esnp2TimeList;
	private ArrayList<Score> esnp2PathwayList;
	
	// MASS
	private ArrayList<Score> massResultList;
	private ArrayList<Tuple> massParamList;
	private ArrayList<Tuple> generalPlusMassParamList;
	private ArrayList<Tuple> massTimeList;
	private ArrayList<Score> massPathwayList;

	// Comparative
	private ArrayList<Score> comparativeResultList;
	private ArrayList<Score> comparativePathwayList;

	/********************************
	 ******* AUXILIARY
	 *******************************/

	private String method;
	private boolean showPathwayAnalysis;
	private String realPath;
	
	public String returnToAnalysis(){
		String ret = "";
		if(this.method.equals(MethodConstant.PIA)){
			ret = "returnPia";
		}
		else if(this.method.equals(MethodConstant.MDR)){
			ret = "returnMdr";
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			ret = "returnEsnp2";
		}
		else if(this.method.equals(MethodConstant.MASS)){
			ret = "returnMass";
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			ret = "returnComparative";
		}
		return ret;
	}
	
	public String downloadXls(){
		
		DownloadOperation.downloadFile(SystemConstant.DEFAULT_FILE_ANALYSIS_XLS,this.realPath,"xls",FacesContext.getCurrentInstance());
		
		return "";
	}
	
	public String downloadTxt(){
		
		DownloadOperation.downloadFile(SystemConstant.DEFAULT_FILE_ANALYSIS_TXT,this.realPath,"txt",FacesContext.getCurrentInstance());
		
		return "";
	}

	// Getters and Setters
	
	public ArrayList<Tuple> getGeneralParamList() {
		return generalParamList;
	}

	public void setGeneralParamList(ArrayList<Tuple> generalParamList) {
		this.generalParamList = generalParamList;
	}

	public ArrayList<Tuple> getPiaParamList() {
		return piaParamList;
	}

	public void setPiaParamList(ArrayList<Tuple> piaParamList) {
		this.piaParamList = piaParamList;
	}

	public ArrayList<Tuple> getMdrParamList() {
		return mdrParamList;
	}

	public void setMdrParamList(ArrayList<Tuple> mdrParamList) {
		this.mdrParamList = mdrParamList;
	}

	public ArrayList<Tuple> getEsnp2ParamList() {
		return esnp2ParamList;
	}

	public void setEsnp2ParamList(ArrayList<Tuple> esnp2ParamList) {
		this.esnp2ParamList = esnp2ParamList;
	}

	public ArrayList<Tuple> getGeneralPlusPiaParamList() {
		return generalPlusPiaParamList;
	}

	public void setGeneralPlusPiaParamList(ArrayList<Tuple> generalPlusPiaParamList) {
		this.generalPlusPiaParamList = generalPlusPiaParamList;
	}

	public ArrayList<Tuple> getGeneralPlusMdrParamList() {
		return generalPlusMdrParamList;
	}

	public void setGeneralPlusMdrParamList(ArrayList<Tuple> generalPlusMdrParamList) {
		this.generalPlusMdrParamList = generalPlusMdrParamList;
	}

	public ArrayList<Tuple> getGeneralPlusEsnp2ParamList() {
		return generalPlusEsnp2ParamList;
	}

	public void setGeneralPlusEsnp2ParamList(
			ArrayList<Tuple> generalPlusEsnp2ParamList) {
		this.generalPlusEsnp2ParamList = generalPlusEsnp2ParamList;
	}

	public ArrayList<Score> getPiaResultList() {
		return piaResultList;
	}

	public void setPiaResultList(ArrayList<Score> piaResultList) {
		this.piaResultList = piaResultList;
	}

	public ArrayList<Score> getMdrResultList() {
		return mdrResultList;
	}

	public void setMdrResultList(ArrayList<Score> mdrResultList) {
		this.mdrResultList = mdrResultList;
	}

	public ArrayList<Tuple> getBalancedAccTest() {
		return balancedAccTest;
	}

	public void setBalancedAccTest(ArrayList<Tuple> balancedAccTest) {
		this.balancedAccTest = balancedAccTest;
	}

	public double getMeanBalancedAccTrain() {
		return meanBalancedAccTrain;
	}

	public void setMeanBalancedAccTrain(double meanBalancedAccTrain) {
		this.meanBalancedAccTrain = meanBalancedAccTrain;
	}

	public double getMeanBalancedAccTest() {
		return meanBalancedAccTest;
	}

	public void setMeanBalancedAccTest(double meanBalancedAccTest) {
		this.meanBalancedAccTest = meanBalancedAccTest;
	}

	public ArrayList<Score> getEsnp2ResultList() {
		return esnp2ResultList;
	}

	public void setEsnp2ResultList(ArrayList<Score> esnp2ResultList) {
		this.esnp2ResultList = esnp2ResultList;
	}

	public ArrayList<Score> getComparativeResultList() {
		return comparativeResultList;
	}

	public void setComparativeResultList(ArrayList<Score> comparativeResultList) {
		this.comparativeResultList = comparativeResultList;
	}

	public ArrayList<Tuple> getPiaTimeList() {
		return piaTimeList;
	}

	public void setPiaTimeList(ArrayList<Tuple> piaTimeList) {
		this.piaTimeList = piaTimeList;
	}

	public ArrayList<Tuple> getMdrTimeList() {
		return mdrTimeList;
	}

	public void setMdrTimeList(ArrayList<Tuple> mdrTimeList) {
		this.mdrTimeList = mdrTimeList;
	}

	public ArrayList<Tuple> getEsnp2TimeList() {
		return esnp2TimeList;
	}

	public void setEsnp2TimeList(ArrayList<Tuple> esnp2TimeList) {
		this.esnp2TimeList = esnp2TimeList;
	}

	public ArrayList<Score> getPiaPathwayList() {
		return piaPathwayList;
	}

	public void setPiaPathwayList(ArrayList<Score> piaPathwayList) {
		this.piaPathwayList = piaPathwayList;
	}

	public ArrayList<Score> getMdrPathwayList() {
		return mdrPathwayList;
	}

	public void setMdrPathwayList(ArrayList<Score> mdrPathwayList) {
		this.mdrPathwayList = mdrPathwayList;
	}

	public ArrayList<Score> getEsnp2PathwayList() {
		return esnp2PathwayList;
	}

	public void setEsnp2PathwayList(ArrayList<Score> esnp2PathwayList) {
		this.esnp2PathwayList = esnp2PathwayList;
	}

	public ArrayList<Score> getComparativePathwayList() {
		return comparativePathwayList;
	}

	public void setComparativePathwayList(ArrayList<Score> comparativePathwayList) {
		this.comparativePathwayList = comparativePathwayList;
	}

	public boolean isShowPathwayAnalysis() {
		return showPathwayAnalysis;
	}

	public void setShowPathwayAnalysis(boolean showPathwayAnalysis) {
		this.showPathwayAnalysis = showPathwayAnalysis;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public ArrayList<Score> getMassResultList() {
		return massResultList;
	}

	public void setMassResultList(ArrayList<Score> massResultList) {
		this.massResultList = massResultList;
	}

	public ArrayList<Tuple> getMassParamList() {
		return massParamList;
	}

	public void setMassParamList(ArrayList<Tuple> massParamList) {
		this.massParamList = massParamList;
	}

	public ArrayList<Tuple> getGeneralPlusMassParamList() {
		return generalPlusMassParamList;
	}

	public void setGeneralPlusMassParamList(
			ArrayList<Tuple> generalPlusMassParamList) {
		this.generalPlusMassParamList = generalPlusMassParamList;
	}

	public ArrayList<Tuple> getMassTimeList() {
		return massTimeList;
	}

	public void setMassTimeList(ArrayList<Tuple> massTimeList) {
		this.massTimeList = massTimeList;
	}

	public ArrayList<Score> getMassPathwayList() {
		return massPathwayList;
	}

	public void setMassPathwayList(ArrayList<Score> massPathwayList) {
		this.massPathwayList = massPathwayList;
	}
}
