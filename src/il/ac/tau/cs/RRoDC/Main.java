package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.problems.SingleRegionPlacementProblem;
import il.ac.tau.cs.RRoDC.problems.SingleRegionRepositionProblem;
import il.ac.tau.cs.RRoDC.problems.Solution;

public class Main {

	public final static int AVAILABLE_RESOURCES = 7;
	public final static int NUMBER_OF_REGIONS = 1;
	public final static int NUMBER_OF_TYPES = 4;
	public final static int REPOSITION_CONSTRAINT = 1;

	public static void main(String[] args) {
		// Run single region, multiple types of service
		// Optimal Resource Placement
		SingleRegionPlacementProblem singleRegionPlacementProblem = new SingleRegionPlacementProblem("C:/Users/Alon/OneDrive/Documents/לימודים/פרוייקטים/Cloud Research/Input");
		Solution placementProblemSolution = singleRegionPlacementProblem.solve();
		placementProblemSolution.printAll();
		// Optimal Resource Reposition Under Reposition Constraint
		SingleRegionRepositionProblem SingleRegionRepositionProblem = new SingleRegionRepositionProblem(placementProblemSolution, REPOSITION_CONSTRAINT);
	}
}