package oarlib.problem.impl;

import java.util.Collection;

import oarlib.core.Graph;
import oarlib.core.Link;
import oarlib.core.Problem;
import oarlib.core.Route;
import oarlib.core.Vertex;

/**
 * The Directed Rural Postman Problem.
 * @author oliverlum
 *
 */
public class DirectedRPP extends Problem {

	public DirectedRPP(Graph<Vertex, Link<Vertex>> g, ObjectiveFunction o) {
		super(g, o);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isFeasible(Collection<Route> routes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Type getType() {
		return Problem.Type.DIRECTED_RURAL_POSTMAN;
	}

}