package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Function;

/**
 * A class representing a demand's cumulative distribution function
 * 
 * @author Alon Grinshpoon
 * 
 */
public class DemandCDF extends Function {

	private final DemandPDF pdf;
	
	/**
	 * Construct a demand CDF by calculating it from a PDF
	 * 
	 * @param demandPDF
	 *            The original PDF
	 */
	public DemandCDF(DemandPDF demandPDF) {
		super(demandPDF.getType(), demandPDF.getRegion());
		this.pdf = demandPDF;
		// Clone original values
		this.valuesVector = this.pdf.valuesVector.clone();
		// Sum values
		for (int i = 1; i < this.valuesVector.size(); i++) {
			this.valuesVector.set(i, this.valuesVector.get(i) + this.valuesVector.get(i - 1));
		}
	}
	
	/**
	 * @return The demand PDF that created this demand CDF
	 */
	public DemandPDF getPdf() {
		return pdf;
	}
	
}
