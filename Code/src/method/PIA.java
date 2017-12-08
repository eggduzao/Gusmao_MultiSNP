package method;

import constants.MethodConstant;

import basic.*;

import analysis.Parameter;

/**
 * 
 * Perform the Polymorphism Interaction Analysis Method v.2, as described in [Mechanic et al. 2008].
 * 
 * @author egg
 *
 */
public class PIA extends CombinatorialMethod implements Method{

	// Results
	private ResultSet resultSet;
	private Time time;
	
	// Time
	private long initTime;
	private long loopTime;
	private long splitTime;
	private long contTime;
	private long ssTime;
	private long overTime;
	private long totalTime;

	/**
	 * Call the method
	 * @param data
	 * @param parameter
	 */
	public PIA(Data data, Parameter parameter){

		super(data,parameter);
		
		// Initialization
		this.initialization();
		
		// Main Loop - Analyze each feature combination at the given order
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
		this.resultSet = new ResultSet(MethodConstant.PIA_SCORES,
				this.comb(this.data.features(),this.parameter.ORDER),this.parameter.ORDER);
		
		// Time
		this.time = new Time();
		
		long t2 = System.currentTimeMillis();
		this.initTime = t2 - t1;
	}
	
	/**
	 * Put the evaluated time in the Time Object
	 */
	private void printTime(){
		this.totalTime = this.initTime + this.loopTime + this.splitTime + this.contTime  + 
		 				 this.ssTime + this.overTime;
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_INIT],this.initTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_LOOP],this.loopTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_SPLIT],this.splitTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_CONT],this.contTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_SS],this.ssTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_OVERALL],this.overTime);
		this.time.add(MethodConstant.PIA_TIME[MethodConstant.PIA_TIME_TOTAL],this.totalTime);
	}
	
	/**
	 * Main Loop - Iterate on each possible and valid combination of SNPs
	 */
	private void mainLoop(){
		
		long loop = System.currentTimeMillis();
		
		// Iterate on all SNP combinations
		while(this.next()){
			
			// GP table associated with the actual combination
			GenotypePhenotypeTable gp = this.getGenoPheno();
			
			// Imbalanced data cutoff based on RATSL parameter
			if((Math.max(gp.getTotalCases(),gp.getTotalControls()) /
			   Math.min(gp.getTotalCases(),gp.getTotalControls())) <= this.parameter.PIA_RATSL){
				
				long t1 = System.currentTimeMillis();
				this.loopTime += (t1-loop);
				
				// Evaluate Contingency-Based Scores
				this.contingencyScores(gp);
				
				long t2 = System.currentTimeMillis();
				this.contTime += (t2-t1);
				
				// Evaluate Single-Shot Scores
				this.singleShotScores(gp);
				
				long t3 = System.currentTimeMillis();
				this.ssTime += (t3-t2);
				
				// Evaluate Split-Based Scores
				this.splitScores(gp);
				
				loop = System.currentTimeMillis();
				this.splitTime += (loop-t3);
				
			}
			
		}
		
	}
	
	/**
	 * Evaluate all Contingency-Table Based scores:
	 * 1 - Correct Rate
	 * 2 - Sensitivity + Specificity
	 * 3 - Positive Predictive Value + Negative Predictive Value
	 * 4 - Risk Ratio
	 * 5 - Odds Ratio
	 * And writes them into the ResultSet
	 * @param GenotypePhenotypeTable
	 */
	private void contingencyScores(GenotypePhenotypeTable gp){
		
		ContingencyTable totalCont = new ContingencyTable();
		
		// Iterate on each ntime
		for(int ntime = 0 ; ntime < this.parameter.PIA_NTIME ; ntime++){
			
			this.data.shuffle(this.parameter.PIA_SHUFFLE);
			int quant = (int)(this.parameter.PIA_FRACT * this.data.population());
			
			// Iterate on each cross-validation interval
			for(int c = 0 ; c < (1/this.parameter.PIA_FRACT) ; c++){
				
				GenotypePhenotypeTable test = this.getGenoPheno((c*quant),((c+1)*quant));
				GenotypePhenotypeTable train = gp.clone();
				train.remove(test);
				
				// Evaluate the contingency table and sums it to total contingency table
				ContingencyTable cont = this.evaluateContingencyTable(train, test);
				totalCont.accumulate(cont);
				
			}
			
		}
		
		// Store all the first contingency-based scores
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_CORRECT],this.data.getHeader(this.actualComb),totalCont.correctRate());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_SENS_SPEC],this.data.getHeader(this.actualComb),totalCont.sensitivity()+totalCont.specificity());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_PPV_NPV],this.data.getHeader(this.actualComb),totalCont.ppv()+totalCont.npv());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_RISK],this.data.getHeader(this.actualComb),totalCont.riskRatio());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_ODDS],this.data.getHeader(this.actualComb),totalCont.oddsRatio());
		
	}
	
	/**
	 * Calculate single-shot scores based on all Contingency-Table Based scores:
	 * 1 - Correct Rate
	 * 2 - Sensitivity + Specificity
	 * 3 - Positive Predictive Value + Negative Predictive Value
	 * 4 - Risk Ratio
	 * 5 - Odds Ratio
	 * And writes them into the ResultSet
	 * The single shot score is calculated with the whole dataset just once
	 * @param GenotypePhenotypeTable
	 */
	private void singleShotScores(GenotypePhenotypeTable gp){
		
		// Evaluate single shot scores
		ContingencyTable totalCont = this.evaluateSingleShot(gp);
		
		// Store the single shot scores
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_CORRECT_SS],this.data.getHeader(this.actualComb),totalCont.correctRate());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_SENS_SPEC_SS],this.data.getHeader(this.actualComb),totalCont.sensitivity()+totalCont.specificity());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_PPV_NPV_SS],this.data.getHeader(this.actualComb),totalCont.ppv()+totalCont.npv());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_RISK_SS],this.data.getHeader(this.actualComb),totalCont.riskRatio());
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_ODDS_SS],this.data.getHeader(this.actualComb),totalCont.oddsRatio());
			
	}
	
	/**
	 * Evaluate all Split-Based scores:
	 * 1 - Gini Index
	 * 2 - Absolute Probability Difference
	 * And writes them into the ResultSet
	 * @param GenotypePhenotypeTable
	 */
	private void splitScores(GenotypePhenotypeTable gp){
		
		// Evaluate the split-based scores
		double giniScore = this.calculateGini(gp);
		double apdScore = this.calculateAPD(gp);
		
		// Store the split-based scores
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_GINI],this.data.getHeader(this.actualComb),giniScore);
		this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_APD],this.data.getHeader(this.actualComb),apdScore);
		
	}
	
	/**
	 * Evaluate the Overall Score that correspond to the summation of all the previous scores for the given
	 * combination after the normalization of each score set so that the highest value is set to 50
	 * And write it into the ResultSet
	 */
	private void overallScore(){
		
		long t1 = System.currentTimeMillis();

		String[] piaScores = null;
		if(this.parameter.PIA_HIDE_SS || this.data.getCases() == this.data.getControls() || this.parameter.PIA_IFRACT)
			piaScores = MethodConstant.PIA_SCORES_OVERALL_WITHOUT_SS.clone();
		else piaScores = MethodConstant.PIA_SCORES_OVERALL.clone();
		
		double[][] tableScore = new double[this.resultSet.size()][piaScores.length];
		String[][] tableSnp = new String[this.resultSet.size()][this.parameter.ORDER];
		boolean first = true;
		
		double[] max = new double[piaScores.length];
		for(int i = 0 ; i < max.length ; i++){max[i] = this.resultSet.getScore(piaScores[i],0);};
		
		for(int scoreCount = 0 ; scoreCount < piaScores.length ; scoreCount++){
			String name = piaScores[scoreCount];
			for(int rank = 0 ; rank < this.resultSet.size() ; rank++){
				tableScore[this.resultSet.getEntry(name,rank)][scoreCount] = this.resultSet.getScore(name,rank);
				if(first) tableSnp[this.resultSet.getEntry(name,rank)] = this.resultSet.getSnps(name,rank);	
			}
			if(first) first = false;	
		}
		
		for(int rank = 0 ; rank < this.resultSet.size() ; rank++){
			double sum = 0;
			for(int scoreCount = 0 ; scoreCount < piaScores.length ; scoreCount++){
				sum+= ((50*tableScore[rank][scoreCount]) / max[scoreCount]);	
			}
			this.resultSet.add(MethodConstant.PIA_SCORES[MethodConstant.PIA_OVERALL],tableSnp[rank],sum);
		}
		
		long t2 = System.currentTimeMillis();
		this.overTime = (t2-t1);
		
	}
	
	private ContingencyTable evaluateSingleShot(GenotypePhenotypeTable gp){
		
		ContingencyTable cont = new ContingencyTable();
		
		for(int i = 0 ; i < gp.size() ; i++){
			double cases = gp.getCase(i); 
			double controls = gp.getControl(i);
			if(controls!=cases){
				if(Math.max(controls,cases)==controls){
					if(controls == cases+1){
						cont.incTN(controls/2);
						cont.incFP(controls/2);
						cont.incFN(cases);
					}
					else{
						cont.incTN(controls);
						cont.incFN(cases);
					}

				}
				else{
					if(cases == controls+1){
						cont.incTP(cases/2);
						cont.incFN(cases/2);
						cont.incFP(controls);
					}
					else{
						cont.incTP(cases);
						cont.incFP(controls);
					}
				}
			}
			else{
				cont.incFP(controls);
				cont.incFN(cases);
			}
		}
		
		return cont;
	}
	
	/**
	 * Count for the number of TP, FP, TN, FN, with the test data and the train data.
	 * Creates a Contingency-Table based on these values.
	 * @param GenotypePhenotypeTable
	 * @param GenotypePhenotypeTable
	 * @return ContingencyTable
	 */
	private ContingencyTable evaluateContingencyTable(GenotypePhenotypeTable train, GenotypePhenotypeTable test){

		ContingencyTable cont = new ContingencyTable();

		// In the case we are using the train data
		if(this.parameter.PIA_ITRAIN){

			for(int i = 0 ; i < train.size() ; i++){

				double no_controls_train = train.getControl(i);
				double no_cases_train = train.getCase(i);
				double no_controls_train_toCompare = no_controls_train;
				double no_cases_train_toCompare = no_cases_train;

				// If we are using fractional occupations
				if(this.parameter.PIA_IFRACT){
					no_controls_train_toCompare = no_controls_train / this.data.getControls();
					no_cases_train_toCompare = no_cases_train / this.data.getCases();
				}

				// Performing a maximum rules
				if(this.parameter.PIA_LOOTR==0){
					if(no_controls_train_toCompare!=no_cases_train_toCompare){
						if(Math.max(no_controls_train_toCompare,no_cases_train_toCompare)==
							no_controls_train_toCompare){
							cont.incTN(no_controls_train);
							cont.incFN(no_cases_train);
						}
						else{
							cont.incTP(no_cases_train);
							cont.incFP(no_controls_train);
						}
					}
					else{
						cont.incTP(no_cases_train/2);
						cont.incFN(no_cases_train/2);
						cont.incTN(no_controls_train/2);
						cont.incFP(no_controls_train/2);
					}
				}
				// Performing a leave-one-out procedure
				else if(this.parameter.PIA_LOOTR==1){
					if(no_controls_train_toCompare!=no_cases_train_toCompare){
						if(Math.max(no_controls_train_toCompare,no_cases_train_toCompare)==
							no_controls_train_toCompare){
							if(no_controls_train_toCompare == no_cases_train_toCompare+1){
								cont.incTN(no_controls_train/2);
								cont.incFP(no_controls_train/2);
								cont.incFN(no_cases_train);
							}
							else{
								cont.incTN(no_controls_train);
								cont.incFN(no_cases_train);
							}

						}
						else{
							if(no_cases_train_toCompare == no_controls_train_toCompare+1){
								cont.incTP(no_cases_train /2);
								cont.incFN(no_cases_train /2);
								cont.incFP(no_controls_train);
							}
							else{
								cont.incTP(no_cases_train);
								cont.incFP(no_controls_train);
							}
						}
					}
					else{
						cont.incFP(no_controls_train);
						cont.incFN(no_cases_train);
					}
				}
				// Performing PIA v.1
				else{
					if(no_controls_train_toCompare+no_cases_train_toCompare>1){
						if(Math.max(no_controls_train_toCompare,no_cases_train_toCompare)==
							no_controls_train_toCompare || no_cases_train_toCompare==no_controls_train_toCompare){
							cont.incTN(no_controls_train);
							cont.incFN(no_cases_train);
						}
						else{
							cont.incTP(no_cases_train);
							cont.incFP(no_controls_train);
						}
					}
					else{
						cont.incFP(no_controls_train);
						cont.incFN(no_cases_train);
					}

				}

			}
		}

		// Using the test data
		for(int i = 0 ; i < test.size() ; i++){

			double no_controls_train = train.getControl(i);
			double no_cases_train = train.getCase(i);
			double no_controls_test = test.getControl(i);
			double no_cases_test = test.getCase(i);
			double no_controls_train_toCompare = no_controls_train;
			double no_cases_train_toCompare = no_cases_train;

			if(this.parameter.PIA_IFRACT){
				no_controls_train_toCompare = no_controls_train / this.data.getControls();
				no_cases_train_toCompare = no_cases_train / this.data.getCases();
			}

			if(no_controls_train_toCompare!=no_cases_train_toCompare){
				if(no_controls_train_toCompare > no_cases_train_toCompare){
					cont.incTN(no_controls_test);
					cont.incFN(no_cases_test);
				}
				else{
					cont.incTP(no_cases_test);
					cont.incFP(no_controls_test);
				}
			}
			else if(no_controls_train_toCompare!=0){
				if(this.parameter.PIA_ITRAIN && this.parameter.PIA_LOOTR==-1){
					cont.incTP(no_cases_test);
					cont.incTN(no_controls_test);
				}
				else{
					cont.incTP(no_cases_test/2);
					cont.incFN(no_cases_test/2);
					cont.incTN(no_controls_test/2);
					cont.incFP(no_controls_test/2);
				}
			}
			else{
				cont.incFP(no_controls_test);
				cont.incFN(no_cases_test);
			}

		}

		return cont;

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
