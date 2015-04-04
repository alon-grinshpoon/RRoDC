package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.Function;
import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class Rmarginal extends Function {

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
		for (int i = 0; i < demandComplement.size(); i++){
			// Use the definition of marginal revenue
			double marginalRevenue = localRevenue.getLocalRevenue() * demandComplement.get(i) - Cost.getCost();
			this.valuesVector.set(i, marginalRevenue);
		}
	}
	
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
}
