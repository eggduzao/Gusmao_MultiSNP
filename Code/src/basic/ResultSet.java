package basic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * 
 * Represents all the results from a method
 * 
 * @author egg
 *
 */
public class ResultSet {

	// Basic Elements
	private Hashtable<String,PriorityQueue> set;
	private String[] names;
	private int pqSize;

	public ResultSet(String[] names, int pqSize, int order){
		this.set = new Hashtable<String,PriorityQueue>();
		for(int i = 0 ; i < names.length ; i++){
			this.set.put(names[i],new PriorityQueue(pqSize,order));
		}
		this.names = names;
		this.pqSize = pqSize;
	}
	
	/**
	 * Set the smaller value to 0 making all the values non negative.
	 */
	public void setSmallerTo0(){
		for(int i = 0 ; i < this.names.length ; i++){
			PriorityQueue pq = this.set.get(this.names[i]);
			double smallerValue = 0;
			for(int j = this.size()-1 ; j >= 0 ; j--){
				if(pq.getSnps(j)!=null){
					smallerValue = pq.getScore(j);
					break;
				}
			}
			if(smallerValue < 0){
				for(int j = 0 ; j < this.size() ; j++){
					pq.setScore(j,pq.getScore(j)-smallerValue);
				}
			}
		}
	}

	/**
	 * Returns the correspondent list of Tuples from a list of Combinations.
	 * Returns only the first ntop tuples and cutts off NULL values.
	 * @param name
	 * @param f
	 * @return ArrayList<Tuple>
	 */
	public ArrayList<Tuple> getTupleSet(String name, DecimalFormat f, int ntop){

		ArrayList<Tuple> ret = new ArrayList<Tuple>();
		
		int firstNullIndex = this.size();
		for(int i = 0 ; i < this.size() ; i++){
			String[] snps = this.set.get(name).getSnps(i);
			if(snps==null){
				firstNullIndex = i;
				break;
			}
		}
		
		int cutIndex = Math.min(ntop,firstNullIndex);
		
		for(int i = 0 ; i < cutIndex ; i++){
			StringBuffer snpsBuffer = new StringBuffer();
			String[] snps = this.set.get(name).getSnps(i);
			snpsBuffer.append(snps[0]);
			for(int j = 1 ; j < snps.length ; j++){
				snpsBuffer.append(" "+snps[j]);
			}
			ret.add(new Tuple(snpsBuffer.toString(),f.format(this.set.get(name).getScore(i))));
		}

		return ret;
	}
	
	/**
	 * Printable version of ResultSet.
	 */
	@Override
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("Result Set:\n"+this.names[0]);
		for(int k = 1 ; k < this.names.length ; k++){
			sb.append(" "+this.names[k]);
		}
		sb.append("\n");
		for(int i = 0 ; i < this.size() ; i++){
			sb.append((i+1)+"o -> ");
			for(int k = 0 ; k < this.names.length ; k++){
				if(this.set.get(this.names[k]).getSnps(i) == null) 
					sb.append("NULL  ");
				else{
					sb.append("("+this.set.get(this.names[k]).getSnp(i,0));
					for(int j = 1 ; j < this.set.get(this.names[k]).getSnps(i).length ; j++){
						sb.append(","+this.set.get(this.names[k]).getSnp(i,j));
					}
					sb.append(") "+this.set.get(this.names[k]).getScore(i)+"  ");
					sb.append(this.set.get(this.names[k]).getEntry(i)+"  ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	// Getters and Setters
	
	public String[] getNames() {
		return names;
	}

	public void add(String name, String[] snps, double score){
		this.set.get(name).add(snps, score);
	}

	public void update(String name, String[] snps, double score){
		this.set.get(name).update(snps, score);
	}

	public String getSnp(String name, int rank, int snpIndex){
		return this.set.get(name).getSnp(rank,snpIndex);
	}

	public String[] getSnps(String name, int rank){
		return this.set.get(name).getSnps(rank);
	}

	public double getScore(String name, int rank){
		return this.set.get(name).getScore(rank);
	}

	public int getEntry(String name, int rank){
		return this.set.get(name).getEntry(rank);
	}

	public int size(){
		return this.pqSize;
	}

	/* Testing Main
	public static void main(String[] args) {

		String[] names = {"%correct","gini"};
		ResultSet rs = new ResultSet(names,9,2);

		String[] s1 = {"A0","A1"};
		String[] s2 = {"B0","B1"};
		String[] s3 = {"C0","C1"};
		String[] s4 = {"D0","D1"};
		String[] s5 = {"E0","E1"};
		String[] s6 = {"F0","F1"};
		String[] s7 = {"G0","G1"};
		String[] s8 = {"H0","H1"};
		String[] s9 = {"I0","I1"};

		rs.add(names[0], s1, 50);
		rs.add(names[0], s2, 40);
		rs.add(names[0], s3, 30);
		rs.add(names[0], s4, 20);
		rs.add(names[0], s5, 10);
		rs.add(names[0], s6, 0);
		rs.add(names[0], s7, -10);
		rs.add(names[0], s8, -20);
		rs.add(names[0], s9, -30);

		rs.add(names[1], s4, -5);
		rs.add(names[1], s5, -4);
		rs.add(names[1], s6, -3);
		rs.add(names[1], s7, -2);
		rs.add(names[1], s8, -9);
		rs.add(names[1], s9, -110);

		
		DecimalFormat f = new DecimalFormat("##0.000000");

		ArrayList<Tuple> ts = rs.getTupleSet(names[1], f, 44);
		for(int i = 0 ; i < ts.size() ; i++){
			System.out.println(ts.get(i).getSnpComb()+" "+ts.get(i).getScore());
		}
		
		
		rs.setSmallerTo0();

		System.out.println(rs.toString());

	}
	*/

}
