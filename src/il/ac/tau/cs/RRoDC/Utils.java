package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

public class Utils {

	public static boolean allFrontLinesAreNegative(Rmarginal... marginalRevenues) {
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
