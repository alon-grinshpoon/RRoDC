package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.problems.SingleRegionPlacementProblem;

public class Main {

	public final static int AVAILABLE_RESOURCES = 7;
	public final static int NUMBER_OF_REGIONS = 1;
	public final static int NUMBER_OF_TYPES = 4;

	public static void main(String[] args) {
		// Run single region, multiple types of service
		new SingleRegionPlacementProblem(NUMBER_OF_TYPES).solve().print();
	}
}