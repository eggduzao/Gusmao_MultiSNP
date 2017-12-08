package io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import constants.MethodConstant;
import constants.SystemConstant;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import basic.Score;
import basic.Tuple;

/**
 * 
 * Creates a XLS File containing the results to be downloaded or send by mail
 * 
 * @author Eduardo Gade Gusmao
 *
 */

public class XlsCreator {
	
	// Basic Elements
	private String method;
	private ArrayList<Tuple>[] parameters;
	private ArrayList<Tuple>[] results;
	private ArrayList<Tuple>[] time;
	private ArrayList<Score> pathway;
	
	// Return
	private WritableWorkbook workbook;

	public XlsCreator(String method, ArrayList<Tuple>[] parameters, ArrayList<Tuple>[] results, ArrayList<Tuple>[] time, 
			ArrayList<Score> pathway, String realPath) throws IOException, WriteException{
		
		// Initialization
		this.method = method;
		this.parameters = parameters;
		this.results = results;
		this.time = time;
		this.pathway = pathway;
		
		// Workbook represents the XLS Object
		this.workbook = Workbook.createWorkbook(new File(realPath+SystemConstant.DEFAULT_FILE_ANALYSIS_XLS)); 
		
		// Create XLS
		this.createXls();
		
		// Close references
		this.workbook.write(); 
		this.workbook.close(); 
		
	}
	
	private void createXls() throws RowsExceededException, WriteException{
		
		this.writeParameters();
		this.writeResults();
		this.writeTime();
		if(this.pathway!=null && !this.pathway.isEmpty()) this.writePathway();
		
	}
	
	private void writeParameters() throws RowsExceededException, WriteException{
		
		// Creating sheet
		WritableSheet parameterSheet = this.workbook.createSheet("Parameters", 0);
		
		// Counter
		int rowCount = 0;
		int colCount = 0;
		
		// Column Width Track
		int[] colTrack = null;
		
		// Title
		parameterSheet.addCell(new Label(colCount,rowCount,"Parameters"));
		if(this.parameters.length==1){
			parameterSheet.mergeCells(colCount,rowCount,colCount+1,rowCount+1);
			parameterSheet.mergeCells(colCount,rowCount+2,colCount+1,rowCount+2);
			colTrack = new int[2];
		}
		else{
			parameterSheet.mergeCells(colCount,rowCount,colCount+10,rowCount+1);
			parameterSheet.mergeCells(colCount,rowCount+2,colCount+10,rowCount+2);
			colTrack = new int[11];
		}
		WritableCell wc = parameterSheet.getWritableCell(colCount,rowCount);
		WritableCellFormat cf = new WritableCellFormat(wc.getCellFormat());
		WritableFont wf = new WritableFont(cf.getFont());
		wf.setBoldStyle(WritableFont.BOLD);
		cf.setBackground(Colour.PALE_BLUE);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		cf.setFont(wf);
		wc.setCellFormat(cf);
		
		int initRowCount = rowCount+1;
		
		// Writting Parameters
		for(int k = 0 ; k < this.parameters.length ; k++){
			
			ArrayList<Tuple> param = this.parameters[k];
			rowCount = initRowCount + 2;
			
			// Parameter Block Title
			if(this.parameters.length==1){
				parameterSheet.addCell(new Label(colCount,rowCount,this.method+" Parameters"));
			}
			else{
				if(k==0) parameterSheet.addCell(new Label(colCount,rowCount,"General Parameters"));
				else if(k==1) parameterSheet.addCell(new Label(colCount,rowCount,"PIA Parameters"));
				else if(k==2) parameterSheet.addCell(new Label(colCount,rowCount,"MDR Parameters"));
				//else if(k==3) parameterSheet.addCell(new Label(colCount,rowCount,"ESNP2 Parameters"));
				else if(k==3) parameterSheet.addCell(new Label(colCount,rowCount,"MASS Parameters"));
			}
			parameterSheet.mergeCells(colCount,rowCount,colCount+1,rowCount);
			WritableCell wcTitle = parameterSheet.getWritableCell(colCount,rowCount);
			WritableCellFormat cfTitle = new WritableCellFormat(wcTitle.getCellFormat());
			WritableFont wfTitle = new WritableFont(cfTitle.getFont());
			wfTitle.setBoldStyle(WritableFont.BOLD);
			cfTitle.setAlignment(Alignment.CENTRE);
			cfTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
			cfTitle.setFont(wfTitle);
			wcTitle.setCellFormat(cfTitle);
			
			// Parameter Values
			for(Tuple t : param){
				
				rowCount++;
				String t1 = t.getT1();
				String t2 = t.getT2();
				
				// Parameter Name
				parameterSheet.addCell(new Label(colCount,rowCount,t1.substring(0,t1.length()-2)));
				WritableCell wcT1 = parameterSheet.getWritableCell(colCount,rowCount);
				WritableCellFormat cfT1 = new WritableCellFormat(wcT1.getCellFormat());
				WritableFont wfT1 = new WritableFont(cfT1.getFont());
				wfT1.setBoldStyle(WritableFont.BOLD);
				cfT1.setFont(wfT1);
				wcT1.setCellFormat(cfT1);
				
				// Parameter Value
				parameterSheet.addCell(new Label(colCount+1,rowCount,t2));
				
				// Keeping Track of the largest Value for both columns to set Column Width
				colTrack[colCount] = Math.max(t1.length()-2,colTrack[colCount]);
				colTrack[colCount+1] = Math.max(t2.length(),colTrack[colCount+1]);
			}
			
			colCount+=3;
			
		}
		
		// Setting Column Width
		for(int i = 0 ; i < colTrack.length ; i++){
			parameterSheet.setColumnView(i,Math.max(colTrack[i]+1,5));
		}
		
	}
	
