package bean;

import io.TxtCreator;
import io.XlsCreator;

import java.io.File;
import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import jxl.write.WriteException;

import constants.MethodConstant;
import constants.SystemConstant;

import basic.Score;
import basic.Tuple;

/**
 * 
 * JSF Interface between JSP code and Java code representing the email results screen
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class EmailBean {

	// Basic Elements
	private String method;
	private String realPath;
	private String email;
	private ArrayList<Tuple>[] parameterList;
	private ArrayList<Tuple>[] resultList;
	private ArrayList<Tuple>[] timeList;
	private ArrayList<Score> pathwayList;

	public void sendEmail(){

		try {
			this.createAndSendMail();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void createAndSendMail() throws Exception{

		// Provider
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

		// Properties
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.quitwait", "false");

		// Session Authentication
		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{ return new PasswordAuthentication("multisnp","AzScTC3u4");	}
		});		

		// MimeMessage Header
		MimeMessage message = new MimeMessage(session);
		message.setSender(new InternetAddress("multisnp@gmail.com"));
		message.setSubject("MultiSNP Analysis Results");
		
		String emailBody = "body";
		
		message.setContent(emailBody, "text/plain");
		
		String recipient = this.email;
		if (recipient.indexOf(',') > 0) 
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

		// Create Files
		File xlsFile = new File(this.realPath+SystemConstant.DEFAULT_FILE_ANALYSIS_XLS);
		File txtFile = new File(this.realPath+SystemConstant.DEFAULT_FILE_ANALYSIS_TXT);
		
		// Create Multipart Object
		Multipart multipart = new MimeMultipart();
		
		// Create Body Part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		StringBuffer sb = new StringBuffer();
		sb.append("This Email contains the results of the MultiSNP Engine, if you didn't request this analysis please discard this email.");
		sb.append("\n\n\n");
		sb.append("This Email contains the following attachments:");
		sb.append("\n\n");
		sb.append("- Excel XLS Results File: Parameters, Results (SNP Ranking), Time Analysis and Pathway Analysis (if requested) are in each spreadsheet of an excel file. The values are all in text format (but easily changed to numeric format for chart creation or other purposes).");
		sb.append("\n\n");
		sb.append("- Simple TXT Format: Parameters, Results (SNP Ranking), Time Analysis and Pathway Analysis (if requested) comes under each other in simple text format. The format is easier to be read on further analysis.");
		sb.append("\n\n\n");
		sb.append("Please feel free to reply this email with any questions / suggestions / problem report.");
		sb.append("\n\n");
		sb.append("Thanks for using multiSNP Engine,");
		sb.append("\n");
		sb.append("MultiSNP Team.");
		sb.append("\n\n\n");
		String actualBody = sb.toString();
		messageBodyPart.setText(actualBody);
		multipart.addBodyPart(messageBodyPart);
		
		// Create XLS Attachment
		MimeBodyPart attachXlsPart = new MimeBodyPart();
		DataSource sourceXls = new FileDataSource(xlsFile);
		attachXlsPart.setDataHandler(new DataHandler(sourceXls));
		attachXlsPart.setFileName(SystemConstant.DEFAULT_FILE_ANALYSIS_XLS);
		multipart.addBodyPart(attachXlsPart);
		
		// Create TXT Attachment
		MimeBodyPart attachTxtPart = new MimeBodyPart();
		DataSource sourceTxt = new FileDataSource(txtFile);
		attachTxtPart.setDataHandler(new DataHandler(sourceTxt));
		attachTxtPart.setFileName(SystemConstant.DEFAULT_FILE_ANALYSIS_TXT);
		multipart.addBodyPart(attachTxtPart);

		// Put parts in message
		message.setContent(multipart);

		// Send the message
		Transport.send( message );
	}
	
	public void createFiles(){
		
		try {
			TxtCreator txt = new TxtCreator(this.method,this.parameterList,this.resultList,this.timeList,this.pathwayList,this.realPath);
			XlsCreator xls = new XlsCreator(this.method,this.parameterList,this.resultList,this.timeList,this.pathwayList,this.realPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		
	}
	
	public String returnToAnalysis(){
		String ret = "";
		if(this.method.equals(MethodConstant.PIA)){
			ret = "returnPia";
		}
		else if(this.method.equals(MethodConstant.MDR)){
			ret = "returnMdr";
		}
		else if(this.method.equals(MethodConstant.ESNP2)){
			ret = "returnEsnp2";
		}
		else if(this.method.equals(MethodConstant.MASS)){
			ret = "returnMass";
		}
		else if(this.method.equals(MethodConstant.COMPARATIVE)){
			ret = "returnComparative";
		}
		return ret;
	}

	// Getters and Setters

	public String getRealPath() {
		return realPath;
	}

	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	public ArrayList<Tuple>[] getParameterList() {
		return parameterList;
	}

	public void setParameterList(ArrayList<Tuple>[] parameterList) {
		this.parameterList = parameterList;
	}

	public ArrayList<Tuple>[] getResultList() {
		return resultList;
	}

	public void setResultList(ArrayList<Tuple>[] resultList) {
		this.resultList = resultList;
	}

	public ArrayList<Tuple>[] getTimeList() {
		return timeList;
	}

	public void setTimeList(ArrayList<Tuple>[] timeList) {
		this.timeList = timeList;
	}

	public ArrayList<Score> getPathwayList() {
		return pathwayList;
	}

	public void setPathwayList(ArrayList<Score> pathwayList) {
		this.pathwayList = pathwayList;
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
