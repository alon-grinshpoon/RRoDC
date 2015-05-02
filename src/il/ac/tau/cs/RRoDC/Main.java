package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.problems.MultiRegionPlacementProblem;
import il.ac.tau.cs.RRoDC.problems.Problem;
import il.ac.tau.cs.RRoDC.problems.SingleRegionPlacementProblem;
import il.ac.tau.cs.RRoDC.problems.SingleRegionRepositionProblem;
import il.ac.tau.cs.RRoDC.problems.Solution;

/**
 * The main running class
 * @author Alon Grinshpoon
 */
public class Main {

	/*
	 * Define Constants
	 */
	public final static boolean DEBUG_MODE = true;
	public final static boolean HIJACK = true;
	public final static int AVAILABLE_RESOURCES = 15;

	public static void main(String[] args) {
		
		/*
		 *  Run single region, multiple types of service
		 */
		
		// Optimal Resource Placement
		Problem singleRegionPlacementProblem = new SingleRegionPlacementProblem("C:/Users/Alon/OneDrive/Documents/לימודים/פרוייקטים/Cloud Research/Inputs/1. SingleRegionPlacementProblem/");
		Solution placementProblemSolution = singleRegionPlacementProblem.solve();
		placementProblemSolution.printAll();
		
		System.out.println();
		
		// Optimal Resource Reposition Under Reposition Constraint
		Problem SingleRegionRepositionProblem = new SingleRegionRepositionProblem("C:/Users/Alon/OneDrive/Documents/לימודים/פרוייקטים/Cloud Research/Inputs/2. SingleRegionRepositionProblem/");
		Solution repositionProblemSolution = SingleRegionRepositionProblem.solve();
		repositionProblemSolution.printAll();
		
		// Optimal Resource Reposition Under Reposition Constraint
		Problem multiRegionPlacementProblem = new MultiRegionPlacementProblem("C:/Users/Alon/OneDrive/Documents/לימודים/פרוייקטים/Cloud Research/Inputs/3. MultiRegionPlacementProblem/");
		Solution multiRegionPlacementProblemSolution = multiRegionPlacementProblem.solve();
		multiRegionPlacementProblemSolution.printAll();
	}
}