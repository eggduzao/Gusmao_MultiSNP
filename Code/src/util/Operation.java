package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.StringTokenizer;

public class Operation {

	/**
	 * Calculates the log2 of some value returning 0 if the value is 0 or less.
	 * @param v
	 * @return log of v in base 2
	 */
	public static double log2(double v){
		double ret = 0;
		if(v>0){
			ret = Math.log(v) / Math.log(2);
		}
		return ret;
	}
	
	/**
	 * Clones a matrix
	 * @return double[][]
	 */
	public static double[][] cloneMatrix(double[][] matrix){
		double[][] newMatrix = new double[matrix.length][matrix[0].length];
		for(int i = 0 ; i < matrix.length ; i++){
			newMatrix[i] = matrix[i].clone();
		}
		return newMatrix;
	}
	
	/**
	 * Converts an InputStream to String
	 * @param is
	 * @return String
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream is)
	throws IOException {
		
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(
						new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {       
			return "";
		}
		
	}
	
}
