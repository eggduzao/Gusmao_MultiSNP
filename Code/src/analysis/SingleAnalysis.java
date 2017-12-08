package analysis;

import constants.MethodConstant;
import io.Arquivo;
import io.Reader;

import method.*;

import basic.Data;
import basic.ResultSet;
import basic.Time;

/**
 * 
 * Perform a single analysis. One method applied on one base.
 * 
 * @author egg
 *
 */
public class SingleAnalysis {
	
	// Basic SingleAnalysis Element
	private Parameter parameter;
	private Data data;

	// Results
	private ResultSet resultSet;
	private Time time;
	private double classificationBalAccMean;
	private double predictionBalAccMean;
	
	public SingleAnalysis(Parameter parameter){
		this.parameter = parameter;
	}
	
	public void doAnalysis(String method){
		
		Reader reader = new Reader(this.parameter.PATH, this.parameter.USE_PATHWAY);
		this.data = reader.getData();
		
		this.analysis(method);
		
	}
	
	public void doAnalysis(String method, Arquivo inFile){
		
		Reader reader = new Reader(inFile, this.parameter.USE_PATHWAY);
		this.data = reader.getData();
		
		this.analysis(method);
		
	}
	
	public void doAnalysis(String method, String data){
		
		Reader reader = new Reader(data, this.parameter.USE_PATHWAY);
		this.data = reader.getData();
		
		this.analysis(method);
		
	}
	
	private void analysis(String method){
		
		Method methodInstance = null;
		
		if(method.equals(MethodConstant.PIA)){
			methodInstance = new PIA(data,this.parameter);
		}
		else if(method.equals(MethodConstant.MDR)){
			methodInstance = new MDR(data,this.parameter);
			this.classificationBalAccMean = ((MDR)methodInstance).getClassificationBalAccMean();
			this.predictionBalAccMean = ((MDR)methodInstance).getPredictionBalAccMean();
		}
		else if(method.equals(MethodConstant.ESNP2)){
			methodInstance = new ESNP2(data,this.parameter);
		}
		else if(method.equals(MethodConstant.MASS)){
			methodInstance = new MASS(data,this.parameter);
		}
		
		this.resultSet = methodInstance.getResultSet();
		this.time = methodInstance.getTime();
		
	}
	
	// Getters and Setters
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
	
	///////////////// Returns: //////////////////

	public ResultSet getResultSet() {
		return this.resultSet;
	}

	public Time getTime() {
		return this.time;
	}
	
	// MDR - This is the mean over all best classification models, even the ones that were discarted
	public double getClassificationBalAccMean(){
		return this.classificationBalAccMean;
	}
	
	// MDR - This is the mean over all best prediction models
	public double getPredictionBalAccMean(){
		return this.predictionBalAccMean;
	}
	
}
