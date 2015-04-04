package il.ac.tau.cs.RRoDC.problems;

/**
 * A interface representing a problem that can be solved.
 * @author Alon Grinshpoon
 */
public interface Problem {

	/**
	 * Solves the defined problem.
	 * @return The solution to the problem
	 */
	public Solution solve();
}
