package il.ac.tau.cs.RRoDC;

import java.util.ArrayList;
import java.util.List;
import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

public class Utils {

	/**
	 * Expected input file names
	 */
	public final static String COST_FILENAME = "/c.txt";
	public final static String DEMANDS_FILENAME = "/d.txt";
	public final static String NEW_DEMANDS_FILENAME = "/d_new.txt";
	public final static String LOCAL_REVENUES_FILENAME = "/Rloc.txt";
	public final static String GLOBAL_REVENUE_FILENAME = "/Rglo.txt";
	public final static String CONSTRAINT_FILENAME = "/constraint.txt";	
	public final static String TYPE_FILENAME = "/type.txt";	
	
	/**
	 * @param marginalRevenues A list of marginal revenue vectors
	 * @return True is all front lines in the marginal revenue vectors point to negative values
	 */
	public static boolean allFrontLinesAreNegative(List<Rmarginal> marginalRevenues) {
		boolean allFrontLinesAreNegative = true;
		for (Rmarginal marginalRevenue : marginalRevenues) {
			if (!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() >= 0) {
				allFrontLinesAreNegative = false;
				break;
			}
		}
		return allFrontLinesAreNegative;
	}
	
	/**
	 * @param marginalRevenues A list of marginal revenue vectors
	 * @return True is all front lines in the marginal revenue vectors have no attractive elements around (positive on right, negative on left).
	 */
	public static boolean allFrontLinesAreUnattractive(List<Rmarginal> marginalRevenues) {
		boolean allFrontLinesAreUnattractive = true;
		for (Rmarginal marginalRevenue : marginalRevenues) {
			if ((!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() >= 0)) {
				allFrontLinesAreUnattractive = false;
				break;
			}
			if ((!marginalRevenue.isUnutilized() && marginalRevenue.get(marginalRevenue.getFrontLine() - 1) < 0)) {
				allFrontLinesAreUnattractive = false;
				break;
			}
		}
		return allFrontLinesAreUnattractive;
	}

	/**
	 * Prints an error and exits the program immediately
	 * @param message The message that will be shown before exiting
	 */
	public static void ErrorAndExit(String message) {
		System.err.println("[ERROR] " + message);
		System.exit(1);
	}
	
	/**
	 * Print a message to the user's screen.
	 * @param message The message that will be shown
	 */
	public static void println(String message){
		System.out.println(message);
	}
	
	/**
	 * Print a blank message to the user's screen.
	 */
	public static void println(){
		println("");
	}
	
	/**
	 * Print a message to the user's screen.
	 * @param message The message that will be shown
	 */
	public static void print(String message){
		System.out.print(message);
	}
	
	/**
	 * Print a blank message to the user's screen.
	 */
	public static void print(){
		print("");
	}
	
	/**
	 * Print a message to the user's screen if debug mode is enabled.
	 * @param message The message that will be shown
	 */
	public static void DEBUG_println(String message){
		if (Main.DEBUG_MODE){
			System.out.println(message);
		}
	}
	
	/**
	 * Print a blank message to the user's screen if debug mode is enabled.
	 */
	public static void DEBUG_println(){
		if (Main.DEBUG_MODE){
			println("");
		}
	}
	
	/**
	 * Print a message to the user's screen if debug mode is enabled.
	 * @param message The message that will be shown
	 */
	public static void DEBUG_print(String message){
		if (Main.DEBUG_MODE){
			System.out.print(message);
		}
	}
	
	/**
	 * Print a blank message to the user's screen if debug mode is enabled.
	 */
	public static void DEBUG_print(){
		if (Main.DEBUG_MODE){
			print("");
		}
	}

	/**
	 * Discreetly Convolves two functions
	 * @param function1 A function
	 * @param function2 A function
	 * @return A ValuesVector which is the convolution product of the two two functions. If either of the functions is null, it returns the other function.
	 */
	public static ValuesVector convolve(Function function1, Function function2) {
		// Check for null input
		if (function1 == null && function2 == null){
			Utils.ErrorAndExit("At least one of the fucntion in the convolution must be non-null.");
		} else if (function1 == null){
			return function2.valuesVector.clone();
		} else if (function2 == null){
			return function1.valuesVector.clone();
		}
		// Check size compatibility of the convolved vectors
		if (function1.size() != function2.size()){
			Utils.ErrorAndExit("Convolution must be between same length functions.");
		}
		int size = function1.size();
		List<Double> convolutionValues = new ArrayList<Double>();
		// Convolve
		// c_n = a_n (+) b_n = a_0 * b_n + a_1 * b_(n-1) + ... + a_n * b_0
		for (int i = 0; i < size; i++){
			double sum = 0;
			for (int j = 0; j <= i; j++){
				sum += function1.get(j) * function2.get(i - j);
			}
			convolutionValues.add(i, sum);
		}

		return new ValuesVector(convolutionValues);
	}
}
