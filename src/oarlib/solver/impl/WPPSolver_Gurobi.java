/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013-2015 Oliver Lum
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package oarlib.solver.impl;

import gnu.trove.TIntObjectHashMap;
import gurobi.*;
import oarlib.core.Graph;
import oarlib.core.Problem;
import oarlib.core.SingleVehicleSolver;
import oarlib.core.Solver;
import oarlib.graph.impl.WindyGraph;
import oarlib.graph.util.CommonAlgorithms;
import oarlib.link.impl.WindyEdge;
import oarlib.problem.impl.ProblemAttributes;
import oarlib.route.impl.Tour;
import oarlib.vertex.impl.WindyVertex;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author oliverlum
 *         <p/>
 *         An IP formulation of the WPP given by Win.  The variables are as follows:
 *         <p/>
 *         c_ij: the cost of going from vertex i to vertex j
 *         x_ij: the number of times an edge is traversed from vertex i to vertex j is included in the augmentation
 */
public class WPPSolver_Gurobi extends SingleVehicleSolver<WindyVertex, WindyEdge, WindyGraph> {

    public WPPSolver_Gurobi(Problem<WindyVertex, WindyEdge, WindyGraph> instance) throws IllegalArgumentException {
        super(instance);
    }

    @Override
    protected boolean checkGraphRequirements() {
        // make sure the graph is connected
        if (mInstance.getGraph() == null)
            return false;
        else {
            WindyGraph mGraph = mInstance.getGraph();
            if (!CommonAlgorithms.isConnected(mGraph))
                return false;
        }
        return true;
    }

    @Override
    protected Problem<WindyVertex, WindyEdge, WindyGraph> getInstance() {
        return mInstance;
    }

    @Override
    protected Collection<Tour> solve() {
        try {
            //copy
            WindyGraph copy = mInstance.getGraph().getDeepCopy();
            TIntObjectHashMap<WindyEdge> indexedEdges = copy.getInternalEdgeMap();
            int n = copy.getVertices().size();
            int m = copy.getEdges().size();

            //Gurobi stuff
            GRBEnv env = new GRBEnv("miplog.log");
            env.set(GRB.DoubleParam.MIPGap, 0); //insist on optimality
            env.set(GRB.IntParam.OutputFlag, 0);
            GRBModel model;

            //Now set up the model in Gurobi and solve it, and see if you get the right answer
            model = new GRBModel(env);

            //create and add vars
            int i, j;
            GRBVar[][] vars = new GRBVar[n + 1][n + 1];
            WindyEdge tempEdge;
            GRBLinExpr[] edgeConstr = new GRBLinExpr[m];
            GRBLinExpr[] vertexConstr = new GRBLinExpr[n];
            for (int k = 1; k <= m; k++) {
                tempEdge = indexedEdges.get(k);
                i = tempEdge.getEndpoints().getFirst().getId();
                j = tempEdge.getEndpoints().getSecond().getId();
                vars[i][j] = model.addVar(0.0, Double.MAX_VALUE, tempEdge.getCost(), GRB.INTEGER, "x_" + i + j);
                vars[j][i] = model.addVar(0.0, Double.MAX_VALUE, tempEdge.getReverseCost(), GRB.INTEGER, "x_" + j + i);

                //add the edge constraint here
                edgeConstr[k - 1] = new GRBLinExpr();
                edgeConstr[k - 1].addTerm(1, vars[i][j]);
                edgeConstr[k - 1].addTerm(1, vars[j][i]);
            }

            for (int k = 1; k <= n; k++) {
                vertexConstr[k - 1] = new GRBLinExpr();
                for (int l = 1; l <= n; l++) {
                    if (vars[k][l] != null) {
                        vertexConstr[k - 1].addTerm(1, vars[k][l]);
                        vertexConstr[k - 1].addTerm(-1, vars[l][k]);
                    }

                }
            }

            //update
            model.update();

            //finalize the constraints
            for (GRBLinExpr ec : edgeConstr)
                model.addConstr(ec, GRB.GREATER_EQUAL, 1, "edge_constraint");
            for (GRBLinExpr vc : vertexConstr)
                model.addConstr(vc, GRB.EQUAL, 0, "vertex_constraint");


            //optimize
            model.optimize();

            //now create the route


            //return the answer
            //ArrayList<Integer> ans = CommonAlgorithms.tryHierholzer(copy);
            //Tour eulerTour = new Tour();
            //for (int i=0;i<ans.size();i++) {
            //eulerTour.appendEdge(indexedEdges.get(ans.get(i)));
            //}

            //print the obj value.
            System.out.println(model.get(GRB.DoubleAttr.ObjVal));

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ProblemAttributes getProblemAttributes() {
        return new ProblemAttributes(Graph.Type.WINDY, ProblemAttributes.Type.CHINESE_POSTMAN, ProblemAttributes.NumVehicles.SINGLE_VEHICLE, ProblemAttributes.NumDepots.SINGLE_DEPOT, null);
    }

    @Override
    public String printCurrentSol() throws IllegalStateException {
        return "This solver does not support printing.";
    }

    @Override
    public String getSolverName() {
        return "An Exact Integer Programming Solver for the Windy Postman Problem";
    }

    @Override
    public Solver<WindyVertex, WindyEdge, WindyGraph> instantiate(Problem<WindyVertex, WindyEdge, WindyGraph> p) {
        return new WPPSolver_Gurobi(p);
    }

    @Override
    public HashMap<String, Double> getProblemParameters() {
        return new HashMap<String, Double>();
    }

}
