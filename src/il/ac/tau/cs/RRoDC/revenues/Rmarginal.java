package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.Function;
import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public abstract class Rmarginal extends Function {

	private int frontLine = 0;

	/**
	 * Construct a marginal revenue vector
	 * @param localRevenue A local revenue of this region
	 * @param demandComplement A complement CDF if this type and region
	 */
	public Rmarginal(Rloc localRevenue, DemandComplement demandComplement) {
		super(demandComplement.getType(), demandComplement.getRegion());
		// Clone original values
		this.valuesVector = demandComplement.valuesVector.clone();
		// Compute marginal revenue for all values
		computeMerginalRevenue(localRevenue, demandComplement);
	}
	
	/**
	 * Compute and set the right marginal revenue for the problem. Need to be implemented for every problem individually.
	 * This requires to extend this class to specific marginal revenue classes.
	 * @param localRevenue
	 * @param demandComplement
	 */
	public abstract void computeMerginalRevenue(Rloc localRevenue, DemandComplement demandComplement);
	
	/**
	 * Set the index front line in the values vector
	 * 
	 * @param frontLine
	 */
	public void setFrontLine(int frontLine) {
		this.frontLine = frontLine;
	}

	/**
	 * @return The index of the front line in the values vector
	 */
	public int getFrontLine() {
		return this.frontLine;
	}

	/**
	 * @return The value that is at the front line of the marginal revenue vector
	 */
	public double getFrontlineValue() {
		return this.valuesVector.get(frontLine);
	}

	/**
	 * Advance the marginal revenue vector's front line by 1
	 */
	public void advanceFrontLine() {
		this.frontLine++;
	}

	/**
	 * Regress the marginal revenue vector's front line by 1
	 */
	public void regressFrontLine() {
		this.frontLine--;
	}

	/**
	 * @return True if the front line reached the end of the vector.
	 * Meaning all values of this marginal revenue vector were chosen
	 */
	public boolean isExhausted() {
		return this.frontLine >= this.valuesVector.size();
	}
	
	/**
	 * @return True if the front line is at the start of the vector.
	 * Meaning all values of this marginal revenue vector were yet to be chosen
	 */
	public boolean isUnutilized() {
		return this.frontLine == 0;
	}
	
	@Override
	public String toString() {
		return super.toString() + " (frontline: " + this.frontLine + ")";
	}
}