	private void writeResults() throws RowsExceededException, WriteException{
		
		// Creating sheet
		WritableSheet resultSheet = this.workbook.createSheet("Results", 1);
		
		// Counter
		int rowCount = 0;
		int colCount = 0;
		
		// Column Width Track
		int[] colTrack = null;
		
		// Title
		resultSheet.addCell(new Label(colCount,rowCount,"Results"));
		String[] scoreNames = null;
		if(this.method.equals(MethodConstant.PIA)){
			resultSheet.mergeCells(colCount,rowCount,colCount+15,rowCount+1);
			resultSheet.mergeCells(colCount,rowCount+2,colCount+15,rowCount+2);
			scoreNames = MethodConstant.PIA_SCORES_WITHOUT_SINGLE_SHOT;
			colTrack = new int[16];
		}
		else if(this.method.equals(MethodConstant.MDR)){
			resultSheet.mergeCells(colCount,rowCount,colCount+3,rowCount+1);
			resultSheet.mergeCells(colCount,rowCount+2,colCount+3,rowCount+2);
			scoreNames = MethodConstant.MDR_SCORES;
			colTrack = new int[4];
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			resultSheet.mergeCells(colCount,rowCount,colCount+1,rowCount+1);
			resultSheet.mergeCells(colCount,rowCount+2,colCount+1,rowCount+2);
			scoreNames = MethodConstant.ESNP2_SCORES;
			colTrack = new int[2];
		}
		else if(this.method.equals(MethodConstant.MASS)){
			resultSheet.mergeCells(colCount,rowCount,colCount+9,rowCount+1);
			resultSheet.mergeCells(colCount,rowCount+2,colCount+9,rowCount+2);
			scoreNames = MethodConstant.MASS_SCORES;
			colTrack = new int[10];
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			resultSheet.mergeCells(colCount,rowCount,colCount+7,rowCount+1);
			resultSheet.mergeCells(colCount,rowCount+2,colCount+7,rowCount+2);
			scoreNames = MethodConstant.COMPARATIVE_SCORES;
			colTrack = new int[8];
		}
		WritableCell wc = resultSheet.getWritableCell(colCount,rowCount);
		WritableCellFormat cf = new WritableCellFormat(wc.getCellFormat());
		WritableFont wf = new WritableFont(cf.getFont());
		wf.setBoldStyle(WritableFont.BOLD);
		cf.setBackground(Colour.PALE_BLUE);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		cf.setFont(wf);
		wc.setCellFormat(cf);
		
		rowCount += 3;
		
		// Writting Methods Titles
		for(int i = 0 ; i < scoreNames.length ; i++){
			resultSheet.addCell(new Label(colCount,rowCount,scoreNames[i]));
			resultSheet.mergeCells(colCount,rowCount,colCount+1,rowCount);
			WritableCell wcNames = resultSheet.getWritableCell(colCount,rowCount);
			WritableCellFormat cfNames = new WritableCellFormat(wcNames.getCellFormat());
			WritableFont wfNames = new WritableFont(cfNames.getFont());
			wfNames.setBoldStyle(WritableFont.BOLD);
			cfNames.setAlignment(Alignment.CENTRE);
			cfNames.setVerticalAlignment(VerticalAlignment.CENTRE);
			cfNames.setFont(wfNames);
			wcNames.setCellFormat(cfNames);
			colCount+=2;
		}
		
		int initRow = rowCount+1;
		colCount = 0;
		
		// Writting methods results
		for(int k = 0 ; k < this.results.length ; k++){
			
			ArrayList<Tuple> param = this.results[k];
			
			rowCount = initRow;
			
			for(int i = 0 ; i < param.size() ; i++){
				
				String t1 = param.get(i).getT1();
				String t2 = param.get(i).getT2();
				
				resultSheet.addCell(new Label(colCount,rowCount,t1));
				WritableCell wcComb = resultSheet.getWritableCell(colCount,rowCount);
				WritableCellFormat cfComb = new WritableCellFormat(wcComb.getCellFormat());
				WritableFont wfComb = new WritableFont(cfComb.getFont());
				wfComb.setBoldStyle(WritableFont.BOLD);
				cfComb.setFont(wfComb);
				wcComb.setCellFormat(cfComb);
				
				resultSheet.addCell(new Label(colCount+1,rowCount,t2));
				
				colTrack[colCount] = Math.max(colTrack[colCount],t1.length());
				colTrack[colCount+1] = Math.max(colTrack[colCount+1],t2.length());
				
				rowCount++;
			}
			
			colCount+=2;
			
		}
		
		// Setting Column Width
		for(int i = 0 ; i < colTrack.length ; i++){
			resultSheet.setColumnView(i,Math.min(colTrack[i]+5,50));
		}
		
	}
	
