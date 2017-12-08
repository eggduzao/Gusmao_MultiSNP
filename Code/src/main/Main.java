package main;

import constants.MethodConstant;

import analysis.*;


/**
 * 
 * Main for experimental design testing and method debug.
 * @author egg
 *
 */
public class Main {

	public Main(){

		Parameter parameter = new Parameter();

		parameter.PATH = "C:/Eduardo/Ciencia da Computacao/IC/Dados/TGVelez/20SNP/200/00/0.txt";
		parameter.USE_PATHWAY = false;
		parameter.ORDER = 2;
		parameter.N_TOP = 20;
		parameter.COUNT_MISSING = false;

		parameter.PIA_SHUFFLE = 1000;
		parameter.PIA_FRACT = 0.1;
		parameter.PIA_RATSL = 1.7;
		parameter.PIA_NTIME = 1;
		parameter.PIA_IFRACT = false;
		parameter.PIA_ITRAIN = false;
		parameter.PIA_LOOTR = 0;
		parameter.PIA_HIDE_SS = false;

		// MDR
		parameter.MDR_T = 1.0;
		parameter.MDR_SHUFFLE = 1000;
		parameter.MDR_FRACT = 0.1;
		parameter.MDR_NTIME = 1;

		// ESNP2
		//parameter.ESNP2_SHUFFLE = 2000;
		
		// MASS
		parameter.MASS_RATSL = 1.4;
		parameter.MASS_SIMPLE_ASSIGNMENT = false;
		parameter.MASS_USE_GAIN = true;
		parameter.MASS_USE_CHI = true;
		parameter.MASS_USE_GINI = true;
		parameter.MASS_USE_APD = false;

		SingleAnalysis singleAnalysis = new SingleAnalysis(parameter);
		
		singleAnalysis.doAnalysis(MethodConstant.MASS);
		
		System.out.println(singleAnalysis.getResultSet());
		
		/*
		int rateGain = 0;
		int rateChi = 0;
		int rateGini = 0;
		int rateAPD = 0;
		int rateOverall = 0;
		int rateEsnp2 = 0;
		
		for(int i = 0 ; i < 100 ; i++){
			parameter.PATH = "C:/Eduardo/Ciencia da Computacao/IC/Dados/ValezData/1600/66/66.1600."+i+".txt";
			singleAnalysis.doAnalysis(MethodConstant.MASS);
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN],0,1).equals("X1"))
				rateGain++;
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI],0,1).equals("X1"))
				rateChi++;
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI],0,1).equals("X1"))
				rateGini++;
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD],0,1).equals("X1"))
				rateAPD++;
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL],0,1).equals("X1"))
				rateOverall++;
			
			singleAnalysis.doAnalysis(MethodConstant.ESNP2);
			if(singleAnalysis.getResultSet().getSnp(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R],0,0).equals("X0") &&
					singleAnalysis.getResultSet().getSnp(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R],0,1).equals("X1"))
				rateEsnp2++;
		}
		
		System.out.println(rateGain);
		System.out.println(rateChi);
		System.out.println(rateGini);
		System.out.println(rateAPD);
		System.out.println(rateOverall);
		System.out.println(rateEsnp2);
		
		// 93 - 81 - 89 - 96 - 66
		*/
		
		/*
		SingleAnalysis singleAnalysis = new SingleAnalysis(parameter);

		double piaT = 0;
		double mdrT = 0;
		double esnp2T = 0;
		double ntime = 10;
		
		for(int i = 0 ; i < ntime ; i++){
			singleAnalysis.doAnalysis(MethodConstant.PIA);
			piaT += singleAnalysis.getTime().get("Total");
				
			singleAnalysis.doAnalysis(MethodConstant.MDR);
			mdrT += singleAnalysis.getTime().get("Total");
			
			singleAnalysis.doAnalysis(MethodConstant.ESNP2);
			esnp2T += singleAnalysis.getTime().get("Total");
		}
		
		System.out.println(piaT); System.out.println(mdrT); System.out.println(esnp2T);
		*/
		
		/* Time analysis 
		int[] pop = {400,800,1600};
		int[] feat = {20,50,100};
		for(int i = 0 ; i < pop.length ; i++){
			for(int j = 0 ; j < feat.length ; j++){
				parameter.PATH = "C:/Documents and Settings/Sony/Desktop/TESTES/Dados/dados/"+feat[j]+" SNPs/"+feat[j]+" SNPs/"+pop[i]+"/00/00."+pop[i]+".0.txt";
				singleAnalysis.doAnalysis(MethodConstant.PIA);
				Time timePIA = singleAnalysis.getTime();
				singleAnalysis.doAnalysis(MethodConstant.MDR);
				Time timeMDR = singleAnalysis.getTime();
				singleAnalysis.doAnalysis(MethodConstant.ESNP2);
				Time timeESNP2 = singleAnalysis.getTime();
				System.out.println("Pop="+pop[i]+", Feat="+feat[j]+" -> PIA = "+timePIA.get("Total")+"  MDR = "+timeMDR.get("Total")+"  ESNP2 = "+timeESNP2.get("Total"));
			}
		}
		*/
		
		/*
		for(int k = 40 ; k < 69 ; k++){

			int piaCorrectCorrect = 0;
			int piaCorrectSS = 0;
			int piaCorrectPPV = 0;
			int piaCorrectRisk = 0;
			int piaCorrectOdds = 0;
			int piaCorrectGini = 0;
			int piaCorrectApd = 0;
			int piaCorrectOverall = 0;
			int mdrCorrect = 0;
			int esnp2Correct = 0;

			for(int i = 0 ; i < 100 ; i++){

				parameter.PATH = "C:/Documents and Settings/Sony/Desktop/TESTES/Dados/1600/"+k+"/"+k+".1600."+i+".txt";

				
				singleAnalysis.doAnalysis(MethodConstant.PIA);
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_CORRECT],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_CORRECT],0,1).equals("X1"))
					piaCorrectCorrect++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_SENS_SPEC],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_SENS_SPEC],0,1).equals("X1"))
					piaCorrectSS++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_PPV_NPV],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_PPV_NPV],0,1).equals("X1"))
					piaCorrectPPV++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_RISK],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_RISK],0,1).equals("X1"))
					piaCorrectRisk++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_ODDS],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_ODDS],0,1).equals("X1"))
					piaCorrectOdds++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_GINI],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_GINI],0,1).equals("X1"))
					piaCorrectGini++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_APD],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_APD],0,1).equals("X1"))
					piaCorrectApd++;
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_OVERALL],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.PIA_SCORES[MethodConstant.PIA_OVERALL],0,1).equals("X1"))
					piaCorrectOverall++;

				
				singleAnalysis.doAnalysis(MethodConstant.MDR);
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_CLASS],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_CLASS],0,1).equals("X1"))
					mdrCorrect++;
				

				singleAnalysis.doAnalysis(MethodConstant.ESNP2);
				if(singleAnalysis.getResultSet().getSnp(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R],0,0).equals("X0") &&
						singleAnalysis.getResultSet().getSnp(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R],0,1).equals("X1"))
					esnp2Correct++;
				

			}

			System.out.println("Path = "+k);
			System.out.println("pia correct = "+piaCorrectCorrect);
			System.out.println("pia sens+spec = "+piaCorrectSS);
			System.out.println("pia ppv+npv = "+piaCorrectPPV);
			System.out.println("pia risk = "+piaCorrectRisk);
			System.out.println("pia odds = "+piaCorrectOdds);
			System.out.println("pia gini = "+piaCorrectGini);
			System.out.println("pia apd = "+piaCorrectApd);
			System.out.println("pia overall = "+piaCorrectOverall);
			System.out.println("mdr = "+mdrCorrect);
			System.out.println("esnp2 = "+esnp2Correct);
			System.out.println();
		}
		*/
		
	}
	
	
	public static void main(String[] args) {
		Main main = new Main();
	}
	
	/*
	public static void main(String[] args) {
		DecimalFormat d = new DecimalFormat("00");
		for(int i = 0 ; i < 100 ; i++){
			System.out.print("\""+i+"\",");
		}
	}
	*/
}
