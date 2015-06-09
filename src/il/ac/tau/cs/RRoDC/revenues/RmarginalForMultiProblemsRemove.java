package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class RmarginalForMultiProblemsRemove extends Rmarginal {

	private DemandComplement globalDemandComplement;
	private int globalNumberOfResources = 0;
	
	public RmarginalForMultiProblemsRemove(Rloc localRevenue, DemandComplement localDemandComplement, DemandComplement globalDemandComplement) {
		super(localRevenue, localDemandComplement, true);
		this.globalDemandComplement = globalDemandComplement;
		computeMarginalRevenue(localRevenue, localDemandComplement);
	}

	@Override
	public void computeMarginalRevenue(Rloc localRevenue, DemandComplement demandComplement) {
		for (int l = 0; l < demandComplement.size(); l++){
			// Use the definition of marginal revenue
			double marginalRevenue = -(localRevenue.getLocalRevenue() * demandComplement.get(l) - Cost.getCost() + Rglo.getGlobalRevenew() * this.globalDemandComplement.get(this.globalNumberOfResources));
			this.valuesVector.set(l, marginalRevenue);
		}
	}

	/**
	 * Recompute and set the right marginal revenue for the problem depending on the global number of resources chosen.
	 * @param localRevenue
	 * @param demandComplement
	 * @param globalNumberOfResources
	 */
	public void recomputeMarginalRevenue(Rloc localRevenue, DemandComplement demandComplement, int globalNumberOfResources) {
		this.globalNumberOfResources = globalNumberOfResources;
		computeMarginalRevenue(localRevenue, demandComplement);
	}

}
