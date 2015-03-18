package il.ac.tau.cs.RRoDC;

/**
 * A class representing a group of resources
 * @author Alon Grinshpoon
 */

public class Resources {

	private int amount;
	private final Type type;
	private final Region region;

	public Resources(Type type, Region region) {
		this.type = type;
		this.region = region;
	}

	/**s
	 * @param The amount of resources to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * @return The amount of resources
	 */
	public int getAmount() {
		return amount;
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
}
