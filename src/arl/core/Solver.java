package arl.core;

import java.util.Collection;

/**
 * Solver abstraction. Most general contract that Solvers must fulfill.
 * @author oliverlum
 *
 */
public abstract class Solver {
	//instance should be set
	protected Problem mInstance;
	/**
	 * Default constructor; must set problem instance.
	 * @param instance - instance for which this is a solver
	 */
	public Solver(Problem instance) throws IllegalArgumentException{
		//make sure I'm a valid problem instance
		if(!(instance.getClass() == getProblemType()))
		{
			throw new IllegalArgumentException();
		}
		mInstance = instance;
	}
	/**
	 * Attempts to solve the instance assigned to this problem.  
	 * @return null if problem instance is not assigned.
	 */
	public Collection<Route> trySolve(){
		if (mInstance == null)
			return null;
		return solve();
	}
	/**
	 * Actually solves the instance, (first checking for feasibility), returning a Collection of routes.
	 * @return The set of routes the solver has concluded is best.
	 */
	protected abstract Collection<Route> solve();
	/**
	 * Specifies what type of problem this is a solver for.
	 * @return
	 */
	public abstract Class<Problem> getProblemType();

}
