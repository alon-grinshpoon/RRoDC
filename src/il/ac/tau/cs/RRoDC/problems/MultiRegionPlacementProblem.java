package il.ac.tau.cs.RRoDC.problems;

import il.ac.tau.cs.RRoDC.Main;
import il.ac.tau.cs.RRoDC.Region;
import il.ac.tau.cs.RRoDC.Resources;
import il.ac.tau.cs.RRoDC.Type;
import il.ac.tau.cs.RRoDC.Utils;
import il.ac.tau.cs.RRoDC.ValuesVector;
import il.ac.tau.cs.RRoDC.Resources.Resource;
import il.ac.tau.cs.RRoDC.demands.DemandCDF;
import il.ac.tau.cs.RRoDC.demands.DemandComplement;
import il.ac.tau.cs.RRoDC.demands.DemandPDF;
import il.ac.tau.cs.RRoDC.revenues.Cost;
import il.ac.tau.cs.RRoDC.revenues.Rglo;
import il.ac.tau.cs.RRoDC.revenues.Rloc;
import il.ac.tau.cs.RRoDC.revenues.Rmarginal;
import il.ac.tau.cs.RRoDC.revenues.RmarginalForMultiProblemsAdd;
import il.ac.tau.cs.RRoDC.revenues.RmarginalForMultiProblemsRemove;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing the multiple regions optimal resource placement problem
 * multiple regions and a single type of service.
 * 
 * @author Alon Grinshpoon
 */
public class MultiRegionPlacementProblem implements Problem {

	private String pathToInputFiles;

	private Type type;
	private int numberOfRegions;
	private Region[] regions;
	private Rmarginal[] marginalRevenuesAdd;
	private Rmarginal[] marginalRevenuesRemove;
	private List<Rmarginal> marginalRevenues;
	private int repositionConstraint;

	/**
	 * Construct a new Multi-Region Placement Problem
	 * 
	 * @param pathToInputFiles
	 *            A path to the inputs files
	 */
	public MultiRegionPlacementProblem(String pathToInputFiles) {
		Utils.DEBUG_println("-> Starting a new Multi-Region Placement Problem...!");
		this.pathToInputFiles = pathToInputFiles;
		Utils.DEBUG_println("-> Getting user input from files..");
		getInput();
		Utils.DEBUG_println("-> Got user input!");
	}

	/**
	 * Obtain input for optimal resource placement algorithm
	 */
	@Override
	public void getInput() {
		try {
			List<String> lines;

			/*
			 * Cost
			 */
			// Get Cost
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.COST_FILENAME), Charset.defaultCharset());
			// Set Cost
			Cost.setCost(Double.parseDouble(lines.get(0)));
			// Print Cost
			Utils.DEBUG_println("--> Cost is " + Cost.getCost());
			
