package il.ac.tau.cs.RRoDC.revenues;

/**
 * A class representing the single cost of a resource
 * 
 * @author Alon Grinshpoon
 * 
 */
public class Cost {
	
	public final static double DEFAULT_COST_PER_SINGLE_RESOURCE = 1;
	
	private static double cost = DEFAULT_COST_PER_SINGLE_RESOURCE;
	
	/**
	 * @return The cost of a single resource
	 */
	public static double getCost(){
		return cost;
	}

	/**
	 * Set the cost of a resource
	 * @param cost
	 */
	public static void setCost(double cost) {
		Cost.cost = cost;
	}
}
