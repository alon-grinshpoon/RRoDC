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
import il.ac.tau.cs.RRoDC.revenues.Rloc;
import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class representing the single region optimal resource placement problem
 * Single region and m types of services. 
 * @author Alon Grinshpoon
 */
public class SingleRegionPlacementProblem implements Problem {
	
	private String pathToInputFiles;
	
	private Region region;
	private int numberOfTypes;
	private List<Rmarginal> marginalRevenues;

	/**
	 * Construct a new Single Region Placement Problem
	 * @param pathToInputFiles A path to the inputs files
	 */
	public SingleRegionPlacementProblem(String pathToInputFiles) {
		Utils.DEBUG_println("-> Starting a new Single Region Placement Problem...!");
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
            lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.COST_FILENAME),
                    Charset.defaultCharset());
           // Set Cost
           Cost.setCost(Double.parseDouble(lines.get(0)));
           // Print Cost
           Utils.DEBUG_println("--> Cost is " + Cost.getCost());
           /*
            * Region and local revenue
            */
           // Get Region and local revenue
           lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.LOCAL_REVENUES_FILENAME),
                   Charset.defaultCharset());
           // Set Region and local revenue
           String regionName = lines.get(0).split(",")[0].trim();
           int localRevenue = Integer.parseInt(lines.get(0).split(",")[1].trim());
           region = new Region(regionName, new Rloc(localRevenue));
           // Print Region and local revenue
           Utils.DEBUG_println("--> Region is " + region.getName() + " and local revenue is " + region.getLocalRevenue().getLocalRevenue());
           /*
            * Demand Probability Vectors
            */
           // Get Demands
           lines = Files.readAllLines(Paths.get(pathToInputFiles + Utils.DEMANDS_FILENAME),
                   Charset.defaultCharset());
           // Set Demands
           int index = 0;
           numberOfTypes = lines.size();
           List<Double> values;
           ValuesVector[] valuesVectors = new ValuesVector[numberOfTypes];
           DemandPDF[] demandPDFs = new DemandPDF[numberOfTypes];
           DemandCDF[] demandCDFs = new DemandCDF[numberOfTypes];
           DemandComplement[] demandComplements = new DemandComplement[numberOfTypes];
           Rmarginal[] marginalRevenues = new Rmarginal[numberOfTypes];
           for (String line : lines){
        	   // Create the types' probability vector
        	   values = new ArrayList<Double>();
        	   for (String value : line.split(",")){
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
           Utils.DEBUG_println("--> Values Vectors are: " + Arrays.toString(valuesVectors));
           Utils.DEBUG_println("--> Demand PDFs are: " + Arrays.toString(demandPDFs));
           Utils.DEBUG_println("--> Demand CDFs are: " + Arrays.toString(demandCDFs));
           Utils.DEBUG_println("--> Demand Complements are: " + Arrays.toString(demandComplements));
           Utils.DEBUG_println("--> Marginal Revenues are: " + Arrays.toString(marginalRevenues));
           
           // Store marginal revenues
           this.marginalRevenues = new ArrayList<Rmarginal>(Arrays.asList(marginalRevenues));
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	@Override
	public Solution solve() {
		
		if (Main.HIJACK){
			///////// Hijack marginal revenue vectors /////////
	        this.marginalRevenues.get(0).valuesVector = new ValuesVector(1.0, 0.8, 0.4, -0.9);
	        this.marginalRevenues.get(1).valuesVector = new ValuesVector(0.9, 0.7, 0.5, 0.4);
	        this.marginalRevenues.get(2).valuesVector = new ValuesVector(0.7, 0.6, 0.2, -0.1);
	        this.marginalRevenues.get(3).valuesVector = new ValuesVector(0.55, 0.1, -0.1, 0.0);
	        //////////////////////////////////////////////////
		}
        
		/*
		 * Run greedy algorithm for optimal resource placement
		 */
		
		Resources resources = new Resources();
		
		// If number of resources limited: stop when you finish resources.
		// If number resources are unlimited: stop when the FRONT LINE is all negative. 
		while(resources.getAmount() < Main.AVAILABLE_RESOURCES && !Utils.allFrontLinesAreNegative(marginalRevenues)){
			// GREEDY: add the resource who have highest value of the demand marginal revenue. Always on FRONT LINE
			// Search for the right resource
			double max = Double.NEGATIVE_INFINITY;
			Rmarginal maxMarginalRevenue = null; 
			for (Rmarginal marginalRevenue : marginalRevenues){
				if (!marginalRevenue.isExhausted() && marginalRevenue.getFrontlineValue() > max){
					max = marginalRevenue.getFrontlineValue();
					maxMarginalRevenue = marginalRevenue;
				}
			}
			// Add the resource
			resources.add(maxMarginalRevenue, maxMarginalRevenue.getFrontLine());
		}
		
		// Calculate revenue
		double revenue = 0;
		for (Rmarginal marginalRevenue : resources.getChosenResources().keySet()){
			for (Resource resouce : resources.getChosenResources().get(marginalRevenue)){
				revenue += resouce.getMarginalRevenue();
			}
		}
		
		// Output solution
		return new Solution(resources, revenue);
	}
	
	/**
	 * @return The region of this single region problem.
	 */
	public Region getRegion() {
		return this.region;
	}

	/**
	 * @return The number of types in this single region problem.
	 */
	public int getNumberOfTypes() {
		return this.numberOfTypes;
	}

	/**
	 * @return A list of the marginal revenue vectors of this  single region problem.
	 */
	public List<Rmarginal> getMarginalRevenues() {
		return this.marginalRevenues;
	}

}
