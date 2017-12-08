package basic;

import java.util.Random;

import util.Operation;

/**
 * Represents a Genotype-Phenotype table
 * It is a k-dimensional table with the snp in the first position of the array representing the most significant
 * digit of a string. So it grows in reverse-significancy:
 * 000 - 100 - 200 - 010 - 110 - 210 - etc.. (assuming 0,1,2 possibilities for each digit)
 * @author egg
 *
 */
public class GenotypePhenotypeTable {

	// Basic Elements
	// 0 for control
	// 1 for case
	private double[][] table;
	private int[] poss;
	private int order;
	private int size;
	
	// Counting
	private double totalCases;
	private double totalControls;
	
	private GenotypePhenotypeTable(){}
	
	public GenotypePhenotypeTable(int[] possibleValues){
		this.poss = possibleValues;
		this.order = possibleValues.length;
		this.size = 1;
		for(int i = 0 ; i < this.poss.length ; i++){
			this.size *= this.poss[i];
		}
		this.table = new double[this.size][2];
	}
	
	/**
	 * Increase the table for a certain SNP combination and a Classif status.
	 * @param snps
	 * @param classif
	 */
	public void increase(double[] snps,Classif classif){
		int index = 0;
		int pow = 1;
		boolean ok = true;
		for(int i = 0 ; i < snps.length ; i++){
			if(snps[i]<0){
				ok = false;
				break;
			}
			index += pow * snps[i];
			pow *= this.poss[i];
		}
		if(ok){
			int col = 0;
			if(classif == Classif.CASE){
				col = 1;
				this.totalCases++;
			}
			else this.totalControls++;
			this.table[index][col]++;
		}
	}
	
	/**
	 * Returns a new GenotypePhenotypeTable containing a number 'quant' of random selected data from this,
	 * that data will be removed from this ContingencyTable
	 * @param quant
	 * @return GenotypePhenotypeTable
	 */
	public GenotypePhenotypeTable removeTestSet(int quant){
		
		GenotypePhenotypeTable test = new GenotypePhenotypeTable(this.poss);
		Random r = new Random();
		
		for(int q = 0 ; q < quant ; q++){
			
			int randomPosition = r.nextInt(this.size);
			int randomClass = r.nextInt(2);
			int randomPositionMark = randomPosition;
			boolean findData = false;
			
			while(!findData){
				
				if(randomClass == 0){
					if(this.getControl(randomPosition) > 0){
						findData = true;
						break;
					}
					else if(this.getCase(randomPosition) > 0){
						findData = true;
						randomClass = 1;
						break;
					}
				}
				else{
					if(this.getCase(randomPosition) > 0){
						findData = true;
						break;
					}
					else if(this.getControl(randomPosition) > 0){
						findData = true;
						randomClass = 0;
						break;
					}
				}
				
				randomPosition++;
				if(randomPosition == this.size) randomPosition = 0;
				if(randomPosition == randomPositionMark) break;
			}
			
			if(findData){
				if(randomClass == 0){
					this.decControl(randomPosition);
					test.incControl(randomPosition);
				}
				else{
					this.decCase(randomPosition);
					test.incCase(randomPosition);
				}
			}
			else break;
			
		}
		
		return test;
	}
	
	/**
	 * Removes the given GenotypePhenotypeTable data from this
	 * @param quant
	 * @return GenotypePhenotypeTable
	 */
	public void remove(GenotypePhenotypeTable gp){
		for(int i = 0 ; i < this.size ; i++){
			this.table[i][0] -= gp.getTable()[i][0];
			this.table[i][1] -= gp.getTable()[i][1];
		}
		this.totalCases -= gp.getTotalCases();
		this.totalControls -= gp.getTotalControls();
	}
	
	/**
	 * Accumulates this object with the data provenient from a new gp passed as parameter
	 * @param gp
	 */
	public void accumulate(GenotypePhenotypeTable gp){
		
		for(int i = 0 ; i < gp.size() ; i++){
			this.setCase(i,this.getCase(i)+gp.getCase(i));
			this.setControl(i,this.getControl(i)+gp.getControl(i));
		}
		
	}
	
	/**
	 * Sums this GenotypePhenotypeTable with another one passed as parameter and return the summation
	 * @param gp
	 * @return GenotypePhenotypeTable
	 */
	public GenotypePhenotypeTable sum(GenotypePhenotypeTable gp){
		
		GenotypePhenotypeTable ret = new GenotypePhenotypeTable(this.poss);
		
		for(int i = 0 ; i < gp.size() ; i++){
			ret.setCase(i,this.getCase(i)+gp.getCase(i));
			ret.setControl(i,this.getControl(i)+gp.getControl(i));
		}
		
		return ret;
	}
	
