package basic;

import java.util.ArrayList;
import java.util.Random;

import util.Operation;

/**
 * Represents the input data with (population) VS. (features + class).
 * Table = Table of values. Negative values represents missing data.
 * Classif = Vector of individuals classes.
 * Header = Vector containing each feature's name
 * Pathway = Vector containing each feature's pathway. It is an optional field.
 * @author egg
 *
 */
public class Data {

	// Basic Elements
	private double[][] table;
	private Classif[] classif;
	private String[] header;
	private String[] pathway;

	// Counting
	private double totalCases;
	private double totalControls;

	// Flags
	private boolean containPathway;

	public Data(int population, int features){
		this.table = new double[population][features];
		this.classif = new Classif[population];
		this.header = new String[features];
		this.pathway = new String[features];
		this.containPathway = false;
	}

	/**
	 * Return true if the data in location (i,j) is missing and false otherwise.
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isMissing(int i, int j){
		return this.getData(i,j)<0;
	}

	/**
	 * Shuffle individuals a couple of times.
	 * @param times
	 */
	public void shuffle(int times){
		Random random = new Random();
		for(int i = 0 ; i < times ; i++){
			int r1 = random.nextInt(this.table.length);
			int r2 = random.nextInt(this.table.length);
			double[] temp = this.table[r1].clone();
			this.table[r1] = this.table[r2].clone();
			this.table[r2] = temp;
			Classif classTemp = this.classif[r1];
			this.classif[r1] = this.classif[r2];
			this.classif[r2] = classTemp;
		}
	}

