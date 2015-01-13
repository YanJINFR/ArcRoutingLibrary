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
package oarlib.problem.impl.cpp;

import oarlib.core.Problem;
import oarlib.core.Route;
import oarlib.graph.impl.MixedGraph;
import oarlib.link.impl.MixedEdge;
import oarlib.objfunc.SumObjectiveFunction;
import oarlib.problem.impl.ChinesePostmanProblem;
import oarlib.vertex.impl.MixedVertex;

import java.util.Collection;

public class MixedCPP extends ChinesePostmanProblem<MixedVertex, MixedEdge, MixedGraph> {

    public MixedCPP(MixedGraph g) {
        this(g, "");
    }

    public MixedCPP(MixedGraph g, String name) {
        super(g, name, new SumObjectiveFunction());
        mGraph = g;
    }

    @Override
    public boolean isFeasible(Collection<Route> routes) {
        return false;
    }

    @Override
    public Type getProblemType() {
        return Problem.Type.MIXED_CHINESE_POSTMAN;
    }

}