	private void writeTime() throws RowsExceededException, WriteException{
		
		// Create Sheet
		WritableSheet timeSheet = this.workbook.createSheet("Time Analysis", 2);
		
		// Counter
		int rowCount = 0;
		int colCount = 0;
		
		// Column Width Track
		int[] colTrack = null;
		
		// Title
		timeSheet.addCell(new Label(colCount,rowCount,"Time Analysis"));
		if(this.parameters.length==1){
			timeSheet.mergeCells(colCount,rowCount,colCount+1,rowCount+1);
			timeSheet.mergeCells(colCount,rowCount+2,colCount+1,rowCount+2);
			colTrack = new int[2];
		}
		else{
			timeSheet.mergeCells(colCount,rowCount,colCount+10,rowCount+1);
			timeSheet.mergeCells(colCount,rowCount+2,colCount+10,rowCount+2);
			colTrack = new int[11];
		}
		WritableCell wcTitle = timeSheet.getWritableCell(colCount,rowCount);
		WritableCellFormat cfTitle = new WritableCellFormat(wcTitle.getCellFormat());
		WritableFont wfTitle = new WritableFont(cfTitle.getFont());
		wfTitle.setBoldStyle(WritableFont.BOLD);
		cfTitle.setBackground(Colour.PALE_BLUE);
		cfTitle.setAlignment(Alignment.CENTRE);
		cfTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
		cfTitle.setFont(wfTitle);
		wcTitle.setCellFormat(cfTitle);
		
		int initRowCount = rowCount+3;
		
		for(int k = 0 ; k < this.time.length ; k++){
			
			ArrayList<Tuple> param = this.time[k];
			rowCount = initRowCount;
			
			// Method Name
			if(this.time.length==1){
				timeSheet.addCell(new Label(colCount,rowCount,this.method+" Time Analysis"));
			}
			else{
				if(k==0) timeSheet.addCell(new Label(colCount,rowCount,"PIA Time Analysis"));
				else if(k==1) timeSheet.addCell(new Label(colCount,rowCount,"MDR Time Analysis"));
				else if(k==2) timeSheet.addCell(new Label(colCount,rowCount,"ESNP2 Time Analysis"));
				else if(k==3) timeSheet.addCell(new Label(colCount,rowCount,"MASS Time Analysis"));
			}
			timeSheet.mergeCells(colCount,rowCount,colCount+1,rowCount);
			WritableCell wcMethod = timeSheet.getWritableCell(colCount,rowCount);
			WritableCellFormat cfMethod = new WritableCellFormat(wcMethod.getCellFormat());
			WritableFont wfMethod = new WritableFont(cfMethod.getFont());
			wfMethod.setBoldStyle(WritableFont.BOLD);
			cfMethod.setAlignment(Alignment.CENTRE);
			cfMethod.setVerticalAlignment(VerticalAlignment.CENTRE);
			cfMethod.setFont(wfMethod);
			wcMethod.setCellFormat(cfMethod);
			
			rowCount++;
			
			for(Tuple t : param){
				
				String t1 = t.getT1();
				String t2 = t.getT2();
				
				timeSheet.addCell(new Label(colCount,rowCount,t1.substring(0,t1.length()-2)));
				WritableCell wcParam = timeSheet.getWritableCell(colCount,rowCount);
				WritableCellFormat cfParam = new WritableCellFormat(wcParam.getCellFormat());
				WritableFont wfParam = new WritableFont(cfParam.getFont());
				wfParam.setBoldStyle(WritableFont.BOLD);
				cfParam.setFont(wfParam);
				wcParam.setCellFormat(cfParam);
				
				timeSheet.addCell(new Label(colCount+1,rowCount,t2));
				
				colTrack[colCount] = Math.max(colTrack[colCount],t1.length()-2);
				colTrack[colCount+1] = Math.max(colTrack[colCount+1],t2.length());
				
				rowCount++;
			}
			
			colCount += 3;
			
		}
		
		// Setting Column Width
		for(int i = 0 ; i < colTrack.length ; i++){
			timeSheet.setColumnView(i,Math.max(colTrack[i]+5,5));
		}
		
	}
	