	/**
	 * Return the printable version of Genotype-Phenotype Table
	 * @return String
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Genotype-Phenotype Table: Cases = "+this.totalCases+", Controls = "+this.totalControls);
		for(int i = 0 ; i < this.size ; i++){
			if(this.poss.length == 2 && i!=0){
				if(i%this.poss[0]==0) sb.append("\n");
				else sb.append(" ");
			}
			else sb.append("\n");
			sb.append("("+this.getControl(i)+","+this.getCase(i)+")");
		} sb.append("\n");
		return sb.toString();
	}
	
	/**
	 * Create a new GenotypePhenotypeTable
	 * @return GenotypePhenotypeTable
	 */
	@Override
	public GenotypePhenotypeTable clone(){
		GenotypePhenotypeTable gp = new GenotypePhenotypeTable();
		gp.setTable(Operation.cloneMatrix(this.table));
		gp.setOrder(this.order);
		gp.setPoss(this.poss.clone());
		gp.setSize(this.size);
		gp.setTotalCases(this.totalCases);
		gp.setTotalControls(this.totalControls);
		return gp;
	}
	
	// Getters and Setters
	
	public double getCase(int index){
		return this.table[index][1];
	}
	
	public double getControl(int index){
		return this.table[index][0];
	}
	
	public double[][] getTable() {
		return table;
	}

	public int[] getPoss() {
		return poss;
	}

	public int getOrder() {
		return order;
	}
	
	public void incCase(int index){
		this.table[index][1]++;
		this.totalCases++;
	}
	
	public void incControl(int index){
		this.table[index][0]++;
		this.totalControls++;
	}
	
	public void decCase(int index){
		this.table[index][1]--;
		this.totalCases--;
	}
	
	public void decControl(int index){
		this.table[index][0]--;
		this.totalControls--;
	}
	
	public void setCase(int index, double value){
		this.totalCases -= (this.table[index][1]);
		this.table[index][1] = value;
		this.totalCases += (this.table[index][1]);
	}
	
	public void setControl(int index, double value){
		this.totalControls -= (this.table[index][0]);
		this.table[index][0] = value;
		this.totalControls += (this.table[index][0]);
	}
	
	public void setTable(double[][] table) {
		this.table = table;
	}

	public void setPoss(int[] poss) {
		this.poss = poss;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
	public int size(){
		return this.size;
	}
	
	public void setSize(int size){
		this.size = size;
	}
	
	public double getTotalCases(){
		return this.totalCases;
	}
	
	public double getTotalControls(){
		return this.totalControls;
	}
	
	public double getTotal(){
		return this.totalCases + this.totalControls;
	}
	
	public void setTotalCases(double totalCases){
		this.totalCases = totalCases;
	}
	
	public void setTotalControls(double totalControls){
		this.totalControls = totalControls;
	}
	
	/* Testing Main
	public static void main(String[] args) {
		
		int[] poss = {4,3,2};
		
		GenotypePhenotypeTable gp = new GenotypePhenotypeTable(poss);
		
		double[] s000 = {0,0,0}; double[] s001 = {0,0,1}; double[] s010 = {0,1,0}; double[] s011 = {0,1,1}; double[] s020 = {0,2,0}; double[] s021 = {0,2,2};
		double[] s100 = {1,0,0}; double[] s101 = {1,0,1}; double[] s110 = {1,1,0}; double[] s111 = {1,1,1}; double[] s120 = {1,2,0}; double[] s121 = {1,2,1};
		double[] s200 = {2,0,0}; double[] s201 = {2,0,1}; double[] s210 = {2,1,0}; double[] s211 = {2,1,1}; double[] s220 = {2,2,0}; double[] s221 = {2,2,1};
		double[] s300 = {3,0,0}; double[] s301 = {3,0,1}; double[] s310 = {3,1,0}; double[] s311 = {3,1,1}; double[] s320 = {3,2,0}; double[] s321 = {3,2,1};
		
		gp.increase(s000,Classif.CONTROL);
		gp.increase(s311,Classif.CONTROL);
		
		GenotypePhenotypeTable gp2 = gp.clone();
		gp2.increase(s000,Classif.CONTROL);
		gp2.increase(s000,Classif.CONTROL);
		gp2.increase(s000,Classif.CONTROL);
		gp2.increase(s000,Classif.CONTROL);
		gp2.increase(s000,Classif.CONTROL);
		
		System.out.println(gp.clone().toString());
		System.out.println(gp2.clone().toString());
		
		gp2.remove(gp);
		System.out.println(gp2);
	}
	*/
	
}
