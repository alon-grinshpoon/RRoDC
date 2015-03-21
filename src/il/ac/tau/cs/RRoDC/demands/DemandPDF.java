package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Region;
import il.ac.tau.cs.RRoDC.Type;

/**
 * A class representing a demand's probability density function
 * 
 * @author Alon Grinshpoon
 */

public class DemandPDF {

	private final Type type;
	private final Region region;
	private ProbabilityVector probabilityVector;
	private int frontLine = 0;

	public DemandPDF(Type type, Region region, ProbabilityVector probabilityVector) {
		this.type = type;
		this.region = region;
		this.probabilityVector = probabilityVector;
		// TODO Verify probabilty vector
	}

	/**
	 * @return The resources' type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return The resources' region
	 */
	public Region getRegion() {
		return region;
	}

	/**
	 * Set the resources' demand vector
	 * 
	 * @param demandVector
	 */
	public void setDemandVector(ProbabilityVector probabilityVector) {
		this.probabilityVector = probabilityVector;
	}

	/**
	 * @return The resources' probability vector
	 */
	public ProbabilityVector getProbabilityVector() {
		return probabilityVector;
	}

	/**
	 * Set the index front line in the PDF's probability vector
	 * @param frontLine
	 */
	public void setFrontLine(int frontLine) {
		this.frontLine = frontLine;
	}

	/**
	 * @return The index of the front line in the PDF's probability vector
	 */
	public int getFrontLine() {
		return frontLine;
	}
	
	/**
	 * @return The value that is at the front line of the PDF
	 */
	public double getFrontlineValue() {
		return this.getProbabilityVector().get(frontLine);
	}
}