			/*
			 * Type
			 */
			// Get Type
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.TYPE_FILENAME), Charset.defaultCharset());
			// Set Type
			type = new Type(lines.get(0));
			// Print Type
			Utils.DEBUG_println("--> Type is " + type);
			
			/*
			 * Reposition Constraint
			 */
			// Get Reposition Constraint
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.CONSTRAINT_FILENAME), Charset.defaultCharset());
			// Set Reposition Constraint
			this.repositionConstraint = Integer.parseInt(lines.get(0));
			// Print Reposition Constraint
			Utils.DEBUG_println("--> Reposition Constraint is " + this.repositionConstraint);
			
			/*
			 * Regions and local revenue
			 */
			// Get Regions and local revenue
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.LOCAL_REVENUES_FILENAME), Charset.defaultCharset());
			// Set Region and local revenue
			int index = 0;
			numberOfRegions = lines.size();
			regions = new Region[numberOfRegions];
			for (String line : lines) {
				String regionName = line.split(",")[0].trim();
				int localRevenue = Integer.parseInt(line.split(",")[1].trim());
				regions[index] = new Region(regionName, new Rloc(localRevenue));
				index++;
			}
			// Print Region and local revenue
			Utils.DEBUG_println("--> Regions are:");
			for (Region region : regions) {
				Utils.DEBUG_println("---> " + region.getName() + " with a local revenue of " + region.getLocalRevenue().getLocalRevenue());
			}
			
			/*
			 * Global Revenue
			 */
			// Get Global Revenue
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.GLOBAL_REVENUE_FILENAME), Charset.defaultCharset());
			// Set Global Revenue
			Rglo.setGlobalRevenue(Double.parseDouble(lines.get(0)));
			// Print Global Revenue
			Utils.DEBUG_println("--> Global Revenue is " + Rglo.getGlobalRevenew());
			
			/*
			 * Demand Probability Vectors
			 */
			// Get Demands
			lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.DEMANDS_FILENAME), Charset.defaultCharset());
			// Set Demands
			index = 0;
			List<Double> values;
			ValuesVector[] valuesVectors = new ValuesVector[numberOfRegions];
			DemandPDF[] demandPDFs = new DemandPDF[numberOfRegions];
			DemandCDF[] demandCDFs = new DemandCDF[numberOfRegions];
			DemandComplement[] demandComplements = new DemandComplement[numberOfRegions];
			Rmarginal[] marginalRevenuesAdd = new Rmarginal[numberOfRegions];
			Rmarginal[] marginalRevenuesRemove = new Rmarginal[numberOfRegions];
			for (String line : lines) {
				// Create the regions' probability vector
				values = new ArrayList<Double>();
				for (String value : line.split(",")) {
					values.add(Double.parseDouble(value.trim()));
				}
				valuesVectors[index] = new ValuesVector(values);
				// Create the regions' demand PDFs
				demandPDFs[index] = new DemandPDF(type, regions[index], valuesVectors[index]);
				// Create the regions' demand CDFs
				demandCDFs[index] = new DemandCDF(demandPDFs[index]);
				// Create the regions' demand complement CDFs
				demandComplements[index] = new DemandComplement(demandCDFs[index]);
				// Advance index
				index++;
			}
			// Compute the global demand vectors using convolution
			ValuesVector convolvedVector = null;
			DemandPDF demandPDF;
			DemandCDF demandCDF;
			DemandComplement demandComplement;
			//// Call Convolution
			for (int i = 0; i < numberOfRegions; i++) {
				convolvedVector = Utils.convolve(convolvedVector, demandPDFs[i].getProbabilityVector());
			}
			//// Set the global demand vectors
			Type globalType = new Type("Global");
			Rloc globalLocalRevenue = new Rloc(Rglo.getGlobalRevenew());
			Region globalRegion = new Region("Global", globalLocalRevenue);
			demandPDF = new DemandPDF(globalType, globalRegion, convolvedVector);
			demandCDF = new DemandCDF(demandPDF);
			demandComplement = new DemandComplement(demandCDF);
			// Compute regions' marginal revenue vectors
			for (int i = 0; i < numberOfRegions; i++) {
				// Create the regions' marginal revenue vectors for adding
				marginalRevenuesAdd[i] = new RmarginalForMultiProblemsAdd(regions[i].getLocalRevenue(), demandComplements[i],
						demandComplement);
				// Create the regions' marginal revenue vectors for removing
				marginalRevenuesRemove[i] = new RmarginalForMultiProblemsRemove(regions[i].getLocalRevenue(), demandComplements[i],
						demandComplement);
			}
			// Print Arrays
			Utils.DEBUG_println("--> Values Vectors are: " + Arrays.toString(valuesVectors));
			Utils.DEBUG_println("--> Demand PDFs are: " + Arrays.toString(demandPDFs));
			Utils.DEBUG_println("--> Demand CDFs are: " + Arrays.toString(demandCDFs));
			Utils.DEBUG_println("--> Demand Complements are: " + Arrays.toString(demandComplements));
			Utils.DEBUG_println("--> Gloal Demand PDF is: " + demandPDF.toString());
			Utils.DEBUG_println("--> Gloal Demand CDF is: " + demandCDF.toString());
			Utils.DEBUG_println("--> Gloal Demand Complement is: " + demandComplement.toString());
			Utils.DEBUG_println("--> Marginal Revenues for adding are: " + Arrays.toString(marginalRevenuesAdd));
			Utils.DEBUG_println("--> Marginal Revenues for removing are: " + Arrays.toString(marginalRevenuesRemove));

			// Store marginal revenues
			this.marginalRevenuesAdd = marginalRevenuesAdd;
			this.marginalRevenuesRemove = marginalRevenuesRemove;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Solution solve() {

		// Initially all resources can be added, therefore the marginal revenues are all addition marginal revenues
		marginalRevenues =  new ArrayList<Rmarginal>(Arrays.asList(marginalRevenuesAdd));
		
		if (Main.HIJACK) {
			// /////// Hijack marginal revenue vectors /////////
			this.marginalRevenues.get(0).valuesVector = new ValuesVector(1.0, 0.8, 0.7, 0.1);
			this.marginalRevenues.get(1).valuesVector = new ValuesVector(0.9, 0.6, 0.5, 0.4);
			this.marginalRevenues.get(2).valuesVector = new ValuesVector(-0.3, -0.8, 0.2, 0.1);
			this.marginalRevenues.get(3).valuesVector = new ValuesVector(-0.3, 0.1, 0.0, 0.0);
			// ////////////////////////////////////////////////
		}

		/*
		 * Run greedy algorithm for optimal resource placement for the multiple regions problem
		 */
		
		Resources resources = new Resources();
		
		// Stop when reaching the reposition constraint or when no attractive elements (positive on right, negative on left).
		int repositions = 0;
		while (repositions < this.repositionConstraint && !Utils.allFrontLinesAreUnattractive(marginalRevenues)) {
			// GREEDY: add the resource who have highest value of the demand
			// marginal revenue on both directions of the front line at the same time
			double max = 0;
			Rmarginal maxMarginalRevenue = null;
			int index = 0;
			int maxIndex = 0;
			boolean isRemove = false;
			for (Rmarginal marginalRevenue : marginalRevenues) {
				// Check right of the front line (positive marginal revenue) 
				if (!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() > max) {
					max = marginalRevenue.getFrontlineValue();
					maxMarginalRevenue = marginalRevenue;
					maxIndex = index;
					isRemove = false;
				}
				// Check left of the front line (negative marginal revenue) 
				if (!marginalRevenue.isUnutilized() && (marginalRevenue.get(marginalRevenue.getFrontLine() - 1) * (-1)) > max) {
					max = marginalRevenue.get(marginalRevenue.getFrontLine() - 1) * (-1);
					maxMarginalRevenue = marginalRevenue;
					maxIndex = index;
					isRemove = true;
				}
				index++;
			}
			// Reposition the resource
			if (isRemove){
				// Update the marginal revenue vector
				maxMarginalRevenue.setFrontlineValue(marginalRevenuesAdd[maxIndex].get(maxMarginalRevenue.getFrontLine()));
				// Remove
				resources.remove(maxMarginalRevenue, maxMarginalRevenue.getFrontLine());
			} else {
				// Update the marginal revenue vector
				maxMarginalRevenue.setFrontlineValue(marginalRevenuesRemove[maxIndex].get(maxMarginalRevenue.getFrontLine()));
				// Add
				resources.add(maxMarginalRevenue, maxMarginalRevenue.getFrontLine());
			}
			repositions++;
		}

		// Calculate revenue
		double revenue = 0;
		for (Rmarginal marginalRevenue : resources.getChosenResources().keySet()) {
			for (Resource resouce : resources.getChosenResources().get(marginalRevenue)) {
				revenue += resouce.getMarginalRevenue();
			}
		}

		// Output solution
		return new Solution(resources, revenue);
	}

	/**
	 * @return The single type in this multiple region problem.
	 */
	public Type getType() {
		return this.type;
	}

	/**
	 * @return The number of regions in this multiple region problem.
	 */
	public int getNumberOfRegions() {
		return this.numberOfRegions;
	}

	/**
	 * @return A list of the marginal revenue vectors of this multiple region problem.
	 */
	public List<Rmarginal> getMarginalRevenues() {
		return this.marginalRevenues;
	}

}
