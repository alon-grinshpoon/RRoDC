package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.demands.DemandPDF;

public class Utils {

	public static boolean allFrontLinesAreNegative(DemandPDF... dPDFs) {
		boolean allFrontLinesAreNegative = true;
		for (DemandPDF dPDF : dPDFs) {
			if (dPDF.getFrontlineValue() > 0) {
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
