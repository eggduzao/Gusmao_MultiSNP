package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import constants.MethodConstant;
import constants.SystemConstant;

import basic.Score;
import basic.Tuple;

/**
 * 
 * Creates a TXT File containing the results to be downloaded or send by mail
 * 
 * @author Eduardo Gade Gusmao
 *
 */

public class TxtCreator {

	// Basic Elements
	private String method;
	private ArrayList<Tuple>[] parameters;
	private ArrayList<Tuple>[] results;
	private ArrayList<Tuple>[] time;
	private ArrayList<Score> pathway;

	// Return
	private BufferedWriter writer;

	public TxtCreator(String method, ArrayList<Tuple>[] parameters, ArrayList<Tuple>[] results, ArrayList<Tuple>[] time, 
			ArrayList<Score> pathway, String realPath) throws IOException{

		// Initialization
		this.method = method;
		this.parameters = parameters;
		this.results = results;
		this.time = time;
		this.pathway = pathway;

		// Writer representing the File to be written
		this.writer = new BufferedWriter(new FileWriter(realPath+SystemConstant.DEFAULT_FILE_ANALYSIS_TXT));

		// Create TXT
		this.createTxt();

		// Close references
		this.writer.close(); 

	}

	private void createTxt() throws IOException{

		this.writeParameters();
		this.writeResults();
		this.writeTime();
		if(this.pathway!=null && !this.pathway.isEmpty()) this.writePathway();

	}

	private void writeParameters() throws IOException{

		this.writer.write("#### Parameters");
		this.writer.newLine(); this.writer.newLine(); 

		for(int i = 0 ; i < this.parameters.length ; i++){

			ArrayList<Tuple> param = this.parameters[i];
			
			if(parameters.length==1){
				if(this.method.equals(MethodConstant.PIA)) this.writer.write("## PIA");
				else if(this.method.equals(MethodConstant.MDR)) this.writer.write("## MDR");
				else if(this.method.equals(MethodConstant.ESNP2)) this.writer.write("## ESNP2");
				else if(this.method.equals(MethodConstant.MASS)) this.writer.write("## MASS");
				this.writer.newLine(); 
			}
			else{
				if(i==0) this.writer.write("## General");
				else if(i==1) this.writer.write("## PIA");
				else if(i==2) this.writer.write("## MDR");
				//else if(i==3) this.writer.write("## ESNP2");
				else if(i==3) this.writer.write("## MASS");
				this.writer.newLine(); 
			}
			
			for(Tuple t : param){
				this.writer.write(t.getT1()+""+t.getT2());
				this.writer.newLine(); 
			}
			
			this.writer.newLine(); 
			
		}
		
		this.writer.newLine(); 

	}

	private void writeResults() throws IOException{
		
		this.writer.write("#### Results");
		this.writer.newLine(); this.writer.newLine(); 
		
		String[] scoreNames = null;
		if(this.method.equals(MethodConstant.PIA)){
			scoreNames = MethodConstant.PIA_SCORES_WITHOUT_SINGLE_SHOT;
		}
		else if(this.method.equals(MethodConstant.MDR)){
			scoreNames = MethodConstant.MDR_SCORES;
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			scoreNames = MethodConstant.ESNP2_SCORES;
		}
		else if(this.method.equals(MethodConstant.MASS)){
			scoreNames = MethodConstant.MASS_SCORES;
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			scoreNames = MethodConstant.COMPARATIVE_SCORES;
		}
		
		this.writer.write(scoreNames[0]);
		for(int j = 1 ; j < scoreNames.length ; j++){
			this.writer.write(" - "+scoreNames[j]);
		}
		this.writer.newLine(); 
		
		for(int i = 0 ; i < this.results[0].size() ; i++){
			
			for(int j = 0 ; j < this.results.length ; j++){
				
				if(i < this.results[j].size()){
					
					this.writer.write("("+this.results[j].get(i).getT1()+" = "+this.results[j].get(i).getT2()+") ");
					
				}
				
			}
			
			this.writer.newLine(); 
			
		}
		
		this.writer.newLine(); 
		this.writer.newLine(); 
		
	}

