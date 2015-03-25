package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Function;
import il.ac.tau.cs.RRoDC.Region;
import il.ac.tau.cs.RRoDC.Type;
import il.ac.tau.cs.RRoDC.Utils;
import il.ac.tau.cs.RRoDC.ValuesVector;

/**
 * A class representing a demand's probability density function
 * 
 * @author Alon Grinshpoon
 */

public class DemandPDF extends Function {

	/**
	 * Construct a demand PDF and validate probabilities
	 * @param demandCDF The original CDF
	 */
	public DemandPDF(Type type, Region region, ValuesVector probabilityVector) {
		super(type, region);
		this.valuesVector = probabilityVector;
		
		// Verify values
		double sum = 0;
		for (double value : this.valuesVector){
			if (Math.abs(value) > 1 || Math.abs(value) < 0){
				Utils.ErrorAndExit("Given an invalid probability value. All probabilities must be between 0 and 1.");
			}
			sum += value;
		}
		if (sum != 1.0){
			Utils.ErrorAndExit("All probabilities must sum up to 1.");
		}
	}

	/**
	 * Set the resources' demand probability vector
	 * 
	 * @param demandVector
	 */
	public void setProbabilityVector(ValuesVector probabilityVector) {
		this.valuesVector = probabilityVector;
	}

	/**
	 * @return The resources' demand probabilities vector
	 */
	public ValuesVector getProbabilityVector() {
		return valuesVector;
	}
	
	@Override
	public String toString() {
		return "Demand PDF of type " + this.getType() + " in region " + this.getRegion() + " is " + valuesVector.toString();
	}
}