	private void writePathway() throws RowsExceededException, WriteException{
		
		// Create Sheet
		WritableSheet pathwaySheet = this.workbook.createSheet("Pathway Analysis", 3);
		
		// Counter
		int rowCount = 0;
		int colCount = 0;
		
		// Column Width Track
		int[] colTrack = null;
		
		// Title
		pathwaySheet.addCell(new Label(colCount,rowCount,"Pathway Analysis"));
		String[] scoreNames = null;
		if(this.method.equals(MethodConstant.PIA)){
			scoreNames = MethodConstant.PIA_SCORES_WITHOUT_SINGLE_SHOT;
			pathwaySheet.mergeCells(colCount,rowCount,colCount+15,rowCount+1);
			pathwaySheet.mergeCells(colCount,rowCount+2,colCount+15,rowCount+2);
			colTrack = new int[16];
		}
		else if(this.method.equals(MethodConstant.MDR)){
			scoreNames = MethodConstant.MDR_SCORES_WITHOUT_PREDICT;
			pathwaySheet.mergeCells(colCount,rowCount,colCount+1,rowCount+1);
			pathwaySheet.mergeCells(colCount,rowCount+2,colCount+1,rowCount+2);
			colTrack = new int[2];
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			scoreNames = MethodConstant.ESNP2_SCORES;
			pathwaySheet.mergeCells(colCount,rowCount,colCount+1,rowCount+1);
			pathwaySheet.mergeCells(colCount,rowCount+2,colCount+1,rowCount+2);
			colTrack = new int[2];
		}
		else if(this.method.equals(MethodConstant.MASS)){
			scoreNames = MethodConstant.MASS_SCORES;
			pathwaySheet.mergeCells(colCount,rowCount,colCount+9,rowCount+1);
			pathwaySheet.mergeCells(colCount,rowCount+2,colCount+9,rowCount+2);
			colTrack = new int[10];
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			scoreNames = MethodConstant.COMPARATIVE_SCORES;
			pathwaySheet.mergeCells(colCount,rowCount,colCount+7,rowCount+1);
			pathwaySheet.mergeCells(colCount,rowCount+2,colCount+7,rowCount+2);
			colTrack = new int[8];
		}
		WritableCell wcTitle = pathwaySheet.getWritableCell(colCount,rowCount);
		WritableCellFormat cfTitle = new WritableCellFormat(wcTitle.getCellFormat());
		WritableFont wfTitle = new WritableFont(cfTitle.getFont());
		wfTitle.setBoldStyle(WritableFont.BOLD);
		cfTitle.setBackground(Colour.PALE_BLUE);
		cfTitle.setAlignment(Alignment.CENTRE);
		cfTitle.setVerticalAlignment(VerticalAlignment.CENTRE);
		cfTitle.setFont(wfTitle);
		wcTitle.setCellFormat(cfTitle);
		
		rowCount += 3;
		
		// Method Names
		for(int i = 0 ; i < scoreNames.length ; i++){
			pathwaySheet.addCell(new Label(colCount,rowCount,scoreNames[i]));
			pathwaySheet.mergeCells(colCount,rowCount,colCount+1,rowCount);
			WritableCell wcMethod = pathwaySheet.getWritableCell(colCount,rowCount);
			WritableCellFormat cfMethod = new WritableCellFormat(wcMethod.getCellFormat());
			WritableFont wfMethod = new WritableFont(cfMethod.getFont());
			wfMethod.setBoldStyle(WritableFont.BOLD);
			cfMethod.setAlignment(Alignment.CENTRE);
			cfMethod.setVerticalAlignment(VerticalAlignment.CENTRE);
			cfMethod.setFont(wfMethod);
			wcMethod.setCellFormat(cfMethod);
			colCount+=2;
		}
		
		int initRow = rowCount + 1;
		
		for(int i = 0 ; i < this.pathway.size() ; i++){
			
			Score s = this.pathway.get(i);
			
			if(this.method.equals(MethodConstant.PIA)){
				
				pathwaySheet.addCell(new Label(0,i+initRow,s.getPiaCorrectSnps()));
				pathwaySheet.addCell(new Label(1,i+initRow,s.getPiaCorrectScore()));
				colTrack[0] = Math.max(colTrack[0],s.getPiaCorrectSnps().length());
				colTrack[1] = Math.max(colTrack[1],s.getPiaCorrectScore().length());
				WritableCell wcParam1 = pathwaySheet.getWritableCell(0,i+initRow);
				WritableCellFormat cfParam1 = new WritableCellFormat(wcParam1.getCellFormat());
				WritableFont wfParam1 = new WritableFont(cfParam1.getFont());
				wfParam1.setBoldStyle(WritableFont.BOLD);
				cfParam1.setFont(wfParam1);
				wcParam1.setCellFormat(cfParam1);
				
				pathwaySheet.addCell(new Label(2,i+initRow,s.getPiaSsSnps()));
				pathwaySheet.addCell(new Label(3,i+initRow,s.getPiaSsScore()));
				colTrack[2] = Math.max(colTrack[2],s.getPiaSsSnps().length());
				colTrack[3] = Math.max(colTrack[3],s.getPiaSsScore().length());
				WritableCell wcParam2 = pathwaySheet.getWritableCell(2,i+initRow);
				WritableCellFormat cfParam2 = new WritableCellFormat(wcParam2.getCellFormat());
				WritableFont wfParam2 = new WritableFont(cfParam2.getFont());
				wfParam2.setBoldStyle(WritableFont.BOLD);
				cfParam2.setFont(wfParam2);
				wcParam2.setCellFormat(cfParam2);
				
				pathwaySheet.addCell(new Label(4,i+initRow,s.getPiaPpvNpvSnps()));
				pathwaySheet.addCell(new Label(5,i+initRow,s.getPiaPpvNpvScore()));
				colTrack[4] = Math.max(colTrack[4],s.getPiaPpvNpvSnps().length());
				colTrack[5] = Math.max(colTrack[5],s.getPiaPpvNpvScore().length());
				WritableCell wcParam3 = pathwaySheet.getWritableCell(4,i+initRow);
				WritableCellFormat cfParam3 = new WritableCellFormat(wcParam3.getCellFormat());
				WritableFont wfParam3 = new WritableFont(cfParam3.getFont());
				wfParam3.setBoldStyle(WritableFont.BOLD);
				cfParam3.setFont(wfParam3);
				wcParam3.setCellFormat(cfParam3);
				
				pathwaySheet.addCell(new Label(6,i+initRow,s.getPiaRiskSnps()));
				pathwaySheet.addCell(new Label(7,i+initRow,s.getPiaRiskScore()));
				colTrack[6] = Math.max(colTrack[6],s.getPiaRiskSnps().length());
				colTrack[7] = Math.max(colTrack[7],s.getPiaRiskScore().length());
				WritableCell wcParam4 = pathwaySheet.getWritableCell(6,i+initRow);
				WritableCellFormat cfParam4 = new WritableCellFormat(wcParam4.getCellFormat());
				WritableFont wfParam4 = new WritableFont(cfParam4.getFont());
				wfParam4.setBoldStyle(WritableFont.BOLD);
				cfParam4.setFont(wfParam4);
				wcParam4.setCellFormat(cfParam4);
				
				pathwaySheet.addCell(new Label(8,i+initRow,s.getPiaOddsSnps()));
				pathwaySheet.addCell(new Label(9,i+initRow,s.getPiaOddsScore()));
				colTrack[8] = Math.max(colTrack[8],s.getPiaOddsSnps().length());
				colTrack[9] = Math.max(colTrack[9],s.getPiaOddsScore().length());
				WritableCell wcParam5 = pathwaySheet.getWritableCell(8,i+initRow);
				WritableCellFormat cfParam5 = new WritableCellFormat(wcParam5.getCellFormat());
				WritableFont wfParam5 = new WritableFont(cfParam5.getFont());
				wfParam5.setBoldStyle(WritableFont.BOLD);
				cfParam5.setFont(wfParam5);
				wcParam5.setCellFormat(cfParam5);
				
				pathwaySheet.addCell(new Label(10,i+initRow,s.getPiaGiniSnps()));
				pathwaySheet.addCell(new Label(11,i+initRow,s.getPiaGiniScore()));
				colTrack[10] = Math.max(colTrack[10],s.getPiaGiniSnps().length());
				colTrack[11] = Math.max(colTrack[11],s.getPiaGiniScore().length());
				WritableCell wcParam6 = pathwaySheet.getWritableCell(10,i+initRow);
				WritableCellFormat cfParam6 = new WritableCellFormat(wcParam6.getCellFormat());
				WritableFont wfParam6 = new WritableFont(cfParam6.getFont());
				wfParam6.setBoldStyle(WritableFont.BOLD);
				cfParam6.setFont(wfParam6);
				wcParam6.setCellFormat(cfParam6);
				
				pathwaySheet.addCell(new Label(12,i+initRow,s.getPiaApdSnps()));
				pathwaySheet.addCell(new Label(13,i+initRow,s.getPiaApdScore()));
				colTrack[12] = Math.max(colTrack[12],s.getPiaApdSnps().length());
				colTrack[13] = Math.max(colTrack[13],s.getPiaApdScore().length());
				WritableCell wcParam7 = pathwaySheet.getWritableCell(12,i+initRow);
				WritableCellFormat cfParam7 = new WritableCellFormat(wcParam7.getCellFormat());
				WritableFont wfParam7 = new WritableFont(cfParam7.getFont());
				wfParam7.setBoldStyle(WritableFont.BOLD);
				cfParam7.setFont(wfParam7);
				wcParam7.setCellFormat(cfParam7);
				
				pathwaySheet.addCell(new Label(14,i+initRow,s.getPiaOverallSnps()));
				pathwaySheet.addCell(new Label(15,i+initRow,s.getPiaOverallScore()));
				colTrack[14] = Math.max(colTrack[14],s.getPiaOverallSnps().length());
				colTrack[15] = Math.max(colTrack[15],s.getPiaOverallScore().length());
				WritableCell wcParam8 = pathwaySheet.getWritableCell(14,i+initRow);
				WritableCellFormat cfParam8 = new WritableCellFormat(wcParam8.getCellFormat());
				WritableFont wfParam8 = new WritableFont(cfParam8.getFont());
				wfParam8.setBoldStyle(WritableFont.BOLD);
				cfParam8.setFont(wfParam8);
				wcParam8.setCellFormat(cfParam8);
				
				
			}
			else if(this.method.equals(MethodConstant.MDR)){
				
				pathwaySheet.addCell(new Label(0,i+initRow,s.getMdrBalancedAccSnps()));
				pathwaySheet.addCell(new Label(1,i+initRow,s.getMdrBalancedAccScore()));
				colTrack[0] = Math.max(colTrack[0],s.getMdrBalancedAccSnps().length());
				colTrack[1] = Math.max(colTrack[1],s.getMdrBalancedAccScore().length());
				WritableCell wcParam1 = pathwaySheet.getWritableCell(0,i+initRow);
				WritableCellFormat cfParam1 = new WritableCellFormat(wcParam1.getCellFormat());
				WritableFont wfParam1 = new WritableFont(cfParam1.getFont());
				wfParam1.setBoldStyle(WritableFont.BOLD);
				cfParam1.setFont(wfParam1);
				wcParam1.setCellFormat(cfParam1);
				
			}
			else if(this.method.equals(MethodConstant.ESNP2)){
				
				pathwaySheet.addCell(new Label(0,i+initRow,s.getEsnp2DeltaRSnps()));
				pathwaySheet.addCell(new Label(1,i+initRow,s.getEsnp2DeltaRScore()));
				colTrack[0] = Math.max(colTrack[0],s.getEsnp2DeltaRSnps().length());
				colTrack[1] = Math.max(colTrack[1],s.getEsnp2DeltaRScore().length());
				WritableCell wcParam1 = pathwaySheet.getWritableCell(0,i+initRow);
				WritableCellFormat cfParam1 = new WritableCellFormat(wcParam1.getCellFormat());
				WritableFont wfParam1 = new WritableFont(cfParam1.getFont());
				wfParam1.setBoldStyle(WritableFont.BOLD);
				cfParam1.setFont(wfParam1);
				wcParam1.setCellFormat(cfParam1);
				
			}
			if(this.method.equals(MethodConstant.MASS)){
				
				pathwaySheet.addCell(new Label(0,i+initRow,s.getMassGainSnps()));
				pathwaySheet.addCell(new Label(1,i+initRow,s.getMassGainScore()));
				colTrack[0] = Math.max(colTrack[0],s.getMassGainSnps().length());
				colTrack[1] = Math.max(colTrack[1],s.getMassGainScore().length());
				WritableCell wcParam1 = pathwaySheet.getWritableCell(0,i+initRow);
				WritableCellFormat cfParam1 = new WritableCellFormat(wcParam1.getCellFormat());
				WritableFont wfParam1 = new WritableFont(cfParam1.getFont());
				wfParam1.setBoldStyle(WritableFont.BOLD);
				cfParam1.setFont(wfParam1);
				wcParam1.setCellFormat(cfParam1);
				
				pathwaySheet.addCell(new Label(2,i+initRow,s.getMassChiSnps()));
				pathwaySheet.addCell(new Label(3,i+initRow,s.getMassChiScore()));
				colTrack[2] = Math.max(colTrack[2],s.getMassChiSnps().length());
				colTrack[3] = Math.max(colTrack[3],s.getMassChiScore().length());
				WritableCell wcParam2 = pathwaySheet.getWritableCell(2,i+initRow);
				WritableCellFormat cfParam2 = new WritableCellFormat(wcParam2.getCellFormat());
				WritableFont wfParam2 = new WritableFont(cfParam2.getFont());
				wfParam2.setBoldStyle(WritableFont.BOLD);
				cfParam2.setFont(wfParam2);
				wcParam2.setCellFormat(cfParam2);
				
				pathwaySheet.addCell(new Label(4,i+initRow,s.getMassGiniSnps()));
				pathwaySheet.addCell(new Label(5,i+initRow,s.getMassGiniScore()));
				colTrack[4] = Math.max(colTrack[4],s.getMassGiniSnps().length());
				colTrack[5] = Math.max(colTrack[5],s.getMassGiniScore().length());
				WritableCell wcParam3 = pathwaySheet.getWritableCell(4,i+initRow);
				WritableCellFormat cfParam3 = new WritableCellFormat(wcParam3.getCellFormat());
				WritableFont wfParam3 = new WritableFont(cfParam3.getFont());
				wfParam3.setBoldStyle(WritableFont.BOLD);
				cfParam3.setFont(wfParam3);
				wcParam3.setCellFormat(cfParam3);
				
				pathwaySheet.addCell(new Label(6,i+initRow,s.getMassApdSnps()));
				pathwaySheet.addCell(new Label(7,i+initRow,s.getMassApdScore()));
				colTrack[6] = Math.max(colTrack[6],s.getMassApdSnps().length());
				colTrack[7] = Math.max(colTrack[7],s.getMassApdScore().length());
				WritableCell wcParam4 = pathwaySheet.getWritableCell(6,i+initRow);
				WritableCellFormat cfParam4 = new WritableCellFormat(wcParam4.getCellFormat());
				WritableFont wfParam4 = new WritableFont(cfParam4.getFont());
				wfParam4.setBoldStyle(WritableFont.BOLD);
				cfParam4.setFont(wfParam4);
				wcParam4.setCellFormat(cfParam4);
				
				pathwaySheet.addCell(new Label(8,i+initRow,s.getMassOverallSnps()));
				pathwaySheet.addCell(new Label(9,i+initRow,s.getMassOverallScore()));
				colTrack[8] = Math.max(colTrack[8],s.getMassOverallSnps().length());
				colTrack[9] = Math.max(colTrack[9],s.getMassOverallScore().length());
				WritableCell wcParam5 = pathwaySheet.getWritableCell(8,i+initRow);
				WritableCellFormat cfParam5 = new WritableCellFormat(wcParam5.getCellFormat());
				WritableFont wfParam5 = new WritableFont(cfParam5.getFont());
				wfParam5.setBoldStyle(WritableFont.BOLD);
				cfParam5.setFont(wfParam5);
				wcParam5.setCellFormat(cfParam5);
				
			}
			else if(this.method.equals(MethodConstant.COMPARATIVE)){
				
				pathwaySheet.addCell(new Label(0,i+initRow,s.getPiaOverallSnps()));
				pathwaySheet.addCell(new Label(1,i+initRow,s.getPiaOverallScore()));
				colTrack[0] = Math.max(colTrack[0],s.getPiaOverallSnps().length());
				colTrack[1] = Math.max(colTrack[1],s.getPiaOverallScore().length());
				WritableCell wcParam1 = pathwaySheet.getWritableCell(0,i+initRow);
				WritableCellFormat cfParam1 = new WritableCellFormat(wcParam1.getCellFormat());
				WritableFont wfParam1 = new WritableFont(cfParam1.getFont());
				wfParam1.setBoldStyle(WritableFont.BOLD);
				cfParam1.setFont(wfParam1);
				wcParam1.setCellFormat(cfParam1);
				
				pathwaySheet.addCell(new Label(2,i+initRow,s.getMdrBalancedAccSnps()));
				pathwaySheet.addCell(new Label(3,i+initRow,s.getMdrBalancedAccScore()));
				colTrack[2] = Math.max(colTrack[2],s.getMdrBalancedAccSnps().length());
				colTrack[3] = Math.max(colTrack[3],s.getMdrBalancedAccScore().length());
				WritableCell wcParam2 = pathwaySheet.getWritableCell(2,i+initRow);
				WritableCellFormat cfParam2 = new WritableCellFormat(wcParam2.getCellFormat());
				WritableFont wfParam2 = new WritableFont(cfParam2.getFont());
				wfParam2.setBoldStyle(WritableFont.BOLD);
				cfParam2.setFont(wfParam2);
				wcParam2.setCellFormat(cfParam2);
				
				pathwaySheet.addCell(new Label(4,i+initRow,s.getEsnp2DeltaRSnps()));
				pathwaySheet.addCell(new Label(5,i+initRow,s.getEsnp2DeltaRScore()));
				colTrack[4] = Math.max(colTrack[4],s.getEsnp2DeltaRSnps().length());
				colTrack[5] = Math.max(colTrack[5],s.getEsnp2DeltaRScore().length());
				WritableCell wcParam3 = pathwaySheet.getWritableCell(4,i+initRow);
				WritableCellFormat cfParam3 = new WritableCellFormat(wcParam3.getCellFormat());
				WritableFont wfParam3 = new WritableFont(cfParam3.getFont());
				wfParam3.setBoldStyle(WritableFont.BOLD);
				cfParam3.setFont(wfParam3);
				wcParam3.setCellFormat(cfParam3);
				
				pathwaySheet.addCell(new Label(6,i+initRow,s.getMassOverallSnps()));
				pathwaySheet.addCell(new Label(7,i+initRow,s.getMassOverallScore()));
				colTrack[6] = Math.max(colTrack[6],s.getMassOverallSnps().length());
				colTrack[7] = Math.max(colTrack[7],s.getMassOverallScore().length());
				WritableCell wcParam4 = pathwaySheet.getWritableCell(6,i+initRow);
				WritableCellFormat cfParam4 = new WritableCellFormat(wcParam4.getCellFormat());
				WritableFont wfParam4 = new WritableFont(cfParam4.getFont());
				wfParam4.setBoldStyle(WritableFont.BOLD);
				cfParam4.setFont(wfParam4);
				wcParam4.setCellFormat(cfParam4);
				
			}
			
		}
		
		// Setting Column Width
		for(int i = 0 ; i < colTrack.length ; i++){
			pathwaySheet.setColumnView(i,Math.min(colTrack[i]+8,50));
		}
		
	}
	
