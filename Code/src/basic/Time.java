package basic;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * 
 * Stores the time for each part of each methods algorithm
 * 
 * @author egg
 *
 */
public class Time {

	// Basic Elements
	private Hashtable<String,Long> time;
	
	public Time(){
		this.time = new Hashtable<String, Long>();
	}
	
	/**
	 * Creates the string version of the Time Object
	 */
	@Override
	public String toString(){
		Enumeration<String> keys = this.time.keys();
		StringBuffer sb = new StringBuffer();
		sb.append("Time (in milisseconds):\n");
		while(keys.hasMoreElements()){
			String name = keys.nextElement();
			sb.append(name+": "+this.time.get(name)+" ms.\n");
		}
		return sb.toString();
	}
	
	// Getters and Setters
	
	public void add(String name, long time){
		this.time.put(name,time);
	}
	
	public long get(String name){
		return this.time.get(name);
	}
	
	/* Testing Main 
	public static void main(String[] args) {
		Time time = new Time();
		time.add("a",10);
		time.add("b",20);
		time.add("c",30);
		time.add("d",40);
		time.add("e",50);
		System.out.println(time.get("c"));
		System.out.println(time);
	}
	*/
	
}
