package analysis;

/**
 * 
 * Class that contains all parameters to the methods
 * 
 * @author egg
 *
 */
public class Parameter {
	
	// Operational Parameters
	public String PATH; // The path to the base to be analysed
	public boolean USE_PATHWAY; // Usage of pathway
	public int ORDER; // Order of interaction
	public int N_TOP; // Number of Top combinations returned
	public boolean COUNT_MISSING; // Use the information of missing data or hide it.
	
	// PIA Parameters
	public int PIA_SHUFFLE; // How many times it will perform a random shuffle on each cross-validation start
	public double PIA_FRACT; // The size of the cross validation interval
	public double PIA_RATSL; // Cutoff too imbalanced combinations
	public int PIA_NTIME; // Number of times cross-validation will be performed
	public boolean PIA_IFRACT; // Usage of Fractional Occupation
	public boolean PIA_ITRAIN; // Usage of training data to populate contingency table
	public int PIA_LOOTR; // If ITRAIN = true -> Kind of strategy of train usage will be performed
	public boolean PIA_HIDE_SS; // If false, the SS score will not be used, that assures that the maximum score is 300
	
	// MDR Parameters
	public double MDR_T; // Threshold for low-risk and high-risk assignments
	public int MDR_SHUFFLE; // How many times it will perform a random shuffle on each cross-validation start
	public double MDR_FRACT; // The size of the cross validation interval
	public int MDR_NTIME; // Number of times cross-validation will be performed
	
	// ESNP2 Parameters
	//public int ESNP2_SHUFFLE; // Initial Shuffle only
	
	// MASS Parameters
	public double MASS_RATSL; // Cutoff too imbalanced combinations
	public boolean MASS_SIMPLE_ASSIGNMENT; // If true Dont make set theory assignments
	public boolean MASS_USE_GAIN; // Use Gain Score on Overall Summation
	public boolean MASS_USE_CHI; // Use Chi-Square Score on Overall Summation
	public boolean MASS_USE_GINI; // Use Gini Index Score on Overall Summation
	public boolean MASS_USE_APD; // Use APD Score on Overall Summation
	
}
