package il.ac.tau.cs.RRoDC;

import java.util.Iterator;

/**
 * An abstract class representing a function
 * @author Alon Grinshpon
 *
 */
public abstract class Function implements Iterable<Double>{
	
	/**
	 * The function's type
	 */
	private final Type type;
	
	/**
	 * The function's region
	 */
	private final Region region;
	
	/**
	 * The function values
	 */
	public ValuesVector valuesVector;
	
	/**
	 * Constructs a function which is a vector of values of a certain type in a certain region
	 * @param type The function's type
	 * @param region The function's region
	 */
	public Function(Type type, Region region) {
		this.type = type;
		this.region = region;
	}
		
	/**
	 * @return The vector's type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @return The vector's region
	 */
	public Region getRegion() {
		return region;
	}
	
	/**
	 * @return An iterator of the function's values
	 */
	public Iterator<Double> iterator() {
		return valuesVector.iterator();
	}
	
	/**
	 * Returns the value at the specified index in this function
	 * @param index - index of the value to return 
	 * @return the value at the specified index in this function 
	 */
	public Double get(int index) {
		return valuesVector.get(index);
	}
	
	/**
	 * Set the value at the specified index in this function
	 * @param index - index of the value to set 
	 * @return the value at the specified index in this function 
	 */
	public void set(int index, double value) {
		valuesVector.set(index, value);
	}
	
	/**
	 * Returns the number of values in this vector.
	 * @return
	 */
	public int size(){
		return valuesVector.size();
	}
	
	@Override
	public String toString() {
		return valuesVector.toString();
	}

}
