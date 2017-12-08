package analysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import constants.MethodConstant;

import basic.Data;
import basic.ResultSet;
import basic.Score;
import basic.Tuple;

public class PathwayAnalysis {

	// Basic Elements
	private String method;
	private Data data;
	private ResultSet rs;

	// Return
	private ArrayList<Score> pathwayAnalysis;

	public PathwayAnalysis(Data data, ResultSet rs, String method){

		this.method = method;
		this.data = data;
		this.rs = rs;
		this.pathwayAnalysis = new ArrayList<Score>();

		this.doPathwayAnalysis();

	}

	/**
	 * Pathway Analysis consist of summing the scores for each pathway, each time its corresponding SNP is present
	 * in some combination. Then it is divided by the summation over all pathway scores.
	 */
	private void doPathwayAnalysis(){

		ArrayList<ArrayList<Tuple>> scoresTrack = new ArrayList<ArrayList<Tuple>>();

		String[] scoreNames = this.rs.getNames();

		for(String metric : scoreNames){

			Hashtable<String,String> snpToPath = new Hashtable<String,String>();
			Hashtable<String,Double> htScores = new Hashtable<String,Double>();

			for(int i = 0 ; i < this.data.features() ; i++){
				snpToPath.put(this.data.getHeader(i),this.data.getPathway(i));
				htScores.put(this.data.getPathway(i), 0.);
			}

			double summation = 0;

			for(int i = 0 ; i < this.rs.size() ; i++){

				String[] snps = this.rs.getSnps(metric,i);
				if(snps!=null){
					double score = this.rs.getScore(metric,i);
	
					for(String s : snps){
						htScores.put(snpToPath.get(s),htScores.get(snpToPath.get(s))+score);
						summation+=score;
					}
				}

			}

			Hashtable<String,Double> newHtScores = new Hashtable<String,Double>();
			Enumeration<String> keys = htScores.keys();
			while(keys.hasMoreElements()){
				String s = keys.nextElement();
				newHtScores.put(s,(htScores.get(s)*100)/summation);
			}

			scoresTrack.add(this.sortHashtable(newHtScores));

		}

		if(this.method.equals(MethodConstant.PIA)){
			ArrayList<Tuple> correctList = scoresTrack.get(0);
			ArrayList<Tuple> ssList = scoresTrack.get(1);
			ArrayList<Tuple> ppvNpvList = scoresTrack.get(2);
			ArrayList<Tuple> riskList = scoresTrack.get(3);
			ArrayList<Tuple> oddsList = scoresTrack.get(4);
			ArrayList<Tuple> giniList = scoresTrack.get(5);
			ArrayList<Tuple> apdList = scoresTrack.get(6);
			ArrayList<Tuple> overallList = scoresTrack.get(7);
			for(int i = 0 ; i < correctList.size() ; i++){
				Score s = new Score();
				s.setPiaCorrectSnps(correctList.get(i).getT1());
				s.setPiaCorrectScore(correctList.get(i).getT2());
				s.setPiaSsSnps(ssList.get(i).getT1());
				s.setPiaSsScore(ssList.get(i).getT2());
				s.setPiaPpvNpvSnps(ppvNpvList.get(i).getT1());
				s.setPiaPpvNpvScore(ppvNpvList.get(i).getT2());
				s.setPiaRiskSnps(riskList.get(i).getT1());
				s.setPiaRiskScore(riskList.get(i).getT2());
				s.setPiaOddsSnps(oddsList.get(i).getT1());
				s.setPiaOddsScore(oddsList.get(i).getT2());
				s.setPiaGiniSnps(giniList.get(i).getT1());
				s.setPiaGiniScore(giniList.get(i).getT2());
				s.setPiaApdSnps(apdList.get(i).getT1());
				s.setPiaApdScore(apdList.get(i).getT2());
				s.setPiaOverallSnps(overallList.get(i).getT1());
				s.setPiaOverallScore(overallList.get(i).getT2());
				this.pathwayAnalysis.add(s);
			}
		}
		else if(this.method.equals(MethodConstant.MDR)){
			ArrayList<Tuple> balancedAccList = scoresTrack.get(0);
			for(int i = 0 ; i < balancedAccList.size() ; i++){
				Score s = new Score();
				s.setMdrBalancedAccSnps(balancedAccList.get(i).getT1());
				s.setMdrBalancedAccScore(balancedAccList.get(i).getT2());
				this.pathwayAnalysis.add(s);
			}
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			ArrayList<Tuple> deltaRList = scoresTrack.get(0);
			for(int i = 0 ; i < deltaRList.size() ; i++){
				Score s = new Score();
				s.setEsnp2DeltaRSnps(deltaRList.get(i).getT1());
				s.setEsnp2DeltaRScore(deltaRList.get(i).getT2());
				this.pathwayAnalysis.add(s);
			}
		}
		else if(this.method.equals(MethodConstant.MASS)){
			ArrayList<Tuple> gainList = scoresTrack.get(0);
			ArrayList<Tuple> chiList = scoresTrack.get(1);
			ArrayList<Tuple> giniList = scoresTrack.get(2);
			ArrayList<Tuple> apdList = scoresTrack.get(3);
			ArrayList<Tuple> overallList = scoresTrack.get(4);
			for(int i = 0 ; i < gainList.size() ; i++){
				Score s = new Score();
				s.setMassGainSnps(gainList.get(i).getT1());
				s.setMassGainScore(gainList.get(i).getT2());
				s.setMassChiSnps(chiList.get(i).getT1());
				s.setMassChiScore(chiList.get(i).getT2());
				s.setMassGiniSnps(giniList.get(i).getT1());
				s.setMassGiniScore(giniList.get(i).getT2());
				s.setMassApdSnps(apdList.get(i).getT1());
				s.setMassApdScore(apdList.get(i).getT2());
				s.setMassOverallSnps(overallList.get(i).getT1());
				s.setMassOverallScore(overallList.get(i).getT2());
				this.pathwayAnalysis.add(s);
			}
		}

	}


