package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class RmarginalForMultiProblemsAdd extends Rmarginal {

	private DemandComplement globalDemandComplement;
	
	public RmarginalForMultiProblemsAdd(Rloc localRevenue, DemandComplement localDemandComplement, DemandComplement globalDemandComplement) {
		super(localRevenue, localDemandComplement, true);
		this.globalDemandComplement = globalDemandComplement;
		computeMarginalRevenue(localRevenue, localDemandComplement);
	}

	@Override
	public void computeMarginalRevenue(Rloc localRevenue, DemandComplement demandComplement) {
		for (int i = 0; i < demandComplement.size(); i++){
			// Use the definition of marginal revenue
			// TODO Check if % needed
			double marginalRevenue = localRevenue.getLocalRevenue() * demandComplement.get(i) - Cost.getCost() + Rglo.getGlobalRevenew() * this.globalDemandComplement.get((i + 1) % demandComplement.size());
			this.valuesVector.set(i, marginalRevenue);
		}
	}

}
