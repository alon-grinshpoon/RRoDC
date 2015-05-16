package il.ac.tau.cs.RRoDC;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing a vector of values as doubles 
 * 
 * @author Alon Grinshpoon
 * 
 */
public class ValuesVector implements Iterable<Double>, Cloneable {

	private List<Double> values;

	/**
	 * Constructs a values vector out of a list of doubles
	 * @param values a list of doubles
	 */
	public ValuesVector(List<Double> values) {
		this.values = values;
	}

	/**
	 * Constructs a values vector out of given doubles
	 * @param values a list of doubles
	 */
	public ValuesVector(Double... values) {
		this(Arrays.asList(values));
	}

	/**
	 * Returns the element at the specified position in this vector
	 * @param index - index of the element to return 
	 * @return the element at the specified position in this vector, 0.0 if the index is out of bound
	 */
	public double get(int index) {
		// Check for out of bound values
		if (index >= this.size()){
			Utils.TRACE_println("[WARN] Accessed an out of bound value in a valuesVector. 0.0 is returned. ");
			return 0.0;
		} else {			
			return values.get(index);
		}
	}
	
	/**
	 * Set the element at the specified position in this vector
	 * @param index - index of the element to set 
	 * @return the element at the specified position in this vector 
	 */
	public double set(int index, double value) {
		return values.set(index, value);
	}
	
	/**
	 * Returns the number of values in this vector.
	 * @return
	 */
	public int size(){
		return values.size();
	}
	
	@Override
	public String toString() {
		return Arrays.toString(values.toArray());
	}

	@Override
	public Iterator<Double> iterator() {
		return this.values.iterator();
	}
	
	@Override
	public ValuesVector clone() {
		List<Double> values = new ArrayList<>(this.values);
		ValuesVector returnedValuesVector = new ValuesVector(values);
		return returnedValuesVector;
	}

}
