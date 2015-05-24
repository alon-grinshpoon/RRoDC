package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class RmarginalForMultiProblemsRemove extends Rmarginal {

	private DemandComplement globalDemandComplement;
	
	public RmarginalForMultiProblemsRemove(Rloc localRevenue, DemandComplement localDemandComplement, DemandComplement globalDemandComplement) {
		super(localRevenue, localDemandComplement, true);
		this.globalDemandComplement = globalDemandComplement;
		computeMarginalRevenue(localRevenue, localDemandComplement);
	}

	@Override
	public void computeMarginalRevenue(Rloc localRevenue, DemandComplement demandComplement) {
		for (int i = 0; i < demandComplement.size(); i++){
			// Use the definition of marginal revenue
			double marginalRevenue = -(localRevenue.getLocalRevenue() * demandComplement.get(i) - Cost.getCost() + Rglo.getGlobalRevenew() * this.globalDemandComplement.get(i));
			this.valuesVector.set(i, marginalRevenue);
		}
	}

}
