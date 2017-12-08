package bean;

import io.TxtCreator;
import io.XlsCreator;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.context.FacesContext;

import jxl.write.WriteException;

import constants.MethodConstant;
import constants.SystemConstant;

import basic.Score;
import basic.Tuple;

/**
 * 
 * JSF Interface between JSP code and Java code representing the download results screen
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class DownloadBean {

	// Basic Elements
	private String method;
	private String realPath;
	private ArrayList<Tuple>[] parameterList;
	private ArrayList<Tuple>[] resultList;
	private ArrayList<Tuple>[] timeList;
	private ArrayList<Score> pathwayList;
	
	public void createFiles(){
		
		try {
			TxtCreator txt = new TxtCreator(this.method,this.parameterList,this.resultList,this.timeList,this.pathwayList,this.realPath);
			XlsCreator xls = new XlsCreator(this.method,this.parameterList,this.resultList,this.timeList,this.pathwayList,this.realPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
	}
	
	public String downloadXls(){
		
		DownloadOperation.downloadFile(SystemConstant.DEFAULT_FILE_ANALYSIS_XLS,this.realPath,"xls",FacesContext.getCurrentInstance());
		
		return "";
	}
	
	public String downloadTxt(){
		
		DownloadOperation.downloadFile(SystemConstant.DEFAULT_FILE_ANALYSIS_TXT,this.realPath,"txt",FacesContext.getCurrentInstance());
		
		return "";
	}
	
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

	// Getters and Setters
	
	public ArrayList<Tuple>[] getParameterList() {
		return parameterList;
	}

	public void setParameterList(ArrayList<Tuple>[] parameterList) {
		this.parameterList = parameterList;
	}

	public ArrayList<Tuple>[] getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<Tuple>[] resultList) {
		this.resultList = resultList;
	}

	public ArrayList<Tuple>[] getTimeList() {
		return timeList;
	}

	public void setTimeList(ArrayList<Tuple>[] timeList) {
		this.timeList = timeList;
	}

	public ArrayList<Score> getPathwayList() {
		return pathwayList;
	}

	public void setPathwayList(ArrayList<Score> pathwayList) {
		this.pathwayList = pathwayList;
	}

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
}
