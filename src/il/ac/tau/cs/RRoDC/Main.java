package il.ac.tau.cs.RRoDC;

import java.util.ArrayList;
import java.util.List;

import il.ac.tau.cs.RRoDC.demands.DemandPDF;
import il.ac.tau.cs.RRoDC.demands.ProbabilityVector;
import il.ac.tau.cs.RRoDC.revenues.C;
import il.ac.tau.cs.RRoDC.revenues.Rloc;

public class Main {

	public final static int AVAILABLE_RESOURCES = 7;
	public final static int NUMBER_OF_REGIONS = 1;
	public final static int NUMBER_OF_TYPES = 4;
	public final static int NUMBER_OF_TIME_SLOTS = 5;

	public static void main(String[] args) {
		// Run single region, multiple types of service
		SingleRegionPlacementProblem(NUMBER_OF_TYPES);
	}

	private static void SingleRegionPlacementProblem(int numberOfTypes) {
		
		/*
		 * Create input for optimal resource placement algorithm
		 */
		// Create a new region
		Region region = new Region("Italy", new Rloc(1));
		// Create the types' probabilty vectors
		ProbabilityVector d1 = new ProbabilityVector(1.0, 0.8, 0.4, -0.9);
		ProbabilityVector d2 = new ProbabilityVector(0.9, 0.7, 0.5, 0.4);
		ProbabilityVector d3 = new ProbabilityVector(0.7, 0.6, 0.2, -0.1);
		ProbabilityVector d4 = new ProbabilityVector(0.55, 0.1, -0.1, 0.0);
		// Create the types' demand PDFs
		DemandPDF dPDF1 = new DemandPDF(new Type("1"), region, d1);
		DemandPDF dPDF2 = new DemandPDF(new Type("2"), region, d2);
		DemandPDF dPDF3 = new DemandPDF(new Type("3"), region, d3);
		DemandPDF dPDF4 = new DemandPDF(new Type("4"), region, d4);
		// Create a new cost
		C c = new C();
		
		/*
		 * Run greedy algorithm
		 */
		Resources resources = new Resources();
		List<DemandPDF> dPDFs = new ArrayList<>();
		dPDFs.add(dPDF1);
		dPDFs.add(dPDF2);
		dPDFs.add(dPDF3);
		dPDFs.add(dPDF4);
		// If number of resources limited: stop when you finish resources.
		// If number resources are unlimited: stop when the FRONT  LINE is all negative. 
		while(resources.getAmount() < AVAILABLE_RESOURCES && !Utils.allFrontLinesAreNegative(dPDF1, dPDF2, dPDF3, dPDF4)){
			// GREEDY: add the resource who have highest value of the demand PDFs. Always on FRONT LINE
			// Search for the right resource
			double max = Double.MIN_VALUE;
			DemandPDF maxPDF = null; 
			for (DemandPDF dPDF : dPDFs){
				if (dPDF.getFrontlineValue() > max){
					max = dPDF.getFrontlineValue();
					maxPDF = dPDF;
				}
			}
			// Add the resource
			resources.add(maxPDF, maxPDF.getFrontLine());
		}
		resources.print();
	}
}
