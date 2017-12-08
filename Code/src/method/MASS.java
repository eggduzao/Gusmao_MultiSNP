package method;

import java.util.ArrayList;

import util.Operation;

import constants.MethodConstant;

import analysis.Parameter;
import basic.Data;
import basic.GenotypePhenotypeTable;
import basic.ResultSet;
import basic.Time;

/**
 * 
 * Perform the MASS (novel algorithm).
 * 
 * @author egg
 *
 */
public class MASS extends CombinatorialMethod implements Method{

	// Results
	private ResultSet resultSet;
	private Time time;

	// Time
	private long initTime;
	private long loopTime;
	private long singleTime;
	private long combTime;
	private long middleTime;
	private long overallTime;
	private long totalTime;

	// Auxiliary
	private double[][] singleEntropy;

	/**
	 * Call the method
	 * @param data
	 * @param parameter
	 */
	public MASS(Data data, Parameter parameter){

		super(data,parameter);

		// Initialization
		this.initialization();

		// Single Entropy calculation
		this.calculateSingleScores();

		// Main Loop - Analyze each feature combination at the requested order
		this.mainLoop();

		// Evaluate Overall Score
		this.overallScore();

		// Print Time
		this.printTime();

	}

	/**
	 * Initialization of the Algorithm
	 */
	private void initialization(){

		long t1 = System.currentTimeMillis();

		// ResultSet
		this.resultSet = new ResultSet(MethodConstant.MASS_SCORES,
				this.comb(this.data.features(),this.parameter.ORDER),this.parameter.ORDER);

		// Time
		this.time = new Time();

		long t2 = System.currentTimeMillis();
		this.initTime = (t2-t1);

	}

	/**
	 * Put the evaluated time in the Time Object
	 */
	private void printTime(){
		this.totalTime = this.initTime + this.loopTime + this.singleTime + this.middleTime + this.combTime + this.overallTime;
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_INIT],this.initTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_LOOP],this.loopTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_SINGLE],this.singleTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_MIDDLE],this.middleTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_COMB],this.combTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_OVERALL],this.overallTime);
		this.time.add(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_TOTAL],this.totalTime);
	}

	/**
	 * Evaluate entropies of all single SNPs
	 */
	private void calculateSingleScores(){

		long t1 = System.currentTimeMillis();

		this.singleEntropy = new double[this.data.features()][MethodConstant.MASS_SCORES_OVERALL.length];

		for(int f = 0 ; f < this.data.features() ; f++){

			int[] poss = {this.possCount[f]};
			GenotypePhenotypeTable gp = new GenotypePhenotypeTable(poss);

			for(int i = 0 ; i < this.data.population() ; i++){
				double[] snp = {this.data.getData(i,f)};
				gp.increase(snp,this.data.getClass(i));
			}

			this.singleEntropy[f][MethodConstant.MASS_GAIN] = this.calculateGain(gp);
			this.singleEntropy[f][MethodConstant.MASS_CHI] = this.calculateChi(gp);
			this.singleEntropy[f][MethodConstant.MASS_GINI] = this.calculateGini(gp);
			this.singleEntropy[f][MethodConstant.MASS_APD] = this.calculateAPD(gp);

		}

		long t2 = System.currentTimeMillis();
		this.loopTime += (t2-t1);

	}

	/**
	 * Evaluate all combinations entropy and put them into the result set
	 */
	private void mainLoop(){

		long loop = System.currentTimeMillis();

		while(this.next()){

			long t1 = System.currentTimeMillis();
			this.loopTime += (t1-loop);

			GenotypePhenotypeTable gp = this.getGenoPheno();

			double gain = this.calculateGain(gp);
			double chi = this.calculateChi(gp);
			double gini = this.calculateGini(gp);
			double apd = this.calculateAPD(gp);

			long t2 = System.currentTimeMillis();
			this.combTime += (t2-t1);

			// Tratamento Intermediario

			if(!this.parameter.MASS_SIMPLE_ASSIGNMENT){

				int[] combMemory = this.actualComb.clone();

				for(int ord = this.parameter.ORDER-1 ; ord > 1 ; ord--){

					this.actualComb = new int[ord];
					while(this.next()){
						GenotypePhenotypeTable gpMiddle = this.getGenoPheno();
						gain -= this.calculateGain(gpMiddle);
						chi -= this.calculateChi(gpMiddle);
						gini -= this.calculateGini(gpMiddle);
						apd -= this.calculateAPD(gpMiddle);
					}

				}

				this.actualComb = combMemory;
			}


			long t3 = System.currentTimeMillis();
			this.middleTime += (t3-t2);

			for(int i = 0 ; i < this.actualComb.length ; i++){
				gain -= this.singleEntropy[this.actualComb[i]][MethodConstant.MASS_GAIN];
				chi -= this.singleEntropy[this.actualComb[i]][MethodConstant.MASS_CHI];
				gini -= this.singleEntropy[this.actualComb[i]][MethodConstant.MASS_GINI];
				apd -= this.singleEntropy[this.actualComb[i]][MethodConstant.MASS_APD];
			}

			this.resultSet.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN],this.data.getHeader(this.actualComb),gain);
			this.resultSet.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI],this.data.getHeader(this.actualComb),chi);
			this.resultSet.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI],this.data.getHeader(this.actualComb),gini);
			this.resultSet.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD],this.data.getHeader(this.actualComb),apd);

			loop = System.currentTimeMillis();
			this.combTime += (loop-t3);

		}

	}

	/**
	 * Evaluate the Overall Score that correspond to the summation of all the previous scores for the given
	 * combination after the normalization of each score set so that the highest value is set to 50
	 * And write it into the ResultSet
	 */
	private void overallScore(){

		long t1 = System.currentTimeMillis();

		ArrayList<String> scoreList = new ArrayList<String>();
		if(this.parameter.MASS_USE_GAIN) scoreList.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN]);
		if(this.parameter.MASS_USE_CHI) scoreList.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI]);
		if(this.parameter.MASS_USE_GINI) scoreList.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI]);
		if(this.parameter.MASS_USE_APD) scoreList.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD]);
		String[] massScores = new String[scoreList.size()];
		scoreList.toArray(massScores);

		double[][] tableScore = new double[this.resultSet.size()][massScores.length];
		String[][] tableSnp = new String[this.resultSet.size()][this.parameter.ORDER];
		boolean first = true;

		// Deslocando os resultados de forma que o menor valor seja 0
		this.resultSet.setSmallerTo0();

		double[] max = new double[massScores.length];
		for(int i = 0 ; i < max.length ; i++){max[i] = this.resultSet.getScore(massScores[i],0);};

		for(int scoreCount = 0 ; scoreCount < massScores.length ; scoreCount++){
			String name = massScores[scoreCount];
			for(int rank = 0 ; rank < this.resultSet.size() ; rank++){
				tableScore[this.resultSet.getEntry(name,rank)][scoreCount] = this.resultSet.getScore(name,rank);
				if(first) tableSnp[this.resultSet.getEntry(name,rank)] = this.resultSet.getSnps(name,rank);	
			}
			if(first) first = false;	
		}

		for(int rank = 0 ; rank < this.resultSet.size() ; rank++){
			double sum = 0;
			for(int scoreCount = 0 ; scoreCount < massScores.length ; scoreCount++){
				sum+= ((100*tableScore[rank][scoreCount]) / max[scoreCount]);	
			}
			this.resultSet.add(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL],tableSnp[rank],sum);
		}

		long t2 = System.currentTimeMillis();
		this.overallTime = (t2-t1);

	}

	/**
	 * Calculates the information gain ratio without subtracting from total base gain
	 * @param gp
	 * @return double gain
	 */
	private double calculateGain(GenotypePhenotypeTable gp){

		double gain = 0;

		for(int i = 0 ; i < gp.size() ; i++){
			double cases = gp.getCase(i);
			double controls = gp.getControl(i);
			double total = cases+controls;
			gain += (total/gp.getTotal())*this.entropy(cases,controls);
		}

		gain = this.entropy(gp.getTotalCases(),gp.getTotalControls()) - gain;

		return gain;
	}

	/**
	 * Calculates Entropy
	 * @param cases
	 * @param controls
	 * @return entropy
	 */
	private double entropy(double cases, double controls){
		double p = 0;
		if(cases+controls > 0){ 
			p = cases/(cases+controls);
			return Math.abs(-(p*Operation.log2(p))-((1-p)*Operation.log2(1-p)));
		}
		else return 1.0;
	}

	/**
	 * Calculates Chi-Square separability test
	 * @param gp
	 * @return chi
	 */
	private double calculateChi(GenotypePhenotypeTable gp){

		double chiScore = 0;

		for(int i = 0 ; i < gp.size() ; i++){
			double esp = (gp.getControl(i) + gp.getCase(i)) / 2;
			if(esp > 0) 
				chiScore += (Math.pow(gp.getControl(i) - esp,2) / esp) + 
				(Math.pow(gp.getCase(i) - esp,2) / esp);
		}

		return chiScore;
	}

	/**
	 * Calculates Gini Index
	 * @param gp
	 * @return gini_index
	 */
	private double calculateGini(GenotypePhenotypeTable gp){

		double classImpurity = 0;
		double sum0 = 0;
		double sum1 = 0;
		double size = gp.getTotal();

		for(int i = 0 ; i < gp.size() ; i++){
			sum0 += gp.getControl(i);
			sum1 += gp.getCase(i);
			if((gp.getControl(i) + gp.getCase(i)) > 0) 
				classImpurity += (Math.pow(gp.getControl(i),2)/(gp.getControl(i)+gp.getCase(i))) + 
				(Math.pow(gp.getCase(i),2)/(gp.getControl(i)+gp.getCase(i)));
		}

		double meanImpurity = (Math.pow(sum0,2)/size) + (Math.pow(sum1,2)/size);
		double giniIndex = (classImpurity - meanImpurity) / size;

		return giniIndex;
	}

	/**
	 * Calculates the Absolute Probability Difference (APD)
	 * @param gp
	 * @return apd
	 */
	private double calculateAPD(GenotypePhenotypeTable gp){

		double apd = 0;

		for(int i = 0 ; i < gp.size() ; i++){
			apd += Math.abs((gp.getControl(i)/gp.getTotalControls()) - (gp.getCase(i)/gp.getTotalCases()));
		}

		return apd;
	}

	// Results

	public ResultSet getResultSet() {
		return this.resultSet;
	}

	public Time getTime() {
		return this.time;
	}
	
}

