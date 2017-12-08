package constants;

/**
 * 
 * Constants used in system configuration
 * 
 * @author Eduardo Gade Gusmao
 *
 */
public class SystemConstant {

	public static final String DEFAULT_FILE = "info.in";
	public static final String DEFAULT_FILE_ANALYSIS_TXT = "multiSNP_Analysis_Results.txt";
	public static final String DEFAULT_FILE_ANALYSIS_XLS = "multiSNP_Analysis_Results.xls";
	public static final char[] DATA_SEPARATION = {' ',',','\t','|','/','\\'};
	
	public static final String[] CONTROL_SYMBOLS = {"0","c","CONTROL","CON","N"};
	public static final String[] CASE_SYMBOLS = {"1","C","CASE","CAS","Y"};
	
	public static final String OUTPUT_SCREEN = "screen";
	public static final String OUTPUT_DOWNLOAD = "download";
	public static final String OUTPUT_EMAIL = "email";
	
}