	private void writeTime() throws IOException{
		
		this.writer.write("#### Time Analysis");
		this.writer.newLine(); this.writer.newLine(); 
		
		for(int i = 0 ; i < this.time.length ; i++){

			ArrayList<Tuple> param = this.time[i];
			
			if(time.length==1){
				if(this.method.equals(MethodConstant.PIA)) this.writer.write("## PIA");
				else if(this.method.equals(MethodConstant.MDR)) this.writer.write("## MDR");
				else if(this.method.equals(MethodConstant.ESNP2)) this.writer.write("## ESNP2");
				else if(this.method.equals(MethodConstant.MASS)) this.writer.write("## MASS");
				this.writer.newLine(); 
			}
			else{
				if(i==0) this.writer.write("## PIA");
				else if(i==1) this.writer.write("## MDR");
				else if(i==2) this.writer.write("## ESNP2");
				else if(i==3) this.writer.write("## MASS");
				this.writer.newLine(); 
			}
			
			for(Tuple t : param){
				this.writer.write(t.getT1()+""+t.getT2());
				this.writer.newLine(); 
			}
			
			this.writer.newLine(); 
			
		}
		
		this.writer.newLine(); 
		
	}

	private void writePathway() throws IOException{
		
		this.writer.write("#### Pathway Analysis");
		this.writer.newLine(); this.writer.newLine(); 
		
		String[] scoreNames = null;
		if(this.method.equals(MethodConstant.PIA)){
			scoreNames = MethodConstant.PIA_SCORES_WITHOUT_SINGLE_SHOT;
		}
		else if(this.method.equals(MethodConstant.MDR)){
			scoreNames = MethodConstant.MDR_SCORES_WITHOUT_PREDICT;
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			scoreNames = MethodConstant.ESNP2_SCORES;
		}
		else if(this.method.equals(MethodConstant.MASS)){
			scoreNames = MethodConstant.MASS_SCORES;
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			scoreNames = MethodConstant.COMPARATIVE_SCORES;
		}
		
		this.writer.write(scoreNames[0]);
		for(int j = 1 ; j < scoreNames.length ; j++){
			this.writer.write(" - "+scoreNames[j]);
		}
		this.writer.newLine();
		
		for(Score s : this.pathway){
			if(this.method.equals(MethodConstant.PIA)){
				this.writer.write("("+s.getPiaCorrectSnps()+" = "+s.getPiaCorrectScore()+") ");
				this.writer.write("("+s.getPiaSsSnps()+" = "+s.getPiaSsScore()+") ");
				this.writer.write("("+s.getPiaPpvNpvSnps()+" = "+s.getPiaPpvNpvScore()+") ");
				this.writer.write("("+s.getPiaRiskSnps()+" = "+s.getPiaRiskScore()+") ");
				this.writer.write("("+s.getPiaOddsSnps()+" = "+s.getPiaOddsScore()+") ");
				this.writer.write("("+s.getPiaGiniSnps()+" = "+s.getPiaGiniScore()+") ");
				this.writer.write("("+s.getPiaApdSnps()+" = "+s.getPiaApdScore()+") ");
				this.writer.write("("+s.getPiaOverallSnps()+" = "+s.getPiaOverallScore()+")");
			}
			else if(this.method.equals(MethodConstant.MDR)){
				this.writer.write("("+s.getMdrBalancedAccSnps()+" = "+s.getMdrBalancedAccScore()+")");
			}
			else if(this.method.equals(MethodConstant.ESNP2)){
				this.writer.write("("+s.getEsnp2DeltaRSnps()+" = "+s.getEsnp2DeltaRScore()+")");
			}
			else if(this.method.equals(MethodConstant.MASS)){
				this.writer.write("("+s.getMassGainSnps()+" = "+s.getMassGainScore()+") ");
				this.writer.write("("+s.getMassChiSnps()+" = "+s.getMassChiScore()+") ");
				this.writer.write("("+s.getMassGiniSnps()+" = "+s.getMassGiniScore()+") ");
				this.writer.write("("+s.getMassApdSnps()+" = "+s.getMassApdScore()+") ");
				this.writer.write("("+s.getMassOverallSnps()+" = "+s.getMassOverallScore()+") ");
			}
			else if(this.method.equals(MethodConstant.COMPARATIVE)){
				this.writer.write("("+s.getPiaOverallSnps()+" = "+s.getPiaOverallScore()+") ");
				this.writer.write("("+s.getMdrBalancedAccSnps()+" = "+s.getMdrBalancedAccScore()+") ");
				this.writer.write("("+s.getEsnp2DeltaRSnps()+" = "+s.getEsnp2DeltaRScore()+")");
				this.writer.write("("+s.getMassOverallSnps()+" = "+s.getMassOverallScore()+") ");
			}
			this.writer.newLine(); 
		}
		
	}

	/* Testing Main 
	public static void main(String[] args){

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("teste.txt"));
			out.write("aString\n");
			out.write("anotherone");
			out.close();
		} catch (IOException e) {
		}

	}
	*/
}
