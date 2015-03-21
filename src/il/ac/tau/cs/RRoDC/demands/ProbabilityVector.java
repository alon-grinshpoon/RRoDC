package il.ac.tau.cs.RRoDC.demands;

import il.ac.tau.cs.RRoDC.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * A class representing a vector of probabilities as doubles 
 * 
 * @author Alon Grinshpoon
 * 
 */
public class ProbabilityVector {

	private List<Double> probabilties;

	/**
	 * Constructs a probability vector out of a list of doubles
	 * @param probabilties a list of doubles
	 */
	public ProbabilityVector(List<Double> probabilties) {
		this.probabilties = probabilties;
		// Verify values
		for (double probabilty : probabilties){
			if (Math.abs(probabilty) > 1){
				Utils.ErrorAndExit("Given an invalid probability value. All probabilities must be between -1 and 1.");
			}
		}
	}

	/**
	 * Constructs a probability vector out of given doubles
	 * @param probabilties a list of doubles
	 */
	public ProbabilityVector(Double... probabilties) {
		this(Arrays.asList(probabilties));
	}

	/**
	 * Returns the element at the specified position in this vector
	 * @param index - index of the element to return 
	 * @return the element at the specified position in this vector 
	 */
	public double get(int index) {
		return probabilties.get(index);
	}
}
