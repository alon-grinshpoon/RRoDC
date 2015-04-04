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
import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing the single region optimal resource reposition problem
 * Single region and m types of services.
 * 
 * @author Alon Grinshpoon
 */
public class SingleRegionRepositionProblem implements Problem {

	private String pathToInputFiles;

	private SingleRegionPlacementProblem prevProblem;
	private Region region;
	private int numberOfTypes;
	private List<Rmarginal> marginalRevenues;
	private int repositionConstraint;

	/**
	 * Construct a new Single Region Placement Problem
	 * 
	 * @param pathToInputFiles
	 *            A path to the inputs files
	 */
	public SingleRegionRepositionProblem(String pathToInputFiles) {
		Utils.DEBUG_println("-> Starting a new Single Region Reposition Problem...!");
		this.pathToInputFiles = pathToInputFiles;
		this.prevProblem = new SingleRegionPlacementProblem(pathToInputFiles);
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
           // Print Cost
           Utils.DEBUG_println("--> Cost is " + Cost.getCost());
			/*
			 * Region and local revenue
			 */
			this.region = prevProblem.getRegion();
			Utils.DEBUG_println("--> Region is " + region.getName() + " and local revenue is " + region.getLocalRevenue().getLocalRevenue());
			/*
			 * Number of types
			 */
			this.numberOfTypes = prevProblem.getNumberOfTypes();
			
			/*
			 * Reposition Constraint
			 */
			// Get Reposition Constraint
			lines = Files.readAllLines(
					Paths.get(pathToInputFiles + Utils.REPOSITION_CONSTRAINT_FILENAME),
					Charset.defaultCharset());
			// Set Reposition Constraint
			this.repositionConstraint = Integer.parseInt(lines.get(0));
			// Print Reposition Constraint
			Utils.DEBUG_println("--> Reposition Constraint is "	+ Cost.getCost());

			/*
			 * New Demand Probability Vectors
			 */
			// Get New Demands
			lines = Files.readAllLines(
					Paths.get(pathToInputFiles + Utils.NEW_DEMANDS_FILENAME),
					Charset.defaultCharset());
			// Set New Demands
			int index = 0;
			if (numberOfTypes != lines.size()){
				Utils.ErrorAndExit("You must have the same number of new demand vectors as old ones.");
			}
			List<Double> values;
			ValuesVector[] valuesVectors = new ValuesVector[numberOfTypes];
			DemandPDF[] demandPDFs = new DemandPDF[numberOfTypes];
			DemandCDF[] demandCDFs = new DemandCDF[numberOfTypes];
			DemandComplement[] demandComplements = new DemandComplement[numberOfTypes];
			Rmarginal[] marginalRevenues = new Rmarginal[numberOfTypes];
			for (String line : lines) {
				// Create the types' probability vector
				values = new ArrayList<Double>();
				for (String value : line.split(",")) {
					values.add(Double.parseDouble(value.trim()));
				}
				valuesVectors[index] = new ValuesVector(values);
				// Create the types' demand PDFs
				demandPDFs[index] = new DemandPDF(new Type(String.valueOf(index)), region, valuesVectors[index]);
				// Create the types' demand CDFs
				demandCDFs[index] = new DemandCDF(demandPDFs[index]);
				// Create the types' demand complement CDFs
				demandComplements[index] = new DemandComplement(demandCDFs[index]);
				// Create the types' marginal revenue vectors
				marginalRevenues[index] = new Rmarginal(region.getLocalRevenue(), demandComplements[index]);
				// Advance index
				index++;
			}
			
			// Print Arrays
			Utils.DEBUG_println("--> New Values Vectors are: " + Arrays.toString(valuesVectors));
			Utils.DEBUG_println("--> New Demand PDFs are: "	+ Arrays.toString(demandPDFs));
			Utils.DEBUG_println("--> New Demand CDFs are: " + Arrays.toString(demandCDFs));
			Utils.DEBUG_println("--> New Demand Complements are: " + Arrays.toString(demandComplements));
			Utils.DEBUG_println("--> New Marginal Revenues are: " + Arrays.toString(marginalRevenues));

			// Store marginal revenues
	        this.marginalRevenues = new ArrayList<Rmarginal>(Arrays.asList(marginalRevenues));
	        
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Solution solve() {

		/*
		 * Run greedy algorithm for optimal resource placement
		 */
		Solution prevSolution = prevProblem.solve();
		
		// Update marginal revenues vectors
		for (int i = 0; i < this.numberOfTypes; i++){
			this.marginalRevenues.get(i).setFrontLine(prevProblem.getMarginalRevenues().get(i).getFrontLine());
		}
		
		// Get previous chosen resources
		Resources resources = prevSolution.getResources();
		
		if (Main.HIJACK){
			///////// Hijack marginal revenue vectors /////////
			this.marginalRevenues.get(0).valuesVector = new ValuesVector(1.0, 0.8, 0.7, 0.1);
        	this.marginalRevenues.get(1).valuesVector = new ValuesVector(0.9, 0.6, 0.5, -0.4);
        	this.marginalRevenues.get(2).valuesVector = new ValuesVector(0.3, -0.8, -0.9, -1.1);
        	this.marginalRevenues.get(3).valuesVector = new ValuesVector(-0.3, -0.4, -0.5, -0.6);
			///////// Hijack resources /////////
			resources.getChosenResources().clear();
			for (Rmarginal marginalRevenue : this.marginalRevenues){
				for (int i = 0; i < marginalRevenue.getFrontLine(); i++){				
					resources.add(marginalRevenue, i);		
					// Increment Values
					resources.setAmount(resources.getAmount() - 1);
					marginalRevenue.regressFrontLine();
				}
			}
			//////////////////////////////////////////////////
		}
        Utils.DEBUG_println("--> Updated Marginal Revenues are: " + Arrays.toString(this.marginalRevenues.toArray()));
        
		/*
		 * Run greedy algorithm for optimal resource reposition
		 */
		
		// Stop when reaching the reposition constraint or when no attractive elements (positive on right, negative on left).
		int repositions = 0;
		while (repositions < this.repositionConstraint && !Utils.allFrontLinesAreUnattractive(marginalRevenues)) {
			// GREEDY: add the resource who have highest value of the demand
			// marginal revenue on both directions of the front line at the same time
			double max = 0;
			Rmarginal maxMarginalRevenue = null;
			boolean isRemoveRepositions = false;
			for (Rmarginal marginalRevenue : marginalRevenues) {
				if (!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() > Math.abs(max)) {
					max = marginalRevenue.getFrontlineValue();
					maxMarginalRevenue = marginalRevenue;
					isRemoveRepositions = false;
				}
				if (!marginalRevenue.isUnutilized() && Math.abs(marginalRevenue.get(marginalRevenue.getFrontLine() - 1)) > max) {
					max = Math.abs(marginalRevenue.get(marginalRevenue.getFrontLine() - 1));
					maxMarginalRevenue = marginalRevenue;
					isRemoveRepositions = true;
				}
			}
			// Reposition the resource
			if (isRemoveRepositions){
				resources.remove(maxMarginalRevenue, maxMarginalRevenue.getFrontLine());
			} else {
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

}
