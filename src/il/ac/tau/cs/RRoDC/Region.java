package il.ac.tau.cs.RRoDC;

/**
 * A class representing a geographical region
 * @author Alon Grinshpoon
 */
public class Region {

	private final String name;
	
	/**
	 * Construct a new region
	 * @param name The region's name
	 */
	public Region(String name) {
		this.name = name;
	}
	
	/**
	 * @return The region's name
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + super.toString() + ")";
	}
}
