package constants;

import java.util.ArrayList;

/**
 * 
 * Constants used by the methods parameters
 * 
 * @author Eduardo Gade Gusmao
 *
 */

public class MethodConstant {

	/**
	 * PIA PARAMETERS
	 */
	public static final String PIA = "PIA";
	public static final String[] PIA_SCORES = {"%Correct","Sens+Spec","Ppv+Npv","Risk","Odds","Gini","Apd","Overall",
		   "%Correct_SS","Sens+Spec_SS","Ppv+Npv_SS","Risk_SS","Odds_SS"};
	public static final String[] PIA_SCORES_OVERALL = {"%Correct","Sens+Spec","Ppv+Npv","Risk","Odds",
		"Gini","Apd"};
	public static final String[] PIA_SCORES_WITHOUT_SINGLE_SHOT = {"%Correct","Sens+Spec","Ppv+Npv","Risk","Odds",
		"Gini","Apd","Overall"};
	public static final String[] PIA_SCORES_OVERALL_WITHOUT_SS = {"%Correct","Ppv+Npv","Risk","Odds",
		"Gini","Apd"};
	public static final int PIA_CORRECT = 0;
	public static final int PIA_SENS_SPEC = 1;
	public static final int PIA_PPV_NPV = 2;
	public static final int PIA_RISK = 3;
	public static final int PIA_ODDS = 4;
	public static final int PIA_GINI = 5;
	public static final int PIA_APD = 6;
	public static final int PIA_OVERALL = 7;
	public static final int PIA_CORRECT_SS = 8;
	public static final int PIA_SENS_SPEC_SS = 9;
	public static final int PIA_PPV_NPV_SS = 10;
	public static final int PIA_RISK_SS = 11;
	public static final int PIA_ODDS_SS = 12;
	
	public static final String[] PIA_TIME = {"Initialization","Loop","Split-Based","Contingency-Based",
		"Single-Shot","Overall","Total"};
	public static final int PIA_TIME_INIT = 0;
	public static final int PIA_TIME_LOOP = 1;
	public static final int PIA_TIME_SPLIT = 2;
	public static final int PIA_TIME_CONT = 3;
	public static final int PIA_TIME_SS = 4;
	public static final int PIA_TIME_OVERALL = 5;
	public static final int PIA_TIME_TOTAL = 6;
	
	/**
	 * MDR PARAMETERS
	 */
	public static final String MDR = "MDR";
	public static final String[] MDR_SCORES = {"BalancedAccuracyClassif","BalancedAccuracyPredict"};
	public static final String[] MDR_SCORES_WITHOUT_PREDICT = {"BalancedAccuracyClassif"};
	public static final int MDR_BAL_ACC_CLASS = 0;
	public static final int MDR_BAL_ACC_PRED = 1;
	
	public static final String[] MDR_TIME = {"Initialization","Loop","Classification","Prediction",
		"Total"};
	public static final int MDR_TIME_INIT = 0;
	public static final int MDR_TIME_LOOP = 1;
	public static final int MDR_TIME_CLAS = 2;
	public static final int MDR_TIME_PRED = 3;
	public static final int MDR_TIME_TOTAL = 4;
	
	/**
	 * ESNP2 PARAMETERS
	 */
	public static final String ESNP2 = "ESNP2";
	public static final String[] ESNP2_SCORES = {"DeltaR"};
	public static final int ESNP2_DELTA_R = 0;
	
	public static final String[] ESNP2_TIME = {"Initialization","Loop","Single Entropy",
		"Combination Entropy","Total"};
	public static final int ESNP2_TIME_INIT = 0;
	public static final int ESNP2_TIME_LOOP = 1;
	public static final int ESNP2_TIME_SINGLE = 2;
	public static final int ESNP2_TIME_COMB = 3;
	public static final int ESNP2_TIME_TOTAL = 4;
	
	/**
	 * MASS PARAMETERS
	 */
	public static final String MASS = "MASS";
	public static final String[] MASS_SCORES = {"Gain","Chi","Gini","APD","Overall"};
	public static final String[] MASS_SCORES_OVERALL = {"Gain","Chi","Gini","APD"};
	public static final int MASS_GAIN = 0;
	public static final int MASS_CHI = 1;
	public static final int MASS_GINI = 2;
	public static final int MASS_APD = 3;
	public static final int MASS_OVERALL = 4;
	
	public static final String[] MASS_TIME = {"Initialization","Loop","Single Scores","Middle-Order Scores",
		"Combination Scores","Overall","Total"};
	public static final int MASS_TIME_INIT = 0;
	public static final int MASS_TIME_LOOP = 1;
	public static final int MASS_TIME_SINGLE = 2;
	public static final int MASS_TIME_MIDDLE = 3;
	public static final int MASS_TIME_COMB = 4;
	public static final int MASS_TIME_OVERALL = 5;
	public static final int MASS_TIME_TOTAL = 6;
	
	
	public static final String COMPARATIVE = "COMPARATIVE";
	public static final String[] COMPARATIVE_SCORES = {"PIA Overall","MDR Balanced Accuracy","ESNP2 Delta R","MASS Overall"};
	public static final int COMPARATIVE_PIA = 0;
	public static final int COMPARATIVE_MDR = 1;
	public static final int COMPARATIVE_ESNP2 = 2;
	public static final int COMPARATIVE_MASS = 3;
	
}
