package main;

import basic.ResultSet;
import io.Arquivo;
import analysis.Parameter;
import analysis.SingleAnalysis;
import constants.MethodConstant;


public class Flavia {
	
	public static void main(String[] args) {
		
		// Parametros ////////////////////////////////////////////////////
		
		Parameter parameter = new Parameter();

		// Entrada e Saida
		String entrada = "C:/Users/Eduardo Gade/Desktop/teste.txt";
		String saida = "C:/Users/Eduardo Gade/Desktop/saida.txt";
		
		// Geral
		parameter.USE_PATHWAY = false;
		parameter.ORDER = 2;
		parameter.N_TOP = 5;
		parameter.COUNT_MISSING = false;
		
		// MASS
		parameter.MASS_RATSL = 1.7;
		parameter.MASS_SIMPLE_ASSIGNMENT = true;
		parameter.MASS_USE_GAIN = true;
		parameter.MASS_USE_CHI = true;
		parameter.MASS_USE_GINI = true;
		parameter.MASS_USE_APD = true;

		// Execucao ////////////////////////////////////////////////////
		
		Arquivo arquivo = new Arquivo(entrada,saida);
		boolean primeiro = true;
		
		int contador = 1;
		
		int acertosGain = 0;
		int acertosChi = 0;
		int acertosGini = 0;
		int acertosApd = 0;
		int acertosOverall = 0;
		
		arquivo.print("Ordem dos Scores: Gain, Chi, Gini, APD, Overall\n\n");
		
		String ss = "";
		
		while(!arquivo.isEndOfFile()){
			
			String s = arquivo.readLine();
			System.out.println(s);
			
			if(s.charAt(0) == '#'){
				
				if(primeiro){
					ss = s;
					primeiro = false;
				}
				else{
					arquivo.print(contador+"o ("+ss+") -> "+acertosGain+"   "+acertosChi+"   "+acertosGini+"   "+acertosApd+"   "+acertosOverall+"\n");
					arquivo.flush();
					
					acertosGain = 0;
					acertosChi = 0;
					acertosGini = 0;
					acertosApd = 0;
					acertosOverall = 0;
					
					contador++;
					ss = s;
				}
			}
			else{
				
				parameter.PATH = s;
				
				SingleAnalysis singleAnalysis = new SingleAnalysis(parameter);
				
				singleAnalysis.doAnalysis(MethodConstant.MASS);
				
				ResultSet rs = singleAnalysis.getResultSet();
				
				if(rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN],0)[0].equals("X0") &&
				   rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_GAIN],0)[1].equals("X1")){
					acertosGain++;
				}
				
				if(rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI],0)[0].equals("X0") &&
				   rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_CHI],0)[1].equals("X1")){
					acertosChi++;
				}
				
				if(rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI],0)[0].equals("X0") &&
				   rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_GINI],0)[1].equals("X1")){
					acertosGini++;
				}
				
				if(rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD],0)[0].equals("X0") &&
				   rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_APD],0)[1].equals("X1")){
					acertosApd++;
				}
				
				if(rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL],0)[0].equals("X0") &&
				   rs.getSnps(MethodConstant.MASS_SCORES[MethodConstant.MASS_OVERALL],0)[1].equals("X1")){
					acertosOverall++;
				}
				
			}
			
		}
		
		// Ultimos
		arquivo.print(contador+"o ("+ss+") -> "+acertosGain+"   "+acertosChi+"   "+acertosGini+"   "+acertosApd+"   "+acertosOverall+"\n");
		
		arquivo.close();
	}
	
}
