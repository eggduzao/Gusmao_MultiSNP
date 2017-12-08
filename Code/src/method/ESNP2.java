package method;

import util.Operation;

import constants.MethodConstant;

import analysis.Parameter;
import basic.Data;
import basic.GenotypePhenotypeTable;
import basic.ResultSet;
import basic.Time;

/**
 * 
 * Perform the ESNP2, as described in [Dong et al. 2007].
 * 
 * @author egg
 *
 */
public class ESNP2 extends CombinatorialMethod implements Method{
	
	// Results
	private ResultSet resultSet;
	private Time time;
	
	// Time
	private long initTime;
	private long loopTime;
	private long singleTime;
	private long combTime;
	private long totalTime;

	// Auxiliary
	private double[] singleEntropy;
	
	/**
	 * Call the method
	 * @param data
	 * @param parameter
	 */
	public ESNP2(Data data, Parameter parameter){

		super(data,parameter);

		// Initialization
		this.initialization();
		
		// Single Entropy calculation
		this.calculateSingleEntropy();
		
		// Main Loop - Analyze each feature combination at the given order
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
		this.resultSet = new ResultSet(MethodConstant.ESNP2_SCORES,
				this.comb(this.data.features(),this.parameter.ORDER),this.parameter.ORDER);
		
		// Time
		this.time = new Time();
		
		// Initial Shuffle - Shuffles the data in the beginning
		//this.data.shuffle(this.parameter.ESNP2_SHUFFLE);
		
		long t2 = System.currentTimeMillis();
		this.initTime = (t2-t1);
		
	}
	
	/**
	 * Put the evaluated time in the Time Object
	 */
	private void printTime(){
		this.totalTime = this.initTime + this.loopTime + this.singleTime + this.combTime;
		this.time.add(MethodConstant.ESNP2_TIME[MethodConstant.ESNP2_TIME_INIT],this.initTime);
		this.time.add(MethodConstant.ESNP2_TIME[MethodConstant.ESNP2_TIME_LOOP],this.loopTime);
		this.time.add(MethodConstant.ESNP2_TIME[MethodConstant.ESNP2_TIME_SINGLE],this.singleTime);
		this.time.add(MethodConstant.ESNP2_TIME[MethodConstant.ESNP2_TIME_COMB],this.combTime);
		this.time.add(MethodConstant.ESNP2_TIME[MethodConstant.ESNP2_TIME_TOTAL],this.totalTime);
	}

	/**
	 * Evaluate entropies of all single SNPs
	 */
	private void calculateSingleEntropy(){
		
		long t1 = System.currentTimeMillis();

		this.singleEntropy = new double[this.data.features()];
		
		for(int f = 0 ; f < this.data.features() ; f++){
			
			int[] poss = {this.possCount[f]};
			GenotypePhenotypeTable gp = new GenotypePhenotypeTable(poss);
			
			for(int i = 0 ; i < this.data.population() ; i++){
				double[] snp = {this.data.getData(i,f)};
				gp.increase(snp,this.data.getClass(i));
			}
			
			this.singleEntropy[f] = this.gain(gp);
			
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
			
			double interactGain = this.gain(gp);
			
			double minH = 1;
			for(int k = 0 ; k < this.actualComb.length ; k++){
				if(this.singleEntropy[this.actualComb[k]] < minH) minH = this.singleEntropy[this.actualComb[k]];
			}

			double score = (minH - interactGain) / minH;
		
			this.resultSet.add(MethodConstant.ESNP2_SCORES[MethodConstant.ESNP2_DELTA_R],this.data.getHeader(this.actualComb),score);
			
			loop = System.currentTimeMillis();
			this.combTime += (loop-t1);
			
		}

	}
	
	/**
	 * Calculates the information gain ratio without subtracting from total base gain
	 * @param gp
	 * @return double gain
	 */
	private double gain(GenotypePhenotypeTable gp){

		double gain = 0;

		for(int i = 0 ; i < gp.size() ; i++){
			double cases = gp.getCase(i);
			double controls = gp.getControl(i);
			double total = cases+controls;
			gain += (total/gp.getTotal())*this.entropy(cases,controls);
		}

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
	
	// Results

	public ResultSet getResultSet() {
		return this.resultSet;
	}

	public Time getTime() {
		return this.time;
	}
	
}

