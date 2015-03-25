package il.ac.tau.cs.RRoDC.revenues;

/**
 * A class representing the local revenue of a region
 * 
 * @author Alon Grinshpoon
 * 
 */
public class Rloc {

	private double localRevenue;

	/**
	 * Construct a local revenue
	 * @param localRevenue A double that represents the value of the local revenue
	 */
	public Rloc(double localRevenue) {
		setLocalRevenue(localRevenue);
	}

	/**
	 * @return The local revenue of a resource
	 */
	public double getLocalRevenue() {
		return localRevenue;
	}

	/**
	 * Set the local revenue of a resource
	 * @param localRevenue The local revenue of a resource
	 */
	public void setLocalRevenue(double localRevenue) {
		this.localRevenue = localRevenue;
	}
}