	private ArrayList<Tuple> sortHashtable(Hashtable<String,Double> hash){

		ArrayList<Tuple> ret = new ArrayList<Tuple>();
		DecimalFormat f = new DecimalFormat("###.##");

		ArrayList<String> keys = new ArrayList<String>(hash.keySet());
		ArrayList<Double> scores = new ArrayList<Double>();
		for(String s : keys){
			scores.add(hash.get(s));
			ret.add(new Tuple(s,f.format(hash.get(s))+"%"));
		}

		for(int i = 0 ; i < scores.size() - 1 ; i++){
			for(int j = 0 ; j < scores.size() - i - 1 ; j++){
				if(scores.get(j)<scores.get(j+1)){

					double temp = scores.get(j).doubleValue();
					scores.set(j,scores.get(j+1));
					scores.set(j+1,temp);

					Tuple tempT = ret.get(j).clone();
					ret.set(j,ret.get(j+1));
					ret.set(j+1,tempT);

				}
			}
		}

		return ret;
	}

	// Getters and Setters

	public ArrayList<Score> getPathwayAnalysis() {
		return pathwayAnalysis;
	}

	public void setPathwayAnalysis(ArrayList<Score> pathwayAnalysis) {
		this.pathwayAnalysis = pathwayAnalysis;
	}

	/* Testing Main
	public static void main(String[] args) {

		Data data = new Data(2,5);
		data.addHeader(0,"X0"); data.addHeader(1,"X1"); data.addHeader(2,"X2"); data.addHeader(3,"X3"); data.addHeader(4,"X4");
		data.addPathway(0,"a"); data.addPathway(1,"b"); data.addPathway(2,"a"); data.addPathway(3,"c"); data.addPathway(4,"b");
		
		String[] scores = {"scoring1","scoring2","scoring3","scoring4","scoring5","scoring6","scoring7","scoring8"};
		
		ResultSet rs = new ResultSet(scores,5,2);
		
		String[] c1 = {"X0","X1"}; String[] c2 = {"X0","X2"}; String[] c3 = {"X0","X3"}; String[] c4 = {"X1","X2"}; String[] c5 = {"X1","X4"};
		rs.add(scores[0],c1,11); rs.add(scores[0],c2,41); rs.add(scores[0],c3,15); rs.add(scores[0],c4,21); rs.add(scores[0],c5,53);
		rs.add(scores[1],c1,12); rs.add(scores[1],c2,31); rs.add(scores[1],c3,15); rs.add(scores[1],c4,21); rs.add(scores[1],c5,53);
		rs.add(scores[2],c1,13); rs.add(scores[2],c2,41); rs.add(scores[2],c3,15); rs.add(scores[2],c4,21); rs.add(scores[2],c5,53);
		rs.add(scores[3],c1,21); rs.add(scores[3],c2,31); rs.add(scores[3],c3,15); rs.add(scores[3],c4,21); rs.add(scores[3],c5,53);
		rs.add(scores[4],c1,51); rs.add(scores[4],c2,41); rs.add(scores[4],c3,15); rs.add(scores[4],c4,21); rs.add(scores[4],c5,53);
		rs.add(scores[5],c1,71); rs.add(scores[5],c2,51); rs.add(scores[5],c3,15); rs.add(scores[5],c4,21); rs.add(scores[5],c5,53);
		rs.add(scores[6],c1,31); rs.add(scores[6],c2,46); rs.add(scores[6],c3,15); rs.add(scores[6],c4,21); rs.add(scores[6],c5,53);
		rs.add(scores[7],c1,91); rs.add(scores[7],c2,1); rs.add(scores[7],c3,15); rs.add(scores[7],c4,21); rs.add(scores[7],c5,53);
		
		PathwayAnalysis analysis = new PathwayAnalysis(data,rs,MethodConstant.PIA);
		ArrayList<Score> res = analysis.getPathwayAnalysis();
		
		for(int i = 0 ; i < res.size() ; i++){
			System.out.print(res.get(i).getPiaCorrectSnps()+" -> "+res.get(i).getPiaCorrectScore()+"  ");
			System.out.print(res.get(i).getPiaSsSnps()+" -> "+res.get(i).getPiaSsScore()+"  ");
			System.out.print(res.get(i).getPiaPpvNpvSnps()+" -> "+res.get(i).getPiaPpvNpvScore()+"  ");
			System.out.print(res.get(i).getPiaRiskSnps()+" -> "+res.get(i).getPiaRiskScore()+"  ");
			System.out.print(res.get(i).getPiaOddsSnps()+" -> "+res.get(i).getPiaOddsScore()+"  ");
			System.out.print(res.get(i).getPiaGiniSnps()+" -> "+res.get(i).getPiaGiniScore()+"  ");
			System.out.print(res.get(i).getPiaApdSnps()+" -> "+res.get(i).getPiaApdScore()+"  ");
			System.out.print(res.get(i).getPiaOverallSnps()+" -> "+res.get(i).getPiaOverallScore()+"  ");
			System.out.println();
		}
		
	}
	*/

}
