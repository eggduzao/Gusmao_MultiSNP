package method;

import constants.MethodConstant;

import basic.*;

import analysis.Parameter;


/**
 * 
 * Performs the Multifactor Dimensionality Reduction (MDR) as described in [Hahn et al. 2003] and
 * [Ritchie et al. 2001]. This is the "core" MDR, based on the balanced accuracy.
 * 
 * @author egg
 *
 */
public class MDR extends CombinatorialMethod implements Method{

	// Results
	private ResultSet resultSet;
	private double classificationBalAccMean;
	private double predictionBalAccMean;
	private Time time;
	
	// Time
	private long initTime;
	private long loopTime;
	private long classTime;
	private long predTime;
	private long totalTime;

	/**
	 * Call the method
	 * @param data
	 * @param parameter
	 */
	public MDR(Data data, Parameter parameter){

		super(data,parameter);

		// Initialization
		this.initialization();

		// Main Loop - n-fold cross-validation. The combination iteration is inside this loop
		this.mainLoop();
		
		// Print Time
		this.printTime();

	}

	/**
	 * Initialization of the Algorithm
	 */
	private void initialization(){

		long t1 = System.currentTimeMillis();
		
		// ResultSet
		this.resultSet = new ResultSet(MethodConstant.MDR_SCORES,
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
		this.totalTime = this.initTime + this.loopTime + this.classTime + this.predTime;
		this.time.add(MethodConstant.MDR_TIME[MethodConstant.MDR_TIME_INIT],this.initTime);
		this.time.add(MethodConstant.MDR_TIME[MethodConstant.MDR_TIME_LOOP],this.loopTime);
		this.time.add(MethodConstant.MDR_TIME[MethodConstant.MDR_TIME_CLAS],this.classTime);
		this.time.add(MethodConstant.MDR_TIME[MethodConstant.MDR_TIME_PRED],this.predTime);
		this.time.add(MethodConstant.MDR_TIME[MethodConstant.MDR_TIME_TOTAL],this.totalTime);
	}
	
	/**
	 * Main Loop - Iterate on each possible and valid combination of SNPs
	 * This loop is in reverse order from what MDR was designed because of performance reasons
	 */
	private void mainLoop(){

		// Keeping track of the classification accuracy of the best models and the prediction accuracy
		double classifTrack = 0; double classifTotal = 0;
		double predictTrack = 0; double predictTotal = 0;

		// Main Loop -> Number of times the cross-validation procedure is performed
		for(int ntime = 0 ; ntime < this.parameter.MDR_NTIME ; ntime++){

			long t1 = System.currentTimeMillis();
			
			// Shuffles and alternates the data
			this.data.shuffle(this.parameter.MDR_SHUFFLE);
			//this.preprocessing();

			// Keeping track of the best model
			double bestClassifRate = 0;
			int[] bestSnpComb = null;
			GenotypePhenotypeTable bestGpTrain = null;
			GenotypePhenotypeTable bestGpTest = null;
			
			// Cross Validation criterion
			int crossValidTimes = (int)(1.0/this.parameter.MDR_FRACT);
			int amountData = (int)(this.data.population()*this.parameter.MDR_FRACT);
			
			long t2 = System.currentTimeMillis();
			this.loopTime += (t2-t1);
			
			boolean first = true;
			
			// Cross Validation Loop -> Perform n-fold cross-validation
			for(int cross = 0 ; cross < crossValidTimes ; cross++){
				
				long loop1 = System.currentTimeMillis();

				// Dividing data in test and train
				Data train = this.data.allBut((cross*amountData),((cross+1)*amountData));
				
				this.restart();
				
				long loop2 = System.currentTimeMillis();
				this.loopTime += (loop2-loop1);
				
				// Combination Loop -> Iterate on each possible feature combination
				while(this.next()){
					
					long t3 = System.currentTimeMillis();
					this.classTime += (t3-loop2);
					
					GenotypePhenotypeTable gpTrain = this.getGenoPheno(train);
					
					if(!first) t3 = System.currentTimeMillis();
					
					//Evaluating classification rate
					double classificationRate = this.classification(gpTrain);

					// Updating the given snp combination (see update method for details)
					this.resultSet.update(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_CLASS],this.data.getHeader(this.actualComb),classificationRate);
					
					// Keeping track of the best classification rate
					if(classificationRate > bestClassifRate){
						bestClassifRate = classificationRate;
						bestSnpComb = this.actualComb.clone();
						bestGpTrain = gpTrain;
						Data test = this.data.siftPop((cross*amountData),((cross+1)*amountData));
						bestGpTest = this.getGenoPheno(test);;
					}
					
					loop2 = System.currentTimeMillis();
					this.classTime += (loop2-t3);

				}

				// ClassifTrack will be used to evaluate the classification mean
				classifTrack += bestClassifRate;
				classifTotal++;
				
				first = false;
				
				//long t3 = System.currentTimeMillis();
				//this.classTime += (t3-loop2);
				
			}
			
			long t4 = System.currentTimeMillis();
			
			// Evaluating the prediction rate
			double predictionRate = this.prediction(bestGpTrain, bestGpTest);
			
			// PredictTrack will be used to evaluate the prediction mean
			predictTrack += predictionRate;
			predictTotal++;
			
			this.resultSet.add(MethodConstant.MDR_SCORES[MethodConstant.MDR_BAL_ACC_PRED],this.data.getHeader(bestSnpComb),predictionRate);
			
			long t5 = System.currentTimeMillis();
			this.predTime += (t5-t4);
			
		}
		
		this.classificationBalAccMean = classifTrack / classifTotal;
		this.predictionBalAccMean = predictTrack / predictTotal;

	}

	/**
	 * Populates a contingency table
	 * @param train
	 * @return double classification rate
	 */
	private double classification(GenotypePhenotypeTable train){

		ContingencyTable cont = new ContingencyTable();

		for(int j = 0 ; j < train.size() ; j++){
			double cases = train.getCase(j);
			double controls = train.getControl(j);
			if(cases+controls > 0){
				if(cases == 0){
					cont.incTN(controls);
				}
				else if(controls == 0){
					cont.incTP(cases);
				}
				else{
					if(cases/controls >= this.parameter.MDR_T){
						cont.incTP(cases);
						cont.incFP(controls);
					}
					else{
						cont.incTN(controls);
						cont.incFN(cases);
					}
				}
			}
		}
		return cont.balancedAccuracy();
	}

	/**
	 * Evaluates the prediction rate of the test data with the train data
	 * @param train
	 * @param test
	 * @return double prediction rate
	 */
	private double prediction(GenotypePhenotypeTable train, GenotypePhenotypeTable test){

		ContingencyTable cont = new ContingencyTable();

		for(int j = 0 ; j < test.size() ; j++){
			double cases_test = test.getCase(j);
			double controls_test = test.getControl(j);
			double cases_train = train.getCase(j);
			double controls_train = train.getControl(j);
			if(controls_train > 0 && cases_train/controls_train >= this.parameter.MDR_T){
				cont.incTP(cases_test);
				cont.incFP(controls_test);
			}
			else{
				cont.incTN(controls_test);
				cont.incFN(cases_test);
			}
		}

		return cont.balancedAccuracy();
	}	

	// Results

	public ResultSet getResultSet() {
		return this.resultSet;
	}

	public Time getTime() {
		return this.time;
	}

	// This is the mean over all best classification models, even the ones that were discarted
	public double getClassificationBalAccMean(){
		return this.classificationBalAccMean;
	}

	// This is the mean over all best prediction models
	public double getPredictionBalAccMean(){
		return this.predictionBalAccMean;
	}

}
