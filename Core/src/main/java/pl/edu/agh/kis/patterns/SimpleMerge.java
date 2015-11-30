/*
 * Copyright (c) 2015, pl.edu.agh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package pl.edu.agh.kis.patterns;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import pl.edu.agh.kis.core.PatternExtractor;
import pl.edu.agh.kis.core.data.AtomNode;
import pl.edu.agh.kis.core.data.AtomNodeType;
import pl.edu.agh.kis.core.data.Edge;
import pl.edu.agh.kis.core.data.Node;
import pl.edu.agh.kis.core.data.NodeType;
import pl.edu.agh.kis.core.data.StructNode;
import pl.edu.agh.kis.core.data.StructNodeType;
import pl.edu.agh.kis.exceptions.BadPatternException;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public class SimpleMerge extends Pattern{
    
    private static boolean inSimpleMerge = false;
    
    @Override
    public StructNode findPattern(Graph g, Node start) throws BadPatternException, UnexpectedException {
        StructNode snode = null;
        if (g.getInEdges(start).size() == 2 && start.getType() == NodeType.ATOM && (((AtomNode) start).getAtomType() == AtomNodeType.EXCLUSIVE_GATEWAY ||
                ((AtomNode) start).getAtomType() == AtomNodeType.TASK) && !inSimpleMerge){
            inSimpleMerge = true;
            Iterator<Edge> iter = g.getInEdges(start).iterator();
            List<Node> pathNodes1 = new ArrayList<>();
            List<Node> pathNodes2 = new ArrayList<>();
            Node endPath1 = iter.next().getStart();
            Node endPath2 = iter.next().getStart();
            Node startPath1 = getFirstNode(g, endPath1, pathNodes1);
            Node startPath2 = getFirstNode(g, endPath2, pathNodes2);
            if(startPath1 == null || startPath2 == null){
                return null;
            }
            if (startPath1.equals(startPath2)) {
               throw new UnexpectedException("Paths can not be merged");
            }
            Graph subgraph1 = FilterUtils.createInducedSubgraph(pathNodes1, g);
            Graph subgraph2 = FilterUtils.createInducedSubgraph(pathNodes2, g);
            PatternExtractor pe = new PatternExtractor();
            snode = new StructNode(pe.extractPatterns(subgraph2, startPath2)  ,pe.extractPatterns(g, start),  pe.extractPatterns(subgraph1, startPath1), null, StructNodeType.SIMPLE_MERGE);
            inSimpleMerge = false;
        }
        return snode;
    }
}