	/**
	 * Sifts the population of the data from pop1 (inclusive) to pop2 (exclusive)
	 * @param pop1
	 * @param pop2
	 * @return Data
	 */
	public Data siftPop(int pop1, int pop2){
		if(pop2 <= pop1 || pop1 < 0 || pop2 > this.population()) return null;
		Data data = new Data((pop2-pop1),this.features());
		double[][] table = new double[data.population()][this.features()];
		Classif[] classif = new Classif[data.population()];
		int cases = 0; int controls = 0;
		for(int i = pop1 ; i < pop2 ; i++){
			table[i-pop1] = this.table[i].clone();
			classif[i-pop1] = this.classif[i];
			if(this.classif[i]==Classif.CASE) cases++;
			else controls++;
		}
		data.setData(table);
		data.setClass(classif);
		data.setCases(cases);
		data.setControls(controls);
		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);
		return data;
	}

	/**
	 * Sifts the features of the data from feat1 (inclusive) to feat2 (exclusive)
	 * @param feat1
	 * @param feat2
	 * @return Data
	 */
	public Data siftFeat(int feat1, int feat2){
		if(feat2 <= feat1 || feat1 < 0 || feat2 > this.features()) return null;
		Data data = new Data(this.population(),feat2-feat1);
		int countJ = 0;
		for(int i = 0 ; i < this.population() ; i++){
			for(int j = feat1 ; j < feat2 ; j++){
				data.addData(i,countJ,this.getData(i,j));
				countJ++;
			}
			countJ = 0;
			data.addClass(i,this.getClass(i));
		}
		for(int j = feat1 ; j < feat2 ; j++){
			data.addHeader(countJ,this.getHeader(j));
			data.addPathway(countJ,this.getPathway(j));
			countJ++;
		}
		data.setContainPathway(this.containPathway);
		return data;
	}
	
	/**
	 * Removes the population interval specified in the parameters in this and returns them as a new Data
	 * @param pop1
	 * @param pop2
	 * @return Data
	 */
	public Data remove(int pop1, int pop2){
		
		if(pop2 <= pop1 || pop1 < 0 || pop2 > this.population()) return null;
		
		Data data = new Data((pop2-pop1),this.features());
		
		int thisCounter = 0;
		double[][] thisTable = new double[this.population()-data.population()][this.features()];
		Classif[] thisClassif = new Classif[this.population()-data.population()];
		int thisCase = 0;
		int thisControl = 0;
		
		int dataCounter = 0;
		double[][] dataTable = new double[data.population()][this.features()];
		Classif[] dataClassif = new Classif[data.population()];
		int dataCase = 0;
		int dataControl = 0;
		
		for(int i = 0 ; i < this.population() ; i++){
			if(pop1 <= i && i < pop2){
				dataTable[dataCounter] = this.table[i].clone();
				dataClassif[dataCounter] = this.classif[i];
				if(this.classif[i]==Classif.CASE) dataCase++;
				else dataControl++;
				dataCounter++;
			}
			else{
				thisTable[thisCounter] = this.table[i].clone();
				thisClassif[thisCounter] = this.classif[i];
				if(this.classif[i]==Classif.CASE) thisCase++;
				else thisControl++;
				thisCounter++;
			}
		}
		
		this.table = thisTable;
		this.classif = thisClassif;
		this.totalCases = thisCase;
		this.totalControls = thisControl;
		
		data.setData(dataTable);
		data.setClass(dataClassif);
		data.setCases(dataCase);
		data.setControls(dataControl);
		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);
		return data;
	}
	
	/**
	 * Return all of this data except the given interval
	 * @param pop1
	 * @param pop2
	 * @return Data
	 */
	public Data allBut(int pop1, int pop2){
		
		if(pop2 <= pop1 || pop1 < 0 || pop2 > this.population()) return null;
		
		Data data = new Data(this.population()-(pop2-pop1),this.features());
		
		int dataCounter = 0;
		double[][] dataTable = new double[data.population()][this.features()];
		Classif[] dataClassif = new Classif[data.population()];
		int dataCase = 0;
		int dataControl = 0;
		
		for(int i = 0 ; i < pop1 ; i++){
			dataTable[dataCounter] = this.table[i].clone();
			dataClassif[dataCounter] = this.classif[i];
			if(this.classif[i]==Classif.CASE) dataCase++;
			else dataControl++;
			dataCounter++;
		}
		for(int i = pop2 ; i < this.population() ; i++){
			dataTable[dataCounter] = this.table[i].clone();
			dataClassif[dataCounter] = this.classif[i];
			if(this.classif[i]==Classif.CASE) dataCase++;
			else dataControl++;
			dataCounter++;
		}
		
		data.setData(dataTable);
		data.setClass(dataClassif);
		data.setCases(dataCase);
		data.setControls(dataControl);
		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);
		return data;
	}

	/**
	 * Returns a bootstrap sample of the data.
	 * @return Data
	 */
	public Data bootstrap(){

		Data data = new Data(this.population(),this.features());
		Random r = new Random();

		int[] popSort = new int[this.population()];
		for(int i = 0 ; i < popSort.length ; i++){
			popSort[i] = r.nextInt(this.population());
		}

		for(int i = 0 ; i < popSort.length ; i++){
			for(int j = 0 ; j < data.features() ; j++){
				data.addData(i,j,this.getData(popSort[i],j));	
			}
			data.addClass(i,this.getClass(popSort[i]));
		}

		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);

		return data;
	}

	/**
	 * Returns a bootstrap sample of the data according to [Dong et al. 2007]
	 * It does not account for correct classif.
	 * @return Data
	 */
	public Data dongBootstrap(){

		Data data = new Data(this.population(),this.features());
		Random r = new Random();

		int[] popSort = new int[this.population()];
		for(int i = 0 ; i < popSort.length ; i++){
			popSort[i] = r.nextInt(this.population());
		}

		for(int i = 0 ; i < popSort.length ; i++){
			for(int j = 0 ; j < data.features() ; j++){
				data.addData(i,j,this.getData(popSort[i],j));	
			}
		}

		data.setClass(this.getWholeClass().clone());
		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);

		return data;
	}

	/**
	 * Fits the data:
	 * 1. Search for attributes that does not follow a 0,1,2,... order criterion.
	 * 2. Changes their value so that the order is respected.
	 */
	public void fit(){

		for(int j = 0 ; j < this.features() ; j++){

			ArrayList<Double> list = new ArrayList<Double>();

			for(int i = 0 ; i < this.population() ; i++){
				if(!this.isMissing(i,j)){
					boolean exist = false;
					for(int k = 0 ; k < list.size() ; k++){
						if(list.get(k) == this.getData(i,j)){
							exist = true;
							break;
						}
					}
					if(!exist){
						list.add(this.getData(i,j));
					}
				}
			}

			int listSize = list.size();
			for(int check = 0 ; check < listSize ; check++){
				boolean exist = false;
				int index = 0;
				for(int k = 0 ; k < list.size() ; k++){
					if(check == list.get(k)){
						exist = true;
						index = k;
						break;
					}
				}
				if(exist){
					list.remove(index);
				}
				else{
					double biggest = -1;
					int biggestIndex = 0;
					for(int k = 0 ; k < list.size() ; k++){
						if(biggest < list.get(k)){
							biggest = list.get(k);
							biggestIndex = k;
						}
					}
					for(int i = 0 ; i < this.population() ; i++){
						if(this.getData(i,j) == biggest){
							this.addData(i,j,check);
						}
					}
					list.remove(biggestIndex);
				}
			}

		}

	}

	/**
	 * Accounts for the number of possible values that each feature may assume.
	 * @param countMissingData
	 * @return int[]
	 */
	public int[] possibleCount(boolean countMissingData){

		int[] possCount = new int[this.features()];

		for(int j = 0 ; j < this.features() ; j++){

			ArrayList<Double> list = new ArrayList<Double>();
			boolean hadMissing = false;

			for(int i = 0 ; i < this.population() ; i++){
				if(this.isMissing(i,j)) hadMissing = true;
				boolean exist = false;
				for(int k = 0 ; k < list.size() ; k++){
					if(list.get(k) == this.getData(i,j)){
						exist = true;
						break;
					}
				}
				if(!exist){
					list.add(this.getData(i,j));
				}

			}
			if(!countMissingData && hadMissing)
				possCount[j] = list.size()-1;
			else possCount[j] = list.size(); 
		}

		return possCount;
	}

	/**
	 * Sums the data passed as parameter with this
	 * @param data
	 */
	public void sum(Data data){

		double[][] newTable = new double[this.population()+data.population()][this.features()];
		Classif[] newClassif = new Classif[this.population()+data.population()];

		double[][] dataTable = data.getWholeData();
		Classif[] dataClassif = data.getWholeClass();

		for(int i = 0 ; i < this.table.length ; i++){
			newTable[i] = this.table[i];
			newClassif[i] = this.classif[i];
		}
		for(int i = 0 ; i < dataTable.length ; i++){
			newTable[this.population()+i] = dataTable[i];
			newClassif[this.population()+i] = dataClassif[i];
		}

		this.table = newTable;
		this.classif = newClassif;
		this.totalCases = this.totalCases + data.getCases();
		this.totalControls = this.totalControls + data.getControls();

	}

	/**
	 * Returns a printable version of the data
	 * @return String
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Data:\n");
		sb.append(this.header[0]);
		for(int j = 1 ; j < this.header.length ; j++){
			sb.append(" "+this.header[j]);
		} sb.append("\n");
		if(this.containPathway){
			sb.append(this.pathway[0]);
			for(int j = 1 ; j < this.pathway.length ; j++){
				sb.append(" "+this.pathway[j]);
			} sb.append("\n");
		}
		for(int i = 0 ; i < this.population() ; i++){
			for(int j = 0 ; j < this.features() ; j++){
				sb.append(this.getData(i,j)+" ");
			}
			sb.append(this.getClass(i)+"\n");
		}
		return sb.toString();
	}
	
	/**
	 * Returns a new Data object.
	 * @return Data
	 */
	@Override
	public Data clone(){
		Data data = new Data(this.population(),this.features());
		data.setData(Operation.cloneMatrix(this.getWholeData()));
		data.setClass(this.getWholeClass().clone());
		data.setHeader(this.getWholeHeader().clone());
		data.setPathway(this.getWholePathway().clone());
		data.setContainPathway(this.containPathway);
		return data;
	}

	// Getters and Setters:

	public void addData(int i, int j, double value){
		this.table[i][j] = value;
	}

	public void setData(double[][] data){
		this.table = data;
	}

	public double getData(int i, int j){
		return this.table[i][j];
	}

	public double[][] getWholeData(){
		return this.table;
	}

	public void addClass(int i, Classif value){
		this.classif[i] = value;
		if(value == Classif.CONTROL) this.totalControls++;
		else this.totalCases++;
	}

	public void setClass(Classif[] classif){
		this.classif = classif;
	}

	public Classif getClass(int i){
		return this.classif[i];
	}

	public Classif[] getWholeClass(){
		return this.classif;
	}

	public void addHeader(int i, String value){
		this.header[i] = value;
	}

	public void setHeader(String[] header){
		this.header = header;
	}

	public String getHeader(int i){
		return this.header[i];
	}

	public String[] getHeader(int[] snps){
		String[] ret = new String[snps.length];
		for(int i = 0 ; i < ret.length ; i++){
			ret[i] = this.header[snps[i]];
		}
		return ret;
	}

	public String[] getWholeHeader(){
		return this.header;
	}

	public void addPathway(int i, String value){
		this.pathway[i] = value;
		if(!this.containPathway) this.containPathway = true;
	}

	public void setPathway(String[] pathway){
		this.pathway = pathway;
		if(!this.containPathway) this.containPathway = true;
	}

	public String getPathway(int i){
		return this.pathway[i];
	}

	public String[] getWholePathway(){
		return this.pathway;
	}

	public int population(){
		return this.table.length;
	}

	public int features(){
		return this.table[0].length;
	}

	public boolean containPathway(){
		return this.containPathway;
	}

	public void setContainPathway(boolean value){
		this.containPathway = value;
	}
	
	public void setCases(int cases){
		this.totalCases = cases;
	}

	public void setControls(int controls){
		this.totalControls = controls;
	}
	
	public double getCases(){
		return this.totalCases;
	}

	public double getControls(){
		return this.totalControls;
	}

	/* Testing Main 
	public static void main(String[] args) {

		Data data = new Data(5,3);
		data.addHeader(0,"X0"); data.addHeader(1,"X1"); data.addHeader(2,"X2");
		data.addPathway(0,"apoptosis"); data.addPathway(1,"dna"); data.addPathway(2,"rna");
		data.addData(0,0,0); data.addData(0,1,0); data.addData(0,2,0); data.addClass(0,Classif.CONTROL);
		data.addData(1,0,1); data.addData(1,1,10); data.addData(1,2,100); data.addClass(1,Classif.CONTROL);
		data.addData(2,0,2); data.addData(2,1,20); data.addData(2,2,200); data.addClass(2,Classif.CASE);
		data.addData(3,0,3); data.addData(3,1,30); data.addData(3,2,300); data.addClass(3,Classif.CASE);
		data.addData(4,0,4); data.addData(4,1,40); data.addData(4,2,400); data.addClass(4,Classif.CONTROL);

		Data data2 = new Data(3,3);
		data2.addHeader(0,"X0"); data2.addHeader(1,"X1"); data2.addHeader(2,"X2");
		data2.addPathway(0,"apoptosis"); data2.addPathway(1,"dna"); data2.addPathway(2,"rna");
		data2.addData(0,0,5); data2.addData(0,1,50); data2.addData(0,2,500); data2.addClass(0,Classif.CONTROL);
		data2.addData(1,0,6); data2.addData(1,1,60); data2.addData(1,2,600); data2.addClass(1,Classif.CASE);
		data2.addData(2,0,7); data2.addData(2,1,70); data2.addData(2,2,700); data2.addClass(2,Classif.CONTROL);

		System.out.println("All But");
		Data allBut = data.allBut(0,5);
		System.out.println(allBut.toString());
		
		System.out.println("Sum");
		data.sum(data2);
		System.out.println(data.toString());

		System.out.println("Dong");
		Data dong = data.dongBootstrap();
		System.out.println(dong.toString());

		System.out.println("Bootstrap");
		Data boot = data.bootstrap();
		System.out.println(boot.toString());

		System.out.println("Fit");
		data.fit();
		System.out.println(data.toString());

		System.out.println("Clone");
		Data clone = data.clone();
		clone.addData(0,0,80);
		System.out.println(data);
		System.out.println(clone);

		System.out.println("popSift");
		Data popSift = data.siftPop(3,5);
		System.out.println(popSift);

		System.out.println("featSift");
		Data featSift = data.siftFeat(1,3);
		System.out.println(featSift);

		System.out.println("Shuffle");
		data.shuffle(100);
		System.out.println(data.toString());

		System.out.println("possible count:");
		int[] poss = data.possibleCount(false);
		for(int i = 0 ; i < poss.length ; i++){
			System.out.print(poss[i]+" ");
		} System.out.println("\n");

	}
	*/

}
