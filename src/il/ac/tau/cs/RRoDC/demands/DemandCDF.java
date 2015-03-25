package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Function;

/**
 * A class representing a demand's cumulative distribution function
 * 
 * @author Alon Grinshpoon
 * 
 */
public class DemandCDF extends Function {

	/**
	 * Construct a demand CDF by calculating it from a PDF
	 * 
	 * @param demandPDF
	 *            The original PDF
	 */
	public DemandCDF(DemandPDF demandPDF) {
		super(demandPDF.getType(), demandPDF.getRegion());
		// Clone original values
		this.valuesVector = demandPDF.valuesVector.clone();
		// Sum values
		// TODO Change to convolution
		for (int i = 1; i < this.valuesVector.size(); i++) {
			this.valuesVector.set(i, this.valuesVector.get(i) + this.valuesVector.get(i - 1));
		}
	}

}
