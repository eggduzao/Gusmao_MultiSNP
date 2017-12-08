package method;

import analysis.Parameter;
import basic.Data;
import basic.GenotypePhenotypeTable;

/**
 * 
 * Contains the basic fields and methods for all combinatorial methods
 * 
 * @author egg
 *
 */
public class CombinatorialMethod{
	
	// Basic Elements
	protected Data data;
	protected Parameter parameter;
	protected int[] possCount;
	
	// Iteration Elements
	protected boolean start;
	protected int[] actualComb;
	
	// MDR Optimization
	protected int[][] totalComb;
	
	public CombinatorialMethod(Data data, Parameter parameter){
		
		// Initiate Basic Elements 
		this.data = data;
		this.parameter = parameter;
		this.possCount = this.data.possibleCount(this.parameter.COUNT_MISSING);
		
		// Iteration Elements
		this.actualComb = new int[this.parameter.ORDER];
		for(int i = 0 ; i < this.actualComb.length ; i++) this.actualComb[i] = 0;
		this.start = true;
		
	}

	/**
	 * Restarts the count.
	 */
	protected void restart(){
		for(int i = 0 ; i < this.actualComb.length ; i++){this.actualComb[i] = 0;}
		this.start = true;
	}
	
	/**
	 * Public method that make the count go to the next combination level
	 * @return boolean
	 */
	protected boolean next(){
		if(this.start){
			this.initFirstCombination();
			return true;
		}
		else return this.nextState();
	}
	
	/**
	 * Initialize the count.
	 */
	private void initFirstCombination(){
		for(int i = 0 ; i < this.actualComb.length ; i++){this.actualComb[i] = i;}
		this.start = false;
	}
	
	/**
	 * Makes an array go to the next interaction state
	 */
	private boolean nextState(){
		int index = -1;
		int featComp = this.data.features();
		for(int i = this.actualComb.length-1 ; i >= 0 ; i--){
			if(this.actualComb[i]+1 == featComp){
				this.actualComb[i] = 0;
				featComp--;
			}
			else{
				this.actualComb[i]++;
				index = i;
				break;
			}
		}
		if(index < 0) return false;
		else{
			int value = this.actualComb[index];
			for(int i = index+1 ; i < this.actualComb.length ; i++){
				value++;
				this.actualComb[i] = value;
			}
			return true;
		}
	}
	
	/**
	 * Returns the GenotypePhenotype table associated with the current count
	 * @return GenotypePhenotypeTable
	 */
	protected GenotypePhenotypeTable getGenoPheno(){
		
		int[] poss = new int[this.actualComb.length];
		for(int i = 0 ; i < poss.length ; i++){
			poss[i] = this.possCount[this.actualComb[i]];
		}
		
		GenotypePhenotypeTable next = new GenotypePhenotypeTable(poss);
		
		for(int i = 0 ; i < this.data.population() ; i++){
			double[] snps = new double[this.actualComb.length];
			for(int k = 0 ; k < snps.length ; k++){
				snps[k] = this.data.getData(i,this.actualComb[k]);
			}
			next.increase(snps,this.data.getClass(i));
		}
		
		return next;
	}
	
	/**
	 * Returns the GenotypePhenotype table associated with the current count
	 * for a given data sample
	 * @return GenotypePhenotypeTable
	 */
	protected GenotypePhenotypeTable getGenoPheno(Data data){
		
		int[] poss = new int[this.parameter.ORDER];
		for(int i = 0 ; i < poss.length ; i++){
			poss[i] = this.possCount[this.actualComb[i]];
		}
		
		GenotypePhenotypeTable next = new GenotypePhenotypeTable(poss);
		
		for(int i = 0 ; i < data.population() ; i++){
			double[] snps = new double[this.parameter.ORDER];
			for(int k = 0 ; k < snps.length ; k++){
				snps[k] = data.getData(i,this.actualComb[k]);
			}
			next.increase(snps,data.getClass(i));
		}
		
		return next;
	}
	
	/**
	 * Returns the GenotypePhenotype table associated with the current count
	 * for the given population interval p1 (inclusive) and p2 (exclusive)
	 * @return GenotypePhenotypeTable
	 */
	protected GenotypePhenotypeTable getGenoPheno(int p1, int p2){
		
		int[] poss = new int[this.parameter.ORDER];
		for(int i = 0 ; i < poss.length ; i++){
			poss[i] = this.possCount[this.actualComb[i]];
		}
		
		GenotypePhenotypeTable next = new GenotypePhenotypeTable(poss);
		
		for(int i = p1 ; i < p2 ; i++){
			double[] snps = new double[this.parameter.ORDER];
			for(int k = 0 ; k < snps.length ; k++){
				snps[k] = this.data.getData(i,this.actualComb[k]);
			}
			next.increase(snps,this.data.getClass(i));
		}
		
		return next;
	}
	
	/**
	 * Calculate the total combinations of p SNPs from a total of n snps.
	 * @param n
	 * @param p
	 * @return total number of combinations
	 */
	protected int comb(int n, int p){
		double ret = 1;
		for(int i = 0 ; i < p ; i++){
			ret *= (double)(n-i) / (double)(p-i);
		}
		return (int)ret;
	}
	
	/**
	 * Used for optimization purposes. It initiates the field totalComb that contains all the
	 * possible combinations of features.
	 */
	protected void initTotalComb(){
		
		this.totalComb = new int[this.comb(data.features(), this.parameter.ORDER)][this.actualComb.length];
		
		this.restart();
		int counter = 0;
		while(this.next()){
			this.totalComb[counter] = this.actualComb.clone();
		}
		
		this.restart();
	}
	
}
