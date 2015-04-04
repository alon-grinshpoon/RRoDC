package il.ac.tau.cs.RRoDC;

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
	public final static String CONSTRAINT_FILENAME = "/constraint.txt";	
	
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
}
