package io;

import java.util.ArrayList;
import java.util.Hashtable;

import constants.SystemConstant;

import basic.Classif;
import basic.Data;

/**
 * 
 * The class reads the data from a pathway or from an Arquivo Object and then creates a Data object.
 * 
 * @author egg
 *
 */
public class Reader {

	// Basic Elements
	private Data data;
	
	/**
	 * Constructor 1 - Receive a String: This string may be
	 * 				   a) A path to the original data
	 * 				   b) The data itself on String format
	 * @param data
	 * @param usePathway
	 */
	public Reader(String data, boolean usePathway){
		
		boolean isPath = true;
		for(int i = 0 ; i < data.length() ; i++){
			if(data.charAt(i)=='\n'){
				isPath = false;
				break;
			}
		}
		
		Reader r = null;
		
		if(isPath){
			Arquivo arquivo = new Arquivo(data,SystemConstant.DEFAULT_FILE);
			r = new Reader(arquivo,usePathway);
		}
		else{
			this.read(this.cleanStringEnd(data), usePathway);
		}
		
		if(r!=null) this.data = r.getData();
		
	}
	
	/**
	 * Constructor 2 - Receive a Arquivo: Represent the file containing the data
	 * @param data
	 * @param usePathway
	 */
	public Reader(Arquivo arquivo, boolean usePathway){
		
		StringBuffer sb = new StringBuffer();
		
		while(!arquivo.isEndOfFile()){
			char c = arquivo.readChar();
			sb.append(c);
		}
		
		this.read(this.cleanStringEnd(sb.toString()),usePathway);
		
		arquivo.close();
		
	}

	/**
	 * Execution of data reading
	 * @param data
	 * @param usePathway
	 */
	private void read(String data, boolean usePathway){
		
		// Basic data count
		int dataIndexCount = 0;
		char dataRead = ' ';

		// Objects that keeps the raw read from file, prepared to write into Data object
		ArrayList<String> rawHeader = new ArrayList<String>();
		ArrayList<String> rawPathway = new ArrayList<String>();
		ArrayList<ArrayList<String>> rawData = new ArrayList<ArrayList<String>>();

		// Read header lines
		StringBuffer cluster = new StringBuffer();
		while(true){
			dataRead = data.charAt(dataIndexCount);
			dataIndexCount++;
			if(dataRead=='\n') break;
			boolean isSeparation = false;
			for(char c : SystemConstant.DATA_SEPARATION){
				if(dataRead==c){
					isSeparation = true;
					break;
				}
			}
			if(isSeparation){
				rawHeader.add(cluster.toString());
				cluster = new StringBuffer();
			}
			else cluster.append(dataRead);
		}
		rawHeader.add(cluster.toString());
		dataRead = data.charAt(dataIndexCount);
		
		// Read pathway lines, if there is any
		if(usePathway){
			cluster = new StringBuffer();
			while(true){
				dataRead = data.charAt(dataIndexCount);
				dataIndexCount++;
				if(dataRead=='\n') break;
				boolean isSeparation = false;
				for(char c : SystemConstant.DATA_SEPARATION){
					if(dataRead==c){
						isSeparation = true;
						break;
					}
				}
				if(isSeparation){ 
					rawPathway.add(cluster.toString());
					cluster = new StringBuffer();
				}
				else cluster.append(dataRead);
			}
			rawPathway.add(cluster.toString());
			dataRead = data.charAt(dataIndexCount);
		}
		
		// Count the population and the features totals
		int population = 0;
		int features = 0;
		int indexJ = 0;
		ArrayList<Double> dataCounting = new ArrayList<Double>();
		boolean first = true;
		cluster = new StringBuffer();
		ArrayList<Hashtable<String,Double>> table = new ArrayList<Hashtable<String,Double>>();
		boolean end = false;
		while(true){
			ArrayList<String> vec = new ArrayList<String>();
			while(true){
				if(dataIndexCount == data.length()){
					end = true;
					break;
				}
				dataRead = data.charAt(dataIndexCount);
				dataIndexCount++;
				if(dataRead=='\n') break;
				boolean isSeparation = false;
				for(char c : SystemConstant.DATA_SEPARATION){
					if(dataRead==c){
						isSeparation = true;
						break;
					}
				}
				if(isSeparation){ 
					if(cluster.length() > 0){
						if(first){
							table.add(new Hashtable<String, Double>());
							dataCounting.add(0.0);
							features++;
						}
						if(!table.get(indexJ).containsKey(cluster.toString())){
							table.get(indexJ).put(cluster.toString(),dataCounting.get(indexJ));
							dataCounting.set(indexJ,dataCounting.get(indexJ)+1);
						}
						indexJ++;
						vec.add(cluster.toString());
						cluster = new StringBuffer();
					}
				}
				else cluster.append(dataRead);
			}
			if(first){
				table.add(new Hashtable<String, Double>());
				dataCounting.add(0.0);
				features++;
			}
			if(!table.get(indexJ).containsKey(cluster.toString())){
				table.get(indexJ).put(cluster.toString(),dataCounting.get(indexJ));
				dataCounting.set(indexJ,dataCounting.get(indexJ)+1);
			}
			population++;
			first = false;
			indexJ = 0;
			vec.add(cluster.toString());
			cluster = new StringBuffer();
			rawData.add(vec);
			if(end) break;
			dataRead = data.charAt(dataIndexCount);
		}

		// Write Vector Data into Data Object
		Data actualData = new Data(population,features-1);
		for(int i = 0 ; i < rawData.size() ; i++){
			for(int j = 0 ; j < rawData.get(i).size() ; j++){
				double d = table.get(j).get(rawData.get(i).get(j));
				if(j==rawData.get(i).size()-1){
					Classif classif = Classif.CONTROL;
					for(String s : SystemConstant.CASE_SYMBOLS){
						if(s.equals(rawData.get(i).get(j))){
							classif = Classif.CASE;
							break;
						}
					}	
					actualData.addClass(i,classif);
				}
				else actualData.addData(i,j,d);
			}
		}
		for(int j = 0 ; j < actualData.features() ; j++){
			actualData.addHeader(j,rawHeader.get(j));
			if(usePathway) actualData.addPathway(j,rawPathway.get(j));
		}

		this.data = actualData;
	}
	
	/**
	 * Auxiliary method that clears special characters from the end of a String
	 * @param s
	 * @return
	 */
	public String cleanStringEnd(String s){
		int index = 0;
		for(int i = s.length()-1 ; i >= 0 ; i--){
			boolean cut = false;
			if(s.charAt(i)=='\n') cut = true;
			for(char c : SystemConstant.DATA_SEPARATION){
				if(s.charAt(i)==c){
					cut = true;
					break;
				}
			}
			if(!cut){
				index = i+1;
				break;
			}
		}
		return s.substring(0,index);
	}
	
	// Getters and Setters
	
	public Data getData(){
		return this.data;
	}

	/* Testing main 
	public static void main(String[] args) {
		
		Reader r = new Reader("C:/Documents and Settings/Sony/Desktop/TESTES/Dados/lit.txt",true);
		Data d = r.getData();

		System.out.println(d.toString());

	}
	*/

}
