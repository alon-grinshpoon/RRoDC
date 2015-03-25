package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.revenues.Rloc;

/**
 * A class representing a geographical region
 * 
 * @author Alon Grinshpoon
 */
public class Region {

	private final String name;
	private Rloc localRevenue;

	/**
	 * Construct a new region
	 * 
	 * @param name
	 *            The region's name
	 */
	public Region(String name, Rloc localRevenue) {
		this.name = name;
		setLocalRevenue(localRevenue);
	}

	/**
	 * @return The region's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return The region's local revenue
	 */
	public Rloc getLocalRevenue() {
		return localRevenue;
	}

	/**
	 * Set the local revenue for the region
	 * 
	 * @param localRevenue
	 */
	public void setLocalRevenue(Rloc localRevenue) {
		this.localRevenue = localRevenue;
	}

	@Override
	public String toString() {
		return getName();
	}

}
