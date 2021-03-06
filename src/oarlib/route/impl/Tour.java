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
package oarlib.route.impl;

import oarlib.core.Link;
import oarlib.core.Route;
import oarlib.core.Vertex;

import java.util.List;

/**
 * A tour is a route that must begin and end at the same node.
 *
 * @author Oliver
 */
public class Tour<V extends Vertex, E extends Link<V>> extends Route<V, E> {

    public Tour() {
        super();
    }

    @Override
    public Tour<V, E> getDeepCopy() {
        Tour<V,E> ans = new Tour<V, E>();
        ans.setVars(this);
        return ans;
    }

    public static boolean isTour(Route r) {
        List<? extends Link<? extends Vertex>> rPath = r.getPath();
        return rPath.get(0).getEndpoints().getFirst().getId() == rPath.get(rPath.size() - 1).getEndpoints().getSecond().getId();

    }

}
