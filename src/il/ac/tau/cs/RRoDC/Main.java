package il.ac.tau.cs.RRoDC;

import java.util.ArrayList;
import java.util.List;

import il.ac.tau.cs.RRoDC.demands.DemandCDF;
import il.ac.tau.cs.RRoDC.demands.DemandComplement;
import il.ac.tau.cs.RRoDC.demands.DemandPDF;
import il.ac.tau.cs.RRoDC.revenues.Rloc;
import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

public class Main {

	public final static int AVAILABLE_RESOURCES = 7;
	public final static int NUMBER_OF_REGIONS = 1;
	public final static int NUMBER_OF_TYPES = 4;

	public static void main(String[] args) {
		// Run single region, multiple types of service
		SingleRegionPlacementProblem(NUMBER_OF_TYPES);
	}

	private static void SingleRegionPlacementProblem(int numberOfTypes) {
		
		/*
		 * Create input for optimal resource placement algorithm
		 */
		// Create a new region
		int localRevenue = 5;
		Region region = new Region("Italy", new Rloc(localRevenue));
		// Create the types' probability vectors
		ValuesVector d1 = new ValuesVector(0.25, 0.25, 0.25, 0.25);
		ValuesVector d2 = new ValuesVector(0.25, 0.25, 0.25, 0.25);
		ValuesVector d3 = new ValuesVector(0.25, 0.25, 0.25, 0.25);
		ValuesVector d4 = new ValuesVector(0.25, 0.25, 0.25, 0.25);
		// Create the types' demand PDFs
		DemandPDF dPDF1 = new DemandPDF(new Type("1"), region, d1);
		DemandPDF dPDF2 = new DemandPDF(new Type("2"), region, d2);
		DemandPDF dPDF3 = new DemandPDF(new Type("3"), region, d3);
		DemandPDF dPDF4 = new DemandPDF(new Type("4"), region, d4);
		// Create the types' demand CDFs
		DemandCDF dCDF1 = new DemandCDF(dPDF1);
		DemandCDF dCDF2 = new DemandCDF(dPDF2);
		DemandCDF dCDF3 = new DemandCDF(dPDF3);
		DemandCDF dCDF4 = new DemandCDF(dPDF4);
		// Create the types' demand complement CDFs
		DemandComplement dCCDF1 = new DemandComplement(dCDF1);
		DemandComplement dCCDF2 = new DemandComplement(dCDF2);
		DemandComplement dCCDF3 = new DemandComplement(dCDF3);
		DemandComplement dCCDF4 = new DemandComplement(dCDF4);
		// Create the types' marginal revenue vectors
		Rmarginal marginalRevenue1 = new Rmarginal(region.getLocalRevenue(), dCCDF1);
		Rmarginal marginalRevenue2 = new Rmarginal(region.getLocalRevenue(), dCCDF2);
		Rmarginal marginalRevenue3 = new Rmarginal(region.getLocalRevenue(), dCCDF3);
		Rmarginal marginalRevenue4 = new Rmarginal(region.getLocalRevenue(), dCCDF4);
		
		///////// Hijack marginal revenue vectors /////////
		marginalRevenue1.valuesVector = new ValuesVector(1.0, 0.8, 0.4, -0.9);
		marginalRevenue2.valuesVector = new ValuesVector(0.9, 0.7, 0.5, 0.4);
		marginalRevenue3.valuesVector = new ValuesVector(0.7, 0.6, 0.2, -0.1);
		marginalRevenue4.valuesVector = new ValuesVector(0.55, 0.1, -0.1, 0.0);
		//////////////////////////////////////////////////
		
		/*
		 * Run greedy algorithm
		 */
		Resources resources = new Resources();
		List<Rmarginal> marginalRevenues = new ArrayList<>();
		marginalRevenues.add(marginalRevenue1);
		marginalRevenues.add(marginalRevenue2);
		marginalRevenues.add(marginalRevenue3);
		marginalRevenues.add(marginalRevenue4);
		// If number of resources limited: stop when you finish resources.
		// If number resources are unlimited: stop when the FRONT LINE is all negative. 
		while(resources.getAmount() < AVAILABLE_RESOURCES && !Utils.allFrontLinesAreNegative(marginalRevenue1, marginalRevenue2, marginalRevenue3, marginalRevenue4)){
			// GREEDY: add the resource who have highest value of the demand marginal revenue. Always on FRONT LINE
			// Search for the right resource
			double max = Double.MIN_VALUE;
			Rmarginal maxMarginalRevenue = null; 
			for (Rmarginal marginalRevenue : marginalRevenues){
				if (marginalRevenue.getFrontlineValue() > max){
					max = marginalRevenue.getFrontlineValue();
					maxMarginalRevenue = marginalRevenue;
				}
			}
			// Add the resource
			resources.add(maxMarginalRevenue, maxMarginalRevenue.getFrontLine());
		}
		resources.print();
	}
}
