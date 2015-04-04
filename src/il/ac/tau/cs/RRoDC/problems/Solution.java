package il.ac.tau.cs.RRoDC.problems;

import il.ac.tau.cs.RRoDC.Resources;

/**
 * A class representing a solution to a problem.
 * @author Alon Grinshpoon
 */
public class Solution {
	
	// All solutions are the number of resources chosen and the total revenue
	private int numberOfResources;
	private double revenue;
	// Also keep the resources themselves for printing
	private Resources resources;
	
	/**
	 * Constructs a new solution
	 * @param resources The resources chosen with holds the number of resources chosen
	 * @param revenue The total revenue of the solution
	 */
	public Solution(Resources resources, double revenue) {
		this.numberOfResources = resources.getAmount();
		this.revenue = revenue;
		this.resources = resources;
	}
	
	/**
	 * @return The number of resources chosen
	 */
	public int getNumberOfResources() {
		return numberOfResources;
	}
	
	/**
	 * Set the number of resources chosen
	 * @param numberOfResources
	 */
	public void setNumberOfResources(int numberOfResources) {
		this.numberOfResources = numberOfResources;
	}
	
	/**
	 * @return The total revenue of the solution
	 */
	public double getRevenue() {
		return revenue;
	}
	
	/**
	 * Set the total revenue of the solution
	 * @param revenue
	 */
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	
	/**
	 * Outputs the solution to the user's screen
	 */
	public void print(){
		System.out.println("========= Problem Solved! =========");
		System.out.println("The number of resources will be " + this.numberOfResources + ".");
		System.out.println("The total revenue will be " + this.revenue + ".");
		System.out.println("===================================");
	}
	
	
	/**
	 * Outputs the solution to the user's screen with details on the resources
	 */
	public void printAll(){
		// Print
		print();
		// Print resources state
		resources.print();
		System.out.println("===================================");
	}

}
