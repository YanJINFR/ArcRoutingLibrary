package oarlib.problem.impl.multivehicle;

import oarlib.core.Route;
import oarlib.graph.impl.UndirectedGraph;
import oarlib.problem.impl.MultiVehicleProblem;

import java.util.Collection;

/**
 * Problem class to represent the Capacitated Undirected Chinese Postman Problem.
 * Currently, this only supports a bound on the number of vehicles, and not a capacity constraint.
 * <p/>
 * Created by Oliver Lum on 7/25/2014.
 */
public class MultiVehicleUCPP extends MultiVehicleProblem<UndirectedGraph> {

    public MultiVehicleUCPP(UndirectedGraph graph, int numVehicles) {
        super(graph, numVehicles);
        mGraph = graph;
    }

    @Override
    public Type getProblemType() {
        return Type.UNDIRECTED_CHINESE_POSTMAN;
    }

    @Override
    public boolean isFeasible(Collection<Route> routes) {
        if (routes.size() > getmNumVehicles())
            return false;

        //TODO: Now check for real.
        return false;
    }

    @Override
    public CapacitatedObjective getObjectiveType() {
        return CapacitatedObjective.MinMax;
    }
}