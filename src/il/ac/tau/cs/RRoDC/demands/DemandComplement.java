package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Function;

/**
 * A class representing a demand's complement to a cumulative distribution function
 * 
 * @author Alon Grinshpoon
 * 
 */
public class DemandComplement extends Function {

	/**
	 * Construct a demand complement CDF by swapping every value in the original CDF with it's 1-complement
	 * @param demandCDF The original CDF
	 */
	public DemandComplement(DemandCDF demandCDF) {
		super(demandCDF.getType(), demandCDF.getRegion());
		// Clone original values
		this.valuesVector = demandCDF.valuesVector.clone();
		// 1-Complement all values
		for (int i = 0; i < this.valuesVector.size(); i++){
			// TODO Add the PDF(i) itself
			// Summing this entire vector should be the expectancy
			this.valuesVector.set(i, 1 - this.valuesVector.get(i));
		}
	}
}
