package basic;

import java.util.ArrayList;

/**
 * 
 * Represents a Priority Queue
 * 
 * @author egg
 *
 */
public class PriorityQueue{

	// Basic Elements
	private Combination[] comb;
	private int order;

	// Counting
	private int entryCounter = 0;

	public PriorityQueue(int size, int order){
		this.comb = new Combination[size];
		for(int i = 0 ; i < this.comb.length ; i++){
			this.comb[i] = new Combination(null,Double.NEGATIVE_INFINITY,-1);
		}
		this.order = order;
	}

	/**
	 * Adds another combination to the queue in the correct order.
	 * @param snps
	 * @param score
	 */
	public void add(String[] snps, double score){
		for(int i = 0 ; i < this.comb.length ; i++){
			if(score >= this.comb[i].getScore()){
				for(int j = this.comb.length-2 ; j >= i ; j--){
					this.comb[j+1] = this.comb[j].clone();
				}
				this.comb[i].setSnps(snps);
				this.comb[i].setScore(score);
				this.comb[i].setEntry(this.entryCounter);
				this.entryCounter++;
				break;
			}
		}
	}

	/**
	 * Search for the given combination and updates its score if it is higher than the actual combination
	 * score (realocating the combination). If the score is lower then it does nothing. If the combination
	 * dont exist it adds the combination.
	 * @param snps
	 * @param score
	 */
	public void update(String[] snps, double score){
		boolean exist = false;
		for(int i = 0 ; i < this.comb.length ; i++){
			if(this.comb[i].getWholeSnps() != null){
				boolean isEqual = true;
				for(int j = 0 ; j < this.order ; j++){
					if(!snps[j].equals(this.comb[i].getSnp(j))){
						isEqual = false;
						break;
					}
				}
				if(isEqual){
					if(score > this.comb[i].getScore()){
						for(int j = i ; j < this.comb.length-1 ; j++){
							this.comb[j] = this.comb[j+1].clone();
						}
					}
					else{
						exist = true;
					}
					break;
				}
			}
		}

		if(!exist){
			for(int i = 0 ; i < this.comb.length ; i++){
				if(score >= this.comb[i].getScore()){
					for(int j = this.comb.length-2 ; j >= i ; j--){
						this.comb[j+1] = this.comb[j].clone();
					}
					this.comb[i].setSnps(snps);
					this.comb[i].setScore(score);
					this.comb[i].setEntry(this.entryCounter);
					this.entryCounter++;
					break;
				}
			}
		}

	}

	/**
	 * Removes the null combinations and resizes the PriorityQueue
	 */
	public void trim(){
		ArrayList<Combination> newComb = new ArrayList<Combination>();
		for(int i = 0 ; i < this.comb.length ; i++){
			if(this.comb[i].getWholeSnps() != null) newComb.add(this.comb[i].clone());
			else break;
		}
		Combination[] newCombArray = new Combination[newComb.size()];
		for(int i = 0 ; i < newCombArray.length ; i++){
			newCombArray[i] = newComb.get(i);
		}
		this.comb = newCombArray;
	}

	/**
	 * Cut the priorityQueue in a certain position 
	 */
	public void cut(int position){
		Combination[] newCombArray = new Combination[position];
		for(int i = 0 ; i < position ; i++){
			newCombArray[i] = this.comb[i].clone();
		}
		this.comb = newCombArray;
	}
	
	/**
	 * Printable version of PriorityQueue
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("PriorityQueue:\n");
		for(int i = 0 ; i < this.size() ; i++){
			if(this.comb[i].getWholeSnps() == null) 
				sb.append((i+1)+"o -> NULL\n");
			else{
				sb.append((i+1)+"o -> ("+this.comb[i].getSnp(0));
				for(int j = 1 ; j < this.order ; j++){
					sb.append(","+this.comb[i].getSnp(j));
				}
				sb.append(") "+this.comb[i].getScore()+" ");
				sb.append(this.comb[i].getEntry()+"\n");
			}
		}
		return sb.toString();
	}

	// Getters and Setters

	public String getSnp(int rank, int snpIndex){
		return this.comb[rank].getSnp(snpIndex);
	}

	public String[] getSnps(int rank){
		return this.comb[rank].getWholeSnps();
	}

	public double getScore(int rank){
		return this.comb[rank].getScore();
	}
	
	public void setScore(int rank, double score){
		this.comb[rank].setScore(score);
	}

	public int getEntry(int rank){
		return this.comb[rank].getEntry();
	}

	public int getOrder(){
		return this.order;
	}

	public int size(){
		return this.comb.length;
	}

	/* Testing Main 
	public static void main(String[] args) {

		PriorityQueue pq = new PriorityQueue(5,2);
		String[] s1 = {"A0","A1"};
		String[] s2 = {"B0","B1"};
		String[] s3 = {"C0","C1"};
		String[] s4 = {"D0","D1"};
		String[] s5 = {"E0","E1"};
		String[] s6 = {"F0","F1"};
		String[] s7 = {"G0","G1"};
		String[] s8 = {"H0","H1"};
		String[] s9 = {"I0","I1"};
		pq.update(s1,10);
		pq.update(s2,21);
		pq.update(s3,21);
		pq.update(s4,13);
		pq.update(s5,14);
		
		pq.update(s4,23);
		pq.update(s4,50);
		pq.update(s4,7);

		System.out.println("Size = "+pq.size()+"\n");
		System.out.println(pq.toString());
	}
	*/

}
