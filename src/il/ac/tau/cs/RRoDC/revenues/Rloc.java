package il.ac.tau.cs.RRoDC.revenues;

/**
 * A class representing the local revenue of a region
 * 
 * @author Alon Grinshpoon
 * 
 */
public class Rloc {

	private int localRevenue;

	public Rloc(int localRevenue) {
		setLocalRevenue(localRevenue);
	}

	public int getLocalRevenue() {
		return localRevenue;
	}

	public void setLocalRevenue(int localRevenue) {
		this.localRevenue = localRevenue;
	}
}
