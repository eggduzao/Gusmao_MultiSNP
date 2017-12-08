
package bean;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import jxl.write.WriteException;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import util.Operation;

import constants.MethodConstant;
import constants.SystemConstant;

import basic.ResultSet;
import basic.Score;
import basic.Time;
import basic.Tuple;

import analysis.Parameter;
import analysis.PathwayAnalysis;
import analysis.SingleAnalysis;

/**
 * 
 * JSF Interface between JSP code and Java code representing the analysis screens
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class AnalysisBean {

	/********************************
	 ******* PARAMETERS
	 *******************************/

	// Operational parameters

	private UploadedFile upFile;
	private String data;
	private boolean usePathway;
	private int order;
	private int top;
	private String outputMethod;
	private String email;

	// PIA parameters
	private int shufflePia;
	private double fractPia;
	private int ntimePia;
	private double ratslPia;
	private boolean ifractPia;
	private boolean itrainPia;
	private String lootrPia;

	// MDR parameters
	private int shuffleMdr;
	private double fractMdr;
	private int ntimeMdr;
	private double thresholdMdr;

	// ESNP2 parameters
	//private int shuffleEsnp2; ESNP2 do not contain this parameter
	
	// MASS parameters
	private double ratslMass;
	private boolean simpleAssignMass;
	private boolean useGainMass;
	private boolean useChiMass;
	private boolean useGiniMass;
	private boolean useApdMass;

	/********************************
	 ******* RESULTS
	 *******************************/

	// General
	private ArrayList<Tuple> generalParamList;

	// PIA
	private ArrayList<Tuple> piaParamList;
	private ArrayList<Tuple> generalPlusPiaParamList;
	private ArrayList<Tuple> correct;
	private ArrayList<Tuple> ss;
	private ArrayList<Tuple> ppvnpv;
	private ArrayList<Tuple> risk;
	private ArrayList<Tuple> odds;
	private ArrayList<Tuple> gini;
	private ArrayList<Tuple> apd;
	private ArrayList<Tuple> overall;
	private ArrayList<Tuple> piaTimeList;
	private ArrayList<Score> piaPathwayList;

	// MDR
	private ArrayList<Tuple> mdrParamList;
	private ArrayList<Tuple> generalPlusMdrParamList;
	private ArrayList<Tuple> balancedAccTrain;
	private ArrayList<Tuple> balancedAccTest;
	private double meanBalancedAccTrain;
	private double meanBalancedAccTest;
	private ArrayList<Tuple> mdrTimeList;
	private ArrayList<Score> mdrPathwayList;

	// ESNP2
	private ArrayList<Tuple> esnp2ParamList;
	private ArrayList<Tuple> generalPlusEsnp2ParamList;
	private ArrayList<Tuple> deltaR;
	private ArrayList<Tuple> esnp2TimeList;
	private ArrayList<Score> esnp2PathwayList;
	
	// MASS
	private ArrayList<Tuple> massParamList;
	private ArrayList<Tuple> generalPlusMassParamList;
	private ArrayList<Tuple> gainMass;
	private ArrayList<Tuple> chiMass;
	private ArrayList<Tuple> giniMass;
	private ArrayList<Tuple> apdMass;
	private ArrayList<Tuple> overallMass;
	private ArrayList<Tuple> massTimeList;
	private ArrayList<Score> massPathwayList;

	// Comparative
	private ArrayList<Score> comparativePathwayList; 

	// Compendium
	private ArrayList<Tuple>[] parameterList;
	private ArrayList<Tuple>[] resultList;
	private ArrayList<Tuple>[] timeList;
	private ArrayList<Score> pathwayList;

	/********************************
	 ******* BEANS
	 *******************************/

	private ResultBean resultBean;
	private DownloadBean downloadBean;
	private EmailBean emailBean;

	/********************************
	 ******* AUXILIARY
	 *******************************/

	private String method;
	private String ret;
	private String realPath;
	private final DecimalFormat FORMAT = new DecimalFormat("##0.000000");

	public String getInit(){

		// Default Parameters
		this.usePathway = false;
		this.order = 2;
		this.top = 20;
		this.outputMethod = SystemConstant.OUTPUT_SCREEN;
		this.shufflePia = 1000;
		this.fractPia = 0.1;
		this.ntimePia = 10;
		this.ratslPia = 1.4;
		this.ifractPia = false;
		this.itrainPia = false;
		this.lootrPia = "Maximum Rules";
		this.shuffleMdr = 1000;
		this.fractMdr = 0.1;
		this.ntimeMdr = 10;
		this.thresholdMdr = 1.0;
		//this.shuffleEsnp2 = 1000;
		this.ratslMass = 1.4;
		this.simpleAssignMass = true;
		this.useGainMass = true;
		this.useChiMass = true;
		this.useGiniMass = true;
		this.useApdMass = true;
		this.email = "";

		// Creating Parameter Lists
		// In this case Tuple represents the mapping: Parameter -> Value
		this.generalParamList = new ArrayList<Tuple>();
		this.piaParamList = new ArrayList<Tuple>();
		this.mdrParamList = new ArrayList<Tuple>();
		this.esnp2ParamList = new ArrayList<Tuple>();
		this.massParamList = new ArrayList<Tuple>();
		this.generalPlusPiaParamList = new ArrayList<Tuple>();
		this.generalPlusMdrParamList = new ArrayList<Tuple>();
		this.generalPlusEsnp2ParamList = new ArrayList<Tuple>();
		this.generalPlusMassParamList = new ArrayList<Tuple>();

		// Creating Result Lists
		// In this case Tuple represents the mapping: Snp Combination -> Score
		this.correct = new ArrayList<Tuple>();
		this.ss = new ArrayList<Tuple>();
		this.ppvnpv = new ArrayList<Tuple>();
		this.risk = new ArrayList<Tuple>();
		this.odds = new ArrayList<Tuple>();
		this.gini = new ArrayList<Tuple>();
		this.apd = new ArrayList<Tuple>();
		this.overall = new ArrayList<Tuple>();
		this.balancedAccTrain = new ArrayList<Tuple>();
		this.balancedAccTest = new ArrayList<Tuple>();
		this.deltaR = new ArrayList<Tuple>();
		this.gainMass = new ArrayList<Tuple>();
		this.chiMass = new ArrayList<Tuple>();
		this.giniMass = new ArrayList<Tuple>();
		this.apdMass = new ArrayList<Tuple>();
		this.overallMass = new ArrayList<Tuple>();

		// Creating Time Lists
		// In this case Tuple represents the mapping: Algorithm Session -> Time
		this.piaTimeList = new ArrayList<Tuple>();
		this.mdrTimeList = new ArrayList<Tuple>();
		this.esnp2TimeList = new ArrayList<Tuple>();
		this.massTimeList = new ArrayList<Tuple>();

		// Creating Pathway Lists
		this.piaPathwayList = new ArrayList<Score>();
		this.mdrPathwayList = new ArrayList<Score>();
		this.esnp2PathwayList = new ArrayList<Score>();
		this.massPathwayList = new ArrayList<Score>();

		return "";
	}

	public String result(){

		// Method to be performed
		FacesContext context = FacesContext.getCurrentInstance();
		String requestPath = (String) context.getExternalContext().getRequestPathInfo();
		if(requestPath.contains("pia")) this.method = MethodConstant.PIA;
		else if(requestPath.contains("mdr")) this.method = MethodConstant.MDR;
		else if(requestPath.contains("esnp2")) this.method = MethodConstant.ESNP2;
		else if(requestPath.contains("mass")) this.method = MethodConstant.MASS;
		else this.method = MethodConstant.COMPARATIVE;
		
		// Real Path association
		FacesContext facesContextInstance = FacesContext.getCurrentInstance();
		ServletContext facesContextToRealPath = (ServletContext)facesContextInstance.getExternalContext().getContext();
		this.realPath = facesContextToRealPath.getRealPath("/");

		try{
			if(this.outputMethod.equals(SystemConstant.OUTPUT_EMAIL)){
				this.ret = "email";
				Thread t = new Thread() {
					public void run() {
						try{
							// Create Parameter List
							createParamList();
							// Results score list and Time list
							createResults();
						}
						catch (Exception e) {
							e.printStackTrace();
							ret = "error";
						}
					}  
					// Create additional Methods here
				};  
				t.start();
			}
			else{
				// Create Parameter List
				this.createParamList();
				// Results score list and Time list
				this.createResults();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.ret = "error";
		}
		return this.ret;
	}

	private void createParamList(){

		// General Parameters
		Tuple pg1 = new Tuple("Base: ",this.upFile.getName());
		Tuple pg2 = new Tuple("Using Pathway Information: ","");
		if(this.usePathway){pg2.setT2("Yes");}
		else{pg2.setT2("No");}
		Tuple pg3 = new Tuple("Order of Interaction: ",this.order+"");
		Tuple pg4 = new Tuple("No. of Combinations Displayed: ",this.top+"");
		this.generalParamList.add(pg1); this.generalParamList.add(pg2); 
		this.generalParamList.add(pg3); this.generalParamList.add(pg4); 

		// PIA Parameters
		if(this.method.equals(MethodConstant.PIA) || this.method.equals(MethodConstant.COMPARATIVE)){
			Tuple pp1 = new Tuple("Random Shuffles: ",this.shufflePia+"");
			Tuple pp2 = new Tuple("Size of Testing Set: ",(this.fractPia*100)+"%");
			Tuple pp3 = new Tuple("Cross-Validation Repetitions: ",this.ntimePia+"");
			Tuple pp4 = new Tuple("Ratsl (Combination Cutoff): ",this.ratslPia+"");
			Tuple pp5 = new Tuple("Use Fractional Occupation: ","");
			if(!this.ifractPia) pp5.setT2("No");
			else pp5.setT2("Yes");
			Tuple pp6 = new Tuple("Use Training Set: ","");
			if(!this.itrainPia) pp6.setT2("No");
			else pp6.setT2("Yes");
			Tuple pp7 = new Tuple("Training Set Usage Mode: ",this.lootrPia+"");
			this.piaParamList.add(pp1); this.piaParamList.add(pp2); this.piaParamList.add(pp3); 
			this.piaParamList.add(pp4); this.piaParamList.add(pp5); this.piaParamList.add(pp6); 
			this.piaParamList.add(pp7);
			this.generalPlusPiaParamList.add(pg1); this.generalPlusPiaParamList.add(pg2); 
			this.generalPlusPiaParamList.add(pg3); this.generalPlusPiaParamList.add(pg4);
			this.generalPlusPiaParamList.add(pp1); this.generalPlusPiaParamList.add(pp2); 
			this.generalPlusPiaParamList.add(pp3); this.generalPlusPiaParamList.add(pp4); 
			this.generalPlusPiaParamList.add(pp5); this.generalPlusPiaParamList.add(pp6); 
			this.generalPlusPiaParamList.add(pp7);
		}

		// MDR Parameters
		if(this.method.equals(MethodConstant.MDR) || this.method.equals(MethodConstant.COMPARATIVE)){
			Tuple pm1 = new Tuple("Random Shuffles: ",this.shuffleMdr+"");
			Tuple pm2 = new Tuple("Size of Testing Set: ",(this.fractMdr*100)+"%");
			Tuple pm3 = new Tuple("Cross-Validation Repetitions: ",this.ntimeMdr+"");
			Tuple pm4 = new Tuple("Threshold: ",this.thresholdMdr+"");
			this.mdrParamList.add(pm1); this.mdrParamList.add(pm2); 
			this.mdrParamList.add(pm3); this.mdrParamList.add(pm4);
			this.generalPlusMdrParamList.add(pg1); this.generalPlusMdrParamList.add(pg2); 
			this.generalPlusMdrParamList.add(pg3); this.generalPlusMdrParamList.add(pg4); 
			this.generalPlusMdrParamList.add(pm1); this.generalPlusMdrParamList.add(pm2); 
			this.generalPlusMdrParamList.add(pm3); this.generalPlusMdrParamList.add(pm4);
		}

		// ESNP2 Parameters
		if(this.method.equals(MethodConstant.ESNP2) || this.method.equals(MethodConstant.COMPARATIVE)){
			//Tuple pe1 = new Tuple("Random Shuffles: ",this.shuffleEsnp2+"");
			//this.esnp2ParamList.add(pe1);
			this.generalPlusEsnp2ParamList.add(pg1); this.generalPlusEsnp2ParamList.add(pg2); 
			this.generalPlusEsnp2ParamList.add(pg3); this.generalPlusEsnp2ParamList.add(pg4); 
			//this.generalPlusEsnp2ParamList.add(pe1);
		}
		
		// MASS Parameters
		if(this.method.equals(MethodConstant.MASS) || this.method.equals(MethodConstant.COMPARATIVE)){
			Tuple pm1 = new Tuple("Ratsl (Combination Cutoff): ",this.ratslMass+"");
			Tuple pm2 = new Tuple("Assignment Type: ","");
			if(this.simpleAssignMass) pm2.setT2("Simple");
			else pm2.setT2("All Combinations");
			Tuple pm3 = new Tuple("Use Gain: ","");
			if(this.useGainMass) pm3.setT2("Yes");
			else pm3.setT2("No");
			Tuple pm4 = new Tuple("Use Chi: ","");
			if(this.useChiMass) pm4.setT2("Yes");
			else pm4.setT2("No");
			Tuple pm5 = new Tuple("Use Gini: ","");
			if(this.useGiniMass) pm5.setT2("Yes");
			else pm5.setT2("No");
			Tuple pm6 = new Tuple("Use APD: ","");
			if(this.useApdMass) pm6.setT2("Yes");
			else pm6.setT2("No");
			this.massParamList.add(pm1); this.massParamList.add(pm2); this.massParamList.add(pm3); 
			this.massParamList.add(pm4); this.massParamList.add(pm5); this.massParamList.add(pm6);
			this.generalPlusMassParamList.add(pg1); this.generalPlusMassParamList.add(pg2); 
			this.generalPlusMassParamList.add(pg3); this.generalPlusMassParamList.add(pg4);
			this.generalPlusMassParamList.add(pm1); this.generalPlusMassParamList.add(pm2); 
			this.generalPlusMassParamList.add(pm3); this.generalPlusMassParamList.add(pm4); 
			this.generalPlusMassParamList.add(pm5); this.generalPlusMassParamList.add(pm6);
		}

	}

	private void createResults() throws WriteException, IOException{

		// Creating data from the uploaded File
		try{
			InputStream inputStream = this.upFile.getInputStream();
			this.data = Operation.convertStreamToString(inputStream);
		}catch (IOException e) {
			e.printStackTrace();
		}

		// Setting Parameters
		Parameter parameter = new Parameter();

		// General Parameters
		parameter.USE_PATHWAY = this.usePathway;
		parameter.ORDER = this.order;
		parameter.N_TOP = this.top;

		// PIA Parameters
		if(this.method.equals(MethodConstant.PIA) || this.method.equals(MethodConstant.COMPARATIVE)){
			parameter.PIA_SHUFFLE = this.shufflePia;
			parameter.PIA_FRACT = this.fractPia;
			parameter.PIA_RATSL = this.ratslPia;
			parameter.PIA_NTIME = this.ntimePia;
			parameter.PIA_IFRACT = this.ifractPia;
			parameter.PIA_ITRAIN = this.itrainPia;
			if(this.lootrPia.equals("Maximum Rules")) parameter.PIA_LOOTR = 0;
			else if(this.lootrPia.equals("Leave One Out")) parameter.PIA_LOOTR = 1;
			else parameter.PIA_LOOTR = -1;
		}

		// MDR Parameters
		if(this.method.equals(MethodConstant.MDR) || this.method.equals(MethodConstant.COMPARATIVE)){
			parameter.MDR_T = this.thresholdMdr;
			parameter.MDR_SHUFFLE = this.shuffleMdr;
			parameter.MDR_FRACT = this.fractMdr;
			parameter.MDR_NTIME = this.ntimeMdr;
		}

		// ESNP2 Parameters
		/*
		if(this.method.equals(MethodConstant.ESNP2) || this.method.equals(MethodConstant.COMPARATIVE)){
			parameter.ESNP2_SHUFFLE = this.shuffleEsnp2;
		}
		*/
		
		// MASS Parameters
		if(this.method.equals(MethodConstant.MASS) || this.method.equals(MethodConstant.COMPARATIVE)){
			parameter.MASS_RATSL = this.ratslMass;
			parameter.MASS_SIMPLE_ASSIGNMENT = this.simpleAssignMass;
			parameter.MASS_USE_GAIN = this.useGainMass;
			parameter.MASS_USE_CHI = this.useChiMass;
			parameter.MASS_USE_GINI = this.useGiniMass;
			parameter.MASS_USE_APD = this.useApdMass;
		}

		// Creating analysis Object
		SingleAnalysis analysis = new SingleAnalysis(parameter);

		// Setting PIA Results
		if(this.method.equals(MethodConstant.PIA) || this.method.equals(MethodConstant.COMPARATIVE)){
			analysis.doAnalysis(MethodConstant.PIA,this.data);
			ResultSet resultsPia = analysis.getResultSet();
			Time timePia = analysis.getTime();
			this.correct = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_CORRECT], this.FORMAT, parameter.N_TOP);
			this.ss = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_SENS_SPEC], this.FORMAT, parameter.N_TOP);
			this.ppvnpv = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_PPV_NPV], this.FORMAT, parameter.N_TOP);
			this.risk = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_RISK], this.FORMAT, parameter.N_TOP);
			this.odds = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_ODDS], this.FORMAT, parameter.N_TOP);
			this.gini = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_GINI], this.FORMAT, parameter.N_TOP);
			this.apd = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_APD], this.FORMAT, parameter.N_TOP);
			this.overall = resultsPia.getTupleSet(MethodConstant.PIA_SCORES[MethodConstant.PIA_OVERALL], this.FORMAT, parameter.N_TOP);
			for(String s : MethodConstant.PIA_TIME){
				this.piaTimeList.add(new Tuple(s+": ",timePia.get(s)+"ms"));
			}
			if(this.usePathway){
				PathwayAnalysis pathwayAnalysis = new PathwayAnalysis(analysis.getData(),resultsPia,MethodConstant.PIA);
				this.piaPathwayList = pathwayAnalysis.getPathwayAnalysis();
			}
		}

		// Setting MDR Results
		if(this.method.equals(MethodConstant.MDR) || this.method.equals(MethodConstant.COMPARATIVE)){
			analysis.doAnalysis(MethodConstant.MDR,this.data);
			ResultSet resultsMdr = analysis.getResultSet();
			Time timeMdr = analysis.getTime();
			double classBalAccMean = analysis.getClassificationBalAccMean();
			double predBalAccMean = analysis.getPredictionBalAccMean();
			this.meanBalancedAccTrain = classBalAccMean;
			this.meanBalancedAccTest = predBalAccMean;
			this.balancedAccTrain = resultsMdr.getTupleSet(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_CLASS], this.FORMAT, parameter.N_TOP);
			this.balancedAccTest = resultsMdr.getTupleSet(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_PRED], this.FORMAT, parameter.N_TOP);
			for(String s : MethodConstant.MDR_TIME){
				this.mdrTimeList.add(new Tuple(s+": ",timeMdr.get(s)+"ms"));
			}
			if(this.usePathway){
				PathwayAnalysis pathwayAnalysis = new PathwayAnalysis(analysis.getData(),resultsMdr,MethodConstant.MDR);
				this.mdrPathwayList = pathwayAnalysis.getPathwayAnalysis();
			}
		}

		// Setting ESNP2 Results
		if(this.method.equals(MethodConstant.ESNP2) || this.method.equals(MethodConstant.COMPARATIVE)){
			analysis.doAnalysis(MethodConstant.ESNP2,this.data);
			ResultSet resultsEsnp2 = analysis.getResultSet();
			Time timeEsnp2 = analysis.getTime();
			this.deltaR = resultsEsnp2.getTupleSet(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R], this.FORMAT, parameter.N_TOP);
			for(String s : MethodConstant.ESNP2_TIME){
				this.esnp2TimeList.add(new Tuple(s+": ",timeEsnp2.get(s)+"ms"));
			}
			if(this.usePathway){
				PathwayAnalysis pathwayAnalysis = new PathwayAnalysis(analysis.getData(),resultsEsnp2,MethodConstant.ESNP2);
				this.esnp2PathwayList = pathwayAnalysis.getPathwayAnalysis();
			}
		}
		
		// Setting MASS Results
		if(this.method.equals(MethodConstant.MASS) || this.method.equals(MethodConstant.COMPARATIVE)){
			analysis.doAnalysis(MethodConstant.MASS,this.data);
			ResultSet resultsMass = analysis.getResultSet();
			Time timeMass = analysis.getTime();
			this.gainMass = resultsMass.getTupleSet(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN], this.FORMAT, parameter.N_TOP);
			this.chiMass = resultsMass.getTupleSet(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI], this.FORMAT, parameter.N_TOP);
			this.giniMass = resultsMass.getTupleSet(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI], this.FORMAT, parameter.N_TOP);
			this.apdMass = resultsMass.getTupleSet(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD], this.FORMAT, parameter.N_TOP);
			this.overallMass = resultsMass.getTupleSet(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL], this.FORMAT, parameter.N_TOP);
			for(String s : MethodConstant.MASS_TIME){
				this.massTimeList.add(new Tuple(s+": ",timeMass.get(s)+"ms"));
			}
			if(this.usePathway){
				PathwayAnalysis pathwayAnalysis = new PathwayAnalysis(analysis.getData(),resultsMass,MethodConstant.MASS);
				this.massPathwayList = pathwayAnalysis.getPathwayAnalysis();
			}
		}

		// Setting additional Comparative Results (Pathway Analysis)
		if(this.method.equals(MethodConstant.COMPARATIVE) && this.usePathway){
			this.comparativePathwayList = new ArrayList<Score>();
			for(int i = 0 ; i < this.piaPathwayList.size() ; i++){
				Score sPia = this.piaPathwayList.get(i);
				Score sMdr = this.mdrPathwayList.get(i);
				Score sEsnp2 = this.esnp2PathwayList.get(i);
				Score sMass = this.massPathwayList.get(i);
				sPia.setMdrBalancedAccSnps(sMdr.getMdrBalancedAccSnps());
				sPia.setMdrBalancedAccScore(sMdr.getMdrBalancedAccScore());
				sPia.setEsnp2DeltaRSnps(sEsnp2.getEsnp2DeltaRSnps());
				sPia.setEsnp2DeltaRScore(sEsnp2.getEsnp2DeltaRScore());
				sPia.setMassGainSnps(sMass.getMassGainSnps());
				sPia.setMassGainScore(sMass.getMassGainScore());
				sPia.setMassChiSnps(sMass.getMassChiSnps());
				sPia.setMassChiScore(sMass.getMassChiScore());
				sPia.setMassGiniSnps(sMass.getMassGiniSnps());
				sPia.setMassGiniScore(sMass.getMassGiniScore());
				sPia.setMassApdSnps(sMass.getMassApdSnps());
				sPia.setMassApdScore(sMass.getMassApdScore());
				sPia.setMassOverallSnps(sMass.getMassOverallSnps());
				sPia.setMassOverallScore(sMass.getMassOverallScore());
				this.comparativePathwayList.add(sPia);
			}
		}
		
		// Writting Compendium
		if(this.method.equals(MethodConstant.PIA)){
			ArrayList[] paramArray = {this.generalPlusPiaParamList};
			ArrayList[] resultArray = {this.correct,this.ss,this.ppvnpv,this.risk,this.odds,this.gini,this.apd,this.overall};
			ArrayList[] timeArray = {this.piaTimeList};
			this.parameterList = paramArray; this.resultList = resultArray; this.timeList = timeArray; this.pathwayList = this.piaPathwayList;
		}
		else if(this.method.equals(MethodConstant.MDR)){
			ArrayList[] paramArray = {this.generalPlusMdrParamList};
			ArrayList[] resultArray = {this.balancedAccTrain,this.balancedAccTest};
			ArrayList[] timeArray = {this.mdrTimeList};
			this.parameterList = paramArray; this.resultList = resultArray; this.timeList = timeArray; this.pathwayList = this.mdrPathwayList;
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			ArrayList[] paramArray = {this.generalPlusEsnp2ParamList};
			ArrayList[] resultArray = {this.deltaR};
			ArrayList[] timeArray = {this.esnp2TimeList};
			this.parameterList = paramArray; this.resultList = resultArray; this.timeList = timeArray; this.pathwayList = this.esnp2PathwayList;
		}
		else if(this.method.equals(MethodConstant.MASS)){
			ArrayList[] paramArray = {this.generalPlusMassParamList};
			ArrayList[] resultArray = {this.gainMass,this.chiMass,this.giniMass,this.apdMass,this.overallMass};
			ArrayList[] timeArray = {this.massTimeList};
			this.parameterList = paramArray; this.resultList = resultArray; this.timeList = timeArray; this.pathwayList = this.massPathwayList;
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			ArrayList[] paramArray = {this.generalParamList,this.piaParamList,this.mdrParamList/*,this.esnp2ParamList*/,this.massParamList};
			ArrayList[] resultArray = {this.overall,this.balancedAccTrain,this.deltaR,this.overallMass};
			ArrayList[] timeArray = {this.piaTimeList, this.mdrTimeList, this.esnp2TimeList,this.massTimeList};
			this.parameterList = paramArray; this.resultList = resultArray; this.timeList = timeArray; this.pathwayList = this.comparativePathwayList;
		}

		// Choose which method results will be returned to the user:
		if(this.outputMethod.equals(SystemConstant.OUTPUT_SCREEN)){
			this.screenResults();
		}
		else if(this.outputMethod.equals(SystemConstant.OUTPUT_DOWNLOAD)){
			this.downloadResults();
		}
		else if(this.outputMethod.equals(SystemConstant.OUTPUT_EMAIL)){
			this.emailResults();
		}

	}

	private void screenResults() throws WriteException, IOException{

		// Writting Flags
		this.resultBean.setShowPathwayAnalysis(this.usePathway);
		this.resultBean.setMethod(this.method);
		this.resultBean.setRealPath(this.realPath);
		
		// Creating Files to the save results button
		this.downloadResults();
		
		// Writting Parameter Lists
		this.resultBean.setGeneralParamList(this.generalParamList);
		this.resultBean.setPiaParamList(this.piaParamList);
		this.resultBean.setMdrParamList(this.mdrParamList);
		this.resultBean.setEsnp2ParamList(this.esnp2ParamList);
		this.resultBean.setMassParamList(this.massParamList);
		this.resultBean.setGeneralPlusPiaParamList(this.generalPlusPiaParamList);
		this.resultBean.setGeneralPlusMdrParamList(this.generalPlusMdrParamList);
		this.resultBean.setGeneralPlusEsnp2ParamList(this.generalPlusEsnp2ParamList);
		this.resultBean.setGeneralPlusMassParamList(this.generalPlusMassParamList);

		// Writting PIA Results
		ArrayList<Score> piaResultList = new ArrayList<Score>();
		for(int i = 0 ; i < this.correct.size() ; i++){
			Score s = new Score();
			s.setPiaCorrectSnps(this.correct.get(i).getT1());
			s.setPiaCorrectScore(this.correct.get(i).getT2());
			s.setPiaSsSnps(this.ss.get(i).getT1());
			s.setPiaSsScore(this.ss.get(i).getT2());
			s.setPiaPpvNpvSnps(this.ppvnpv.get(i).getT1());
			s.setPiaPpvNpvScore(this.ppvnpv.get(i).getT2());
			s.setPiaRiskSnps(this.risk.get(i).getT1());
			s.setPiaRiskScore(this.risk.get(i).getT2());
			s.setPiaOddsSnps(this.odds.get(i).getT1());
			s.setPiaOddsScore(this.odds.get(i).getT2());
			s.setPiaGiniSnps(this.gini.get(i).getT1());
			s.setPiaGiniScore(this.gini.get(i).getT2());
			s.setPiaApdSnps(this.apd.get(i).getT1());
			s.setPiaApdScore(this.apd.get(i).getT2());
			s.setPiaOverallSnps(this.overall.get(i).getT1());
			s.setPiaOverallScore(this.overall.get(i).getT2());
			piaResultList.add(s);
		}
		this.resultBean.setPiaResultList(piaResultList);

		// Writting MDR Results
		this.resultBean.setMeanBalancedAccTrain(this.meanBalancedAccTrain);
		this.resultBean.setMeanBalancedAccTest(this.meanBalancedAccTest);
		ArrayList<Score> mdrResultList = new ArrayList<Score>();
		for(int i = 0 ; i < this.balancedAccTrain.size() ; i++){
			Score s = new Score();
			s.setMdrBalancedAccSnps(this.balancedAccTrain.get(i).getT1());
			s.setMdrBalancedAccScore(this.balancedAccTrain.get(i).getT2());
			mdrResultList.add(s);
		}
		this.resultBean.setMdrResultList(mdrResultList);
		this.resultBean.setBalancedAccTest(this.balancedAccTest);

		// Writting ESNP2 Results
		ArrayList<Score> esnp2ResultList = new ArrayList<Score>();
		for(int i = 0 ; i < this.deltaR.size() ; i++){
			Score s = new Score();
			s.setEsnp2DeltaRSnps(this.deltaR.get(i).getT1());
			s.setEsnp2DeltaRScore(this.deltaR.get(i).getT2());
			esnp2ResultList.add(s);
		}
		this.resultBean.setEsnp2ResultList(esnp2ResultList);
		
		// Writting MASS Results
		ArrayList<Score> massResultList = new ArrayList<Score>();
		for(int i = 0 ; i < this.gainMass.size() ; i++){
			Score s = new Score();
			s.setMassGainSnps(this.gainMass.get(i).getT1());
			s.setMassGainScore(this.gainMass.get(i).getT2());
			s.setMassChiSnps(this.chiMass.get(i).getT1());
			s.setMassChiScore(this.chiMass.get(i).getT2());
			s.setMassGiniSnps(this.giniMass.get(i).getT1());
			s.setMassGiniScore(this.giniMass.get(i).getT2());
			s.setMassApdSnps(this.apdMass.get(i).getT1());
			s.setMassApdScore(this.apdMass.get(i).getT2());
			s.setMassOverallSnps(this.overallMass.get(i).getT1());
			s.setMassOverallScore(this.overallMass.get(i).getT2());
			massResultList.add(s);
		}
		this.resultBean.setMassResultList(massResultList);

		// Writting Comparative Results
		ArrayList<Score> comparativeResultList = new ArrayList<Score>();
		if(this.method.equals(MethodConstant.COMPARATIVE)){
			for(int i = 0 ; i < this.correct.size() ; i++){
				Score s = new Score();
				s.setPiaOverallSnps(this.overall.get(i).getT1());
				s.setPiaOverallScore(this.overall.get(i).getT2());
				s.setMdrBalancedAccSnps(this.balancedAccTrain.get(i).getT1());
				s.setMdrBalancedAccScore(this.balancedAccTrain.get(i).getT2());
				s.setEsnp2DeltaRSnps(this.deltaR.get(i).getT1());
				s.setEsnp2DeltaRScore(this.deltaR.get(i).getT2());
				s.setMassOverallSnps(this.overallMass.get(i).getT1());
				s.setMassOverallScore(this.overallMass.get(i).getT2());
				comparativeResultList.add(s);
			}
		}
		this.resultBean.setComparativeResultList(comparativeResultList);

		// Writting PIA Time
		this.resultBean.setPiaTimeList(this.piaTimeList);

		// Writting MDR Time
		this.resultBean.setMdrTimeList(this.mdrTimeList);

		// Writting ESNP2 Time
		this.resultBean.setEsnp2TimeList(this.esnp2TimeList);
		
		// Writting MASS Time
		this.resultBean.setMassTimeList(this.massTimeList);

		// Writing Pathway Analysis
		if(this.usePathway){
			this.resultBean.setPiaPathwayList(this.piaPathwayList);
			this.resultBean.setMdrPathwayList(this.mdrPathwayList);
			this.resultBean.setEsnp2PathwayList(this.esnp2PathwayList);
			this.resultBean.setMassPathwayList(this.massPathwayList);
			this.resultBean.setComparativePathwayList(this.comparativePathwayList);
		}

		// Defining ret
		if(this.method.equals(MethodConstant.PIA)){
			this.ret = "screenPia";		
		}
		else if(this.method.equals(MethodConstant.MDR)){
			this.ret = "screenMdr";
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			this.ret = "screenEsnp2";
		}
		else if(this.method.equals(MethodConstant.MASS)){
			this.ret = "screenMass";
		}
		else this.ret = "screenComparative";

	}

	private void downloadResults() throws WriteException, IOException{

		// Defining Real Path in this sessions context
		this.downloadBean.setRealPath(this.realPath);
		
		// Defining the method
		this.downloadBean.setMethod(this.method);
		
		// Defining results
		this.downloadBean.setParameterList(this.parameterList);
		this.downloadBean.setResultList(this.resultList);
		this.downloadBean.setTimeList(this.timeList);
		this.downloadBean.setPathwayList(this.pathwayList);
		
		// Creating files to be downloaded
		// This creation is made before the screen change
		this.downloadBean.createFiles();
		
		this.ret = "download";
	}

	private void emailResults(){
		
		// Defining Real Path in this sessions context
		this.emailBean.setRealPath(this.realPath);
		
		// Defining the method
		this.emailBean.setMethod(this.method);
		
		// Defining the email recipient address
		this.emailBean.setEmail(this.email);
		
		// Defining results
		this.emailBean.setParameterList(this.parameterList);
		this.emailBean.setResultList(this.resultList);
		this.emailBean.setTimeList(this.timeList);
		this.emailBean.setPathwayList(this.pathwayList);

		// Creating files to be downloaded
		this.emailBean.createFiles();
		
		this.emailBean.sendEmail();
		
	}

	// Getters and Setters

	public UploadedFile getUpFile() {
		return upFile;
	}

	public ResultBean getResultBean() {
		return resultBean;
	}

	public void setResultBean(ResultBean resultBean) {
		this.resultBean = resultBean;
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
	}

	public boolean isUsePathway() {
		return usePathway;
	}

	public void setUsePathway(boolean usePathway) {
		this.usePathway = usePathway;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public String getOutputMethod() {
		return outputMethod;
	}

	public void setOutputMethod(String outputMethod) {
		this.outputMethod = outputMethod;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getShufflePia() {
		return shufflePia;
	}

	public void setShufflePia(int shufflePia) {
		this.shufflePia = shufflePia;
	}

	public double getFractPia() {
		return fractPia;
	}

	public void setFractPia(double fractPia) {
		this.fractPia = fractPia;
	}

	public int getNtimePia() {
		return ntimePia;
	}

	public void setNtimePia(int ntimePia) {
		this.ntimePia = ntimePia;
	}

	public double getRatslPia() {
		return ratslPia;
	}

	public void setRatslPia(double ratslPia) {
		this.ratslPia = ratslPia;
	}

	public boolean isIfractPia() {
		return ifractPia;
	}

	public void setIfractPia(boolean ifractPia) {
		this.ifractPia = ifractPia;
	}

	public boolean isItrainPia() {
		return itrainPia;
	}

	public void setItrainPia(boolean itrainPia) {
		this.itrainPia = itrainPia;
	}

	public String getLootrPia() {
		return lootrPia;
	}

	public void setLootrPia(String lootrPia) {
		this.lootrPia = lootrPia;
	}

	public int getShuffleMdr() {
		return shuffleMdr;
	}

	public void setShuffleMdr(int shuffleMdr) {
		this.shuffleMdr = shuffleMdr;
	}

	public double getFractMdr() {
		return fractMdr;
	}

	public void setFractMdr(double fractMdr) {
		this.fractMdr = fractMdr;
	}

	public int getNtimeMdr() {
		return ntimeMdr;
	}

	public void setNtimeMdr(int ntimeMdr) {
		this.ntimeMdr = ntimeMdr;
	}

	public double getThresholdMdr() {
		return thresholdMdr;
	}

	public void setThresholdMdr(double thresholdMdr) {
		this.thresholdMdr = thresholdMdr;
	}
	/*
	public int getShuffleEsnp2() {
		return shuffleEsnp2;
	}

	public void setShuffleEsnp2(int shuffleEsnp2) {
		this.shuffleEsnp2 = shuffleEsnp2;
	}
	*/
	public DownloadBean getDownloadBean() {
		return downloadBean;
	}

	public double getRatslMass() {
		return ratslMass;
	}

	public void setRatslMass(double ratslMass) {
		this.ratslMass = ratslMass;
	}

	public boolean isSimpleAssignMass() {
		return simpleAssignMass;
	}

	public void setSimpleAssignMass(boolean simpleAssignMass) {
		this.simpleAssignMass = simpleAssignMass;
	}

	public boolean isUseGainMass() {
		return useGainMass;
	}

	public void setUseGainMass(boolean useGainMass) {
		this.useGainMass = useGainMass;
	}

	public boolean isUseChiMass() {
		return useChiMass;
	}

	public void setUseChiMass(boolean useChiMass) {
		this.useChiMass = useChiMass;
	}

	public boolean isUseGiniMass() {
		return useGiniMass;
	}

	public void setUseGiniMass(boolean useGiniMass) {
		this.useGiniMass = useGiniMass;
	}

	public boolean isUseApdMass() {
		return useApdMass;
	}

	public void setUseApdMass(boolean useApdMass) {
		this.useApdMass = useApdMass;
	}

	public void setDownloadBean(DownloadBean downloadBean) {
		this.downloadBean = downloadBean;
	}

	public EmailBean getEmailBean() {
		return emailBean;
	}

	public void setEmailBean(EmailBean emailBean) {
		this.emailBean = emailBean;
	}
	
	

}
