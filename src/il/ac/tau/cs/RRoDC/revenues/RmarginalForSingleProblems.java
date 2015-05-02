package il.ac.tau.cs.RRoDC.revenues;

import il.ac.tau.cs.RRoDC.demands.DemandComplement;

public class RmarginalForSingleProblems extends Rmarginal {

	public RmarginalForSingleProblems(Rloc localRevenue, DemandComplement demandComplement) {
		super(localRevenue, demandComplement);
	}

	@Override
	public void computeMerginalRevenue(Rloc localRevenue, DemandComplement demandComplement) {
		for (int i = 0; i < demandComplement.size(); i++){
			// Use the definition of marginal revenue
			double marginalRevenue = localRevenue.getLocalRevenue() * demandComplement.get(i) - Cost.getCost();
			this.valuesVector.set(i, marginalRevenue);
		}
	}

}
