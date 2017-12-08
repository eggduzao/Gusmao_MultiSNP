package main;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Hashtable;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import basic.ResultSet;
import basic.Time;
import analysis.Parameter;
import analysis.SingleAnalysis;
import constants.MethodConstant;

public class Simulation {

	public static void main(String[] args) throws RowsExceededException, WriteException, IOException {

		Parameter parameter = new Parameter();

		parameter.USE_PATHWAY = false;
		parameter.ORDER = 2;
		parameter.N_TOP = 5;
		parameter.COUNT_MISSING = false;

		parameter.PIA_SHUFFLE = 1;
		parameter.PIA_FRACT = 0.2;
		parameter.PIA_RATSL = 1.7;
		parameter.PIA_NTIME = 1;
		parameter.PIA_IFRACT = false;
		parameter.PIA_ITRAIN = false;
		parameter.PIA_LOOTR = 0;
		parameter.PIA_HIDE_SS = false;

		// MDR
		parameter.MDR_T = 1.0;
		parameter.MDR_SHUFFLE = 1;
		parameter.MDR_FRACT = 0.2;
		parameter.MDR_NTIME = 1;

		// ESNP2
		//parameter.ESNP2_SHUFFLE = 2000;

		// MASS
		parameter.MASS_RATSL = 1.4;
		parameter.MASS_SIMPLE_ASSIGNMENT = true;
		parameter.MASS_USE_GAIN = true;
		parameter.MASS_USE_CHI = true;
		parameter.MASS_USE_GINI = true;
		parameter.MASS_USE_APD = true;
		
		DecimalFormat f = new DecimalFormat("#.##");

		SingleAnalysis singleAnalysis = new SingleAnalysis(parameter);

		String[] snp = {"20"}; //String[] snp = {"20","30","40","50"};
		String[] pop = {"200","400","800","1600"};//String[] pop = {"200","400","800","1600"};
		String[] model = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69"};
		String[] base = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99"};

		String out = "C:/Eduardo/Ciencia da Computacao/IC/Results/resultTG.xls";
		WritableWorkbook output = Workbook.createWorkbook(new File(out));
		WritableSheet resultSheet = output.createSheet("Result", 0);
		WritableSheet timeSheet = output.createSheet("Time", 1);
		int resultRow = 0; int resultCol = 0;
		int timeRow = 0; int timeCol = 0;

		String[][] allScores = {MethodConstant.PIA_SCORES_WITHOUT_SINGLE_SHOT,MethodConstant.MDR_SCORES_WITHOUT_PREDICT,MethodConstant.ESNP2_SCORES,MethodConstant.MASS_SCORES};
		String[][] allTimes = {MethodConstant.PIA_TIME,MethodConstant.MDR_TIME,MethodConstant.ESNP2_TIME,MethodConstant.MASS_TIME};

		int colHeader = 2;
		for(int i = 0 ; i < model.length ; i++){
			resultSheet.addCell(new Label(colHeader,resultRow,model[i]));
			colHeader++;
		}
		colHeader = 1;
		for(int i = 0 ; i < allTimes.length ; i++){
			String[] score = allTimes[i];
			for(int j = 0 ; j < score.length ; j++){
				String s = "";
				if(i==0) s = "PIA";
				else if(i==1) s = "MDR";
				else if(i==2) s = "ESNP2";
				else s = "MASS";
				timeSheet.addCell(new Label(colHeader,timeRow,score[j]+" - "+s));
				timeSheet.setColumnView(colHeader,score[j].length()+s.length()+4);
				colHeader++;
			}
		}
		
		resultSheet.setColumnView(0,20);
		resultSheet.setColumnView(1,20);
		
		timeSheet.setColumnView(0,20);

		resultRow++;
		timeRow++;

		for(int s = 0 ; s < snp.length ; s++){

			System.out.println("#### SNP = "+snp[s]);

			for(int p = 0 ; p < pop.length ; p++){

				System.out.println("## Pop = "+pop[p]);

				resultCol = 0;
				resultSheet.addCell(new Label(resultCol++,resultRow,"SNP "+snp[s]+", POP "+pop[p]));
				for(int i = 0 ; i < allScores.length ; i++){
					String[] score = allScores[i];
					for(int j = 0 ; j < score.length ; j++){
						String st = "";
						if(i==0) st = "PIA";
						else if(i==1) st = "MDR";
						else if(i==2) st = "ESNP2";
						else st = "MASS";
						resultSheet.addCell(new Label(resultCol,resultRow++,score[j]+" - "+st));
					}
				}
				resultRow-=15;
				resultCol++;

				timeSheet.addCell(new Label(timeCol++,timeRow,"SNP "+snp[s]+", POP "+pop[p]));


				Hashtable<String,Double> hashTime = new Hashtable<String,Double>();
				for(int i = 0 ; i < allTimes.length ; i++){
					String[] score = allTimes[i];
					for(int j = 0 ; j < score.length ; j++){
						hashTime.put(score[j]+i,0.0);
					}
				}

				for(int m = 0 ; m < model.length ; m++){

					System.out.print(model[m]);

					Hashtable<String,Double> hashScore = new Hashtable<String,Double>();
					for(int i = 0 ; i < allScores.length ; i++){
						String[] score = allScores[i];
						for(int j = 0 ; j < score.length ; j++){
							hashScore.put(score[j]+i,0.0);
						}
					}

					for(int b = 0 ; b < base.length ; b++){

						if(b%20==0) System.out.print("*");

						String in = "C:/Eduardo/Ciencia da Computacao/IC/Dados/TGVelez/"+snp[s]+"SNP2/"+pop[p]+"/"+model[m]+"/"+model[m]+"."+pop[p]+"."+base[b]+".txt";
						parameter.PATH = in;

						singleAnalysis.doAnalysis(MethodConstant.PIA);
						ResultSet resultPia = singleAnalysis.getResultSet();
						Time timePia = singleAnalysis.getTime();
						singleAnalysis.doAnalysis(MethodConstant.MDR);
						ResultSet resultMdr = singleAnalysis.getResultSet();
						Time timeMdr = singleAnalysis.getTime();
						singleAnalysis.doAnalysis(MethodConstant.ESNP2);
						ResultSet resultEsnp2 = singleAnalysis.getResultSet();
						Time timeEsnp2 = singleAnalysis.getTime();
						singleAnalysis.doAnalysis(MethodConstant.MASS);
						ResultSet resultMass = singleAnalysis.getResultSet();
						Time timeMass = singleAnalysis.getTime();

						ResultSet[] allRs = {resultPia,resultMdr,resultEsnp2,resultMass};
						Time[] allTime = {timePia,timeMdr,timeEsnp2,timeMass};

						for(int i = 0 ; i < allRs.length ; i++){

							ResultSet rs = allRs[i];
							Time time = allTime[i];
							String[] score = allScores[i];
							String[] timeName = allTimes[i];

							for(int j = 0 ; j < score.length ; j++){
								String[] combSnp = rs.getSnps(score[j],0);
								if(combSnp[0].equals("X0") && combSnp[1].equals("X1")){
									hashScore.put(score[j]+i,hashScore.get(score[j]+i)+1);
								}
							}

							for(int j = 0 ; j < timeName.length ; j++){
								double timePart = time.get(timeName[j]);
								hashTime.put(timeName[j]+i,hashTime.get(timeName[j]+i)+timePart);
							}

						}

					}

					for(int i = 0 ; i < allScores.length ; i++){
						String[] score = allScores[i];
						for(int j = 0 ; j < score.length ; j++){
							resultSheet.addCell(new Label(resultCol,resultRow++,hashScore.get(score[j]+i)+""));
						}
					}
					resultRow-=15;
					resultCol++;

					System.out.print(" ");

				}

				for(int i = 0 ; i < allTimes.length ; i++){
					String[] score = allTimes[i];
					for(int j = 0 ; j < score.length ; j++){
						timeSheet.addCell(new Label(timeCol++,timeRow,f.format(hashTime.get(score[j]+i)/(model.length*base.length))+""));
					}
				}
				timeRow++;
				timeCol = 0;
				resultRow+=16;
				resultCol = 0;
				
				System.out.println();

			}

		}
		
		output.write();
		output.close();
	}

}
