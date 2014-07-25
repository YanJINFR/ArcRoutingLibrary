package oarlib.graph.transform.impl;

import java.util.HashMap;
import java.util.HashSet;

import oarlib.core.Arc;
import oarlib.graph.impl.DirectedGraph;
import oarlib.graph.transform.GraphTransformer;
import oarlib.vertex.impl.DirectedVertex;

/**
 * Graph transformer that takes an edge-weighted graph, and produces a vertex-weighted graph such that a 
 * a k-way weighted vertex partition of the transformed graph corresponds to an equal k-way edge partition 
 * in the original graph.  We anticipate using this to turn our single-vehicle solvers into capacitated solvers.
 * 
 * @author oliverlum
 *
 */
public class DirectedKWayPartitionTransform implements GraphTransformer<DirectedGraph, DirectedGraph>{

	private DirectedGraph mGraph;
	DirectedKWayPartitionTransform (DirectedGraph input)
	{
		mGraph = input;
	}
	@Override
	public DirectedGraph transformGraph() {

		try
		{
			int n = mGraph.getVertices().size();
			//ans
			DirectedGraph ans = new DirectedGraph(n);

			//setup
			DirectedVertex temp;
			HashMap<Integer, DirectedVertex> ansVertices = ans.getInternalVertexMap();
			HashSet<Arc> arcs = mGraph.getEdges();
			int id;

			for(Arc a : arcs)
			{
				id = a.getHead().getId();

				temp = ansVertices.get(id);
				temp.setCost(temp.getCost() + a.getCost());

				ans.addEdge(a.getTail().getId(), a.getHead().getId(), 1);
			}
			return ans;
		} catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void setGraph(DirectedGraph input) {
		mGraph = input;
	}
}
