package il.ac.tau.cs.RRoDC;

import il.ac.tau.cs.RRoDC.revenues.Rmarginal;

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
	private Map<Rmarginal, List<Resource>> resources = new LinkedHashMap<>();

	/**
	 * An Inner class representing a single resource
	 * @author Alon Grinshpoon
	 */
	public class Resource {
		private final int id;
		private final Type type;
		private final Region region;
		private final double marginalRevenue;
		
		public Resource(int id, Type type, Region region, double marginalRevenue) {
			this.id = id;
			this.type = type;
			this.region = region;
			this.marginalRevenue = marginalRevenue;
		}
		
		/**
		 * @return The resource's id
		 */
		public int getID() {
			return this.id;
		}
		
		/**
		 * @return The resource's marginal revenue
		 */
		public double getMarginalRevenue() {
			return this.marginalRevenue;
		}
		
		/**
		 * @return The resource's type
		 */
		public Type getType() {
			return this.type;
		}

		/**
		 * @return The resource's region
		 */
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
	 * @return The chosen resources
	 */
	public Map<Rmarginal, List<Resource>> getChosenResources() {
		return this.resources;
	}

	/**
	 * Add a new resource the this resources group
	 * @param marginalRevenue The marginal revenue of the resource type 
	 * @param frontLine The index of the resource to add 
	 */
	public void add(Rmarginal marginalRevenue, int frontLine) {
		Resource newResource = new Resource(frontLine, marginalRevenue.getType(), marginalRevenue.getRegion(), marginalRevenue.valuesVector.get(frontLine));
		if (resources.containsKey(marginalRevenue)) {
			resources.get(marginalRevenue).add(newResource);
		} else {
			List<Resource> resourceList = new ArrayList<>();
			resourceList.add(newResource);
			resources.put(marginalRevenue, resourceList);
		}
		// Increment Values
		this.amount++;
		marginalRevenue.advanceFrontLine();
	}
	
	/**
	 * Print this resources group
	 */
	public void print() {
		System.out.println(this.toString());
		for (Rmarginal marginalRevenue : resources.keySet()){
			System.out.print(marginalRevenue.toString() + ": ");
			for (Resource resouce : this.resources.get(marginalRevenue)){
				System.out.print(resouce.marginalRevenue + ", ");
			}
			System.out.println();
		}
	}
	
	@Override
	public String toString() {
		return "This resources group holds "
				+ this.amount + " resources.";
	}
}
