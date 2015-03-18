package il.ac.tau.cs.RRoDC.demands;

import java.util.Arrays;
import java.util.List;

import il.ac.tau.cs.RRoDC.Region;
import il.ac.tau.cs.RRoDC.Type;

/**
 * A class representing a demand's probability density function
 * @author Alon Grinshpoon
 */

public class DemandPDF {

	private final Type type;
	private final Region region;
	private List<Double> probabilties;

	public DemandPDF(Type type, Region region, List<Double> probabilties) {
		this.type = type;
		this.region = region;
		this.probabilties = probabilties;
	}
	
	public DemandPDF(Type type, Region region, Double... probabilties){
		this(type, region, Arrays.asList(probabilties));
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
	
	/**
	 * @return The resources' probabilty vector
	 */
	public List<Double> getProbabilties() {
		return probabilties;
	}
}
