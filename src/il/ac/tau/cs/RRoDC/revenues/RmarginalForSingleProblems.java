package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class RmarginalForSingleProblems extends Rmarginal {

	public RmarginalForSingleProblems(Rloc localRevenue, DemandComplement demandComplement) {
		super(localRevenue, demandComplement);
	}

	@Override
	public void computeMarginalRevenue(Rloc localRevenue, DemandComplement demandComplement) {
		for (int l = 0; l < demandComplement.size(); l++){
			// Use the definition of marginal revenue
			double marginalRevenue = localRevenue.getLocalRevenue() * demandComplement.get(l) - Cost.getCost();
			this.valuesVector.set(l, marginalRevenue);
		}
	}

}
