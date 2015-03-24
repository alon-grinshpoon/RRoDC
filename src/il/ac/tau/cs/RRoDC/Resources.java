package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.demands.DemandPDF;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a group of resources
 * @author Alon Grinshpoon
 */
public class Resources {

	private int amount;
	private Map<DemandPDF, List<Resource>> resources = new LinkedHashMap<>();

	/**
	 * An Inner class representing a single resource
	 * @author Alon Grinshpoon
	 */
	private class Resource {
		private final int id;
		private final double probability;
		private final Type type;
		private final Region region;
		
		public Resource(int id, double probability, Type type, Region region) {
			this.id = id;
			this.probability = probability;
			this.type = type;
			this.region = region;
		}
		
		/**
		 * @return The resource's id
		 */
		@SuppressWarnings("unused")
		public int getID() {
			return this.id;
		}
		
		/**
		 * @return The resource's probability
		 */
		@SuppressWarnings("unused")
		public double getProbability() {
			return this.probability;
		}
		
		/**
		 * @return The resource's type
		 */
		@SuppressWarnings("unused")
		public Type getType() {
			return this.type;
		}

		/**
		 * @return The resource's region
		 */
		@SuppressWarnings("unused")
		public Region getRegion() {
			return this.region;
		}
	}
	
	/**
	 * @param The amount of resources to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return The amount of resources
	 */
	public int getAmount() {
		return this.amount;
	}

	/**
	 * Add a new resource the this resources group
	 * @param PDF The demand PDF representing the resource type 
	 * @param frontLine The index of the resource to add 
	 */
	public void add(DemandPDF PDF, int frontLine) {
		Resource newResource = new Resource(frontLine, PDF.getProbabilityVector().get(frontLine), PDF.getType(), PDF.getRegion());
		if (resources.containsKey(PDF)) {
			resources.get(PDF).add(newResource);
		} else {
			List<Resource> resourceList = new ArrayList<>();
			resourceList.add(newResource);
			resources.put(PDF, resourceList);
		}
		// Increment Values
		this.amount++;
		PDF.advanceFrontLine();
	}
	
	/**
	 * Print this resources group
	 */
	public void print() {
		for (DemandPDF dPDF : resources.keySet()){
			System.out.print(dPDF.toString() + ": ");
			for (Resource resouce : this.resources.get(dPDF)){
				System.out.print(resouce.probability + ", ");
			}
			System.out.println();
		}
	}
	
	@Override
	public String toString() {
		return "This resource group holds "
				+ this.amount + " resources "+ " (" + super.toString() + ")";
	}
}
