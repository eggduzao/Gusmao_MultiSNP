package main;

import io.Arquivo;
import basic.ResultSet;
import basic.Time;
import constants.MethodConstant;
import analysis.Parameter;
import analysis.SingleAnalysis;

public class FlaviaTempo {

	public static void main(String[] args) {
		
		// Parametros ////////////////////////////////////////////////////
		
		Parameter parameter = new Parameter();
		
		// Saida
		String entrada = "C:/Eduardo/Ciencia da Computacao/IC/Dados/TGVelez/20SNP/1600/59/99.txt";
		String saida = "C:/Users/Eduardo Gade/Desktop/tempo.txt";
		
		// Geral
		parameter.PATH = entrada;
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
		
		Arquivo arquivo = new Arquivo("info.in",saida);
		
		SingleAnalysis singleAnalysis = new SingleAnalysis(parameter);
		
		singleAnalysis.doAnalysis(MethodConstant.MASS);
		
		Time time = singleAnalysis.getTime();
		
		arquivo.println("Tempo total MASS = "+time.get(MethodConstant.MASS_TIME[MethodConstant.MASS_TIME_TOTAL])+" milissegundos");
		
		arquivo.close();
	}
	
}
