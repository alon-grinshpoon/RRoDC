package il.ac.tau.cs.RRoDC;

/**
 * A class representing a type of distribution
 * @author Alon Grinshpoon
 */
public class Type {

	private final String name;
	
	/**
	 * Construct a new type
	 * @param name The type's name
	 */
	public Type(String name) {
		this.name = name;
	}
	
	/**
	 * @return The type's name
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name + "(" + super.toString() + ")";
	}
}
