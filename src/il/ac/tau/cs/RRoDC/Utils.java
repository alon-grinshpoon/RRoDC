package il.ac.tau.cs.RRoDC;

import java.util.List;

import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

public class Utils {

	/**
	 * Expected input file names
	 */
	public final static String COST_FILENAME = "/c.txt";
	public final static String DEMANDS_FILENAME = "/d.txt";
	public final static String LOCAL_REVENUES_FILENAME = "/Rloc.txt";
	
	/**
	 * 
	 * @param marginalRevenues A list of marginal revenue vectors
	 * @return True is all front lines in the marginal revenue vectors point to negative values
	 */
	public static boolean allFrontLinesAreNegative(List<Rmarginal> marginalRevenues) {
		boolean allFrontLinesAreNegative = true;
		for (Rmarginal marginalRevenue : marginalRevenues) {
			if (!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() > 0) {
				allFrontLinesAreNegative = false;
				break;
			}
		}
		return allFrontLinesAreNegative;
	}

	/**
	 * Prints an error and exits the program immediately
	 * @param message The message that will be shown before exiting
	 */
	public static void ErrorAndExit(String message) {
		System.err.println("[ERROR] " + message);
		System.exit(1);
	}

}
