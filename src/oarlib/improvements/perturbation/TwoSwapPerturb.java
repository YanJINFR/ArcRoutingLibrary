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
package oarlib.improvements.perturbation;

import gnu.trove.TIntObjectHashMap;
import oarlib.core.Graph;
import oarlib.core.Problem;
import oarlib.core.Route;
import oarlib.graph.impl.WindyGraph;
import oarlib.improvements.ImprovementStrategy;
import oarlib.improvements.IntraRouteImprovementProcedure;
import oarlib.improvements.util.CompactMove;
import oarlib.improvements.util.Mover;
import oarlib.link.impl.WindyEdge;
import oarlib.problem.impl.ProblemAttributes;
import oarlib.vertex.impl.WindyVertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by oliverlum on 12/5/14.
 */
public class TwoSwapPerturb extends IntraRouteImprovementProcedure<WindyVertex, WindyEdge, WindyGraph> {

    public TwoSwapPerturb(Problem<WindyVertex, WindyEdge, WindyGraph> problem) {
        super(problem);
    }

    public TwoSwapPerturb(Problem<WindyVertex, WindyEdge, WindyGraph> problem, ImprovementStrategy.Type strat, Collection<Route<WindyVertex, WindyEdge>> initialSol) {
        super(problem, strat, initialSol);
    }

    @Override
    public ProblemAttributes getProblemAttributes() {
        return new ProblemAttributes(Graph.Type.WINDY, null, null, ProblemAttributes.NumDepots.SINGLE_DEPOT, null);
    }

    @Override
    public Route<WindyVertex, WindyEdge> improveRoute(Route<WindyVertex, WindyEdge> r) {

        Route<WindyVertex, WindyEdge> ans = null;

        Random rng = new Random();
        List<WindyEdge> rPath = r.getPath();
        int routeLength = r.getCompactRepresentation().size();
        int index1 = rng.nextInt(routeLength);
        int index2 = rng.nextInt(routeLength);

        //in case they're the same
        if (index1 == index2) {
            index2 = (index2 + 1) % routeLength;
        }

        CompactMove<WindyVertex, WindyEdge> temp, temp2;
        ArrayList<CompactMove<WindyVertex, WindyEdge>> moveList = new ArrayList<CompactMove<WindyVertex, WindyEdge>>();
        //swap them
        temp = new CompactMove<WindyVertex, WindyEdge>(r, r, index1, index2);
        if (index1 < index2)
            temp2 = new CompactMove<WindyVertex, WindyEdge>(r, r, index2 - 1, index1);
        else
            temp2 = new CompactMove<WindyVertex, WindyEdge>(r, r, index2 + 1, index1);//or + 1
        moveList.add(temp);
        moveList.add(temp2);

        Mover<WindyVertex, WindyEdge, WindyGraph> mover = new Mover<WindyVertex, WindyEdge, WindyGraph>(getGraph());
        ArrayList<Route<WindyVertex, WindyEdge>> tempList = new ArrayList<Route<WindyVertex, WindyEdge>>();
        tempList.add(r);
        mover.evalComplexMove(moveList, tempList);
        TIntObjectHashMap<Route<WindyVertex, WindyEdge>> newRoutes = mover.makeComplexMove(moveList);

        for (int i : newRoutes.keys())
            return newRoutes.get(i);

        return null;
    }
}
