package il.ac.tau.cs.RRoDC.revenues;

/**
 * A class representing the global revenue of all regions
 * 
 * @author Alon Grinshpoon
 * 
 */
public class Rglo {

	public final static double DEFAULT_GLOBAL_REVENUE = 1;
	private static double globalRevenue = DEFAULT_GLOBAL_REVENUE;
	
	/**
	 * @return The global revenue of a single resource
	 */
	public static final double getGlobalRevenew(){
		return globalRevenue;
	}

	/**
	 * Set the global revenue of a resource
	 * @param globalRevenue
	 */
	public static void setGlobalRevenue(double globalRevenue) {
		Rglo.globalRevenue = globalRevenue;
	}
	
}