	/* Testing Main 
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException {
		
		WritableWorkbook workbook = null;
		workbook = Workbook.createWorkbook(new File(SystemConstant.DEFAULT_FILE_ANALYSIS_XLS));
		
		WritableSheet sheet1 = workbook.createSheet("First Sheet", 0);
		WritableSheet sheet2 = workbook.createSheet("Second Sheet", 1);
		
		Label label1 = new Label(0,0,"Teste1"); 
		sheet1.addCell(label1);
		Label label2 = new Label(0,1,"1.111"); 
		sheet1.addCell(label2);
		Label label3 = new Label(1,0,"Teste2"); 
		sheet1.addCell(label3);
		Label label4 = new Label(1,1,2.222+""); 
		sheet1.addCell(label4);
		
		Label label5 = new Label(3,3,"testemerge"); 
		sheet1.addCell(label5);
		
		Label label1s = new Label(0,0,"0,0"); 
		sheet2.addCell(label1s);
		Label label2s = new Label(0,1,"0,1"); 
		sheet2.addCell(label2s);
		Label label3s = new Label(1,0,"1,0"); 
		sheet2.addCell(label3s);
		Label label4s = new Label(1,1,"1,1"); 
		sheet2.addCell(label4s);
		
		sheet1.mergeCells(3,3,4,5);
		
		WritableCell wc = sheet1.getWritableCell(3,3);
		WritableCellFormat cf = new WritableCellFormat(wc.getCellFormat());
		WritableFont wf = new WritableFont(cf.getFont());
		wf.setBoldStyle(WritableFont.BOLD);
		cf.setFont(wf);
		cf.setBackground(Colour.PALE_BLUE);
		cf.setAlignment(Alignment.CENTRE);
		cf.setVerticalAlignment(VerticalAlignment.CENTRE);
		wc.setCellFormat(cf);

		workbook.write(); 
		workbook.close(); 

	}
	*/
	
}
