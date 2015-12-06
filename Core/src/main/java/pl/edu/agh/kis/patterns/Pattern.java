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

import edu.uci.ics.jung.graph.Graph;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * Represents BPMN pattern
 */
public abstract class Pattern {

    public abstract StructNode findPattern(Graph g, Node start) throws BadPatternException, UnexpectedException;
    private static final List<StructNodeType> REVERSE_PATTERNS;
    
    static{
        REVERSE_PATTERNS = new ArrayList<>();
        REVERSE_PATTERNS.add(StructNodeType.SIMPLE_MERGE);
        REVERSE_PATTERNS.add(StructNodeType.SYNCHRONIZATION);
    }
    
    public static Graph replaceGraphSegment(Graph<Node, Edge> g, StructNode patternTree) {
        g.addVertex(patternTree);

        List<Edge> edgeToRemove = null;
        if (!g.getInEdges(patternTree).isEmpty()) {
            edgeToRemove = new ArrayList<Edge>(g.getInEdges(patternTree.getLeft()));
            for (Edge e : edgeToRemove) {
                if (isReversePattern(patternTree)) {
                    g.removeEdge(e);
                } else {
                    g.removeEdge(e);
                    e.setEnd(patternTree);
                    g.addEdge(e, e.getStart(), patternTree);
                }
            }
        }
        AtomNode last = findLastNode(patternTree);
        if (!g.getOutEdges(last).isEmpty()) {
            edgeToRemove = new ArrayList<Edge>(g.getOutEdges(last));
            for (Edge e : edgeToRemove) {
                if (isReversePattern(patternTree)) {
                    g.removeEdge(e);
                } else {
                    g.removeEdge(e);
                    e.setStart(patternTree);
                    g.addEdge(e, patternTree, e.getEnd());
                }
            }
        }
        return g;
    }

    private static AtomNode findLastNode(StructNode patternTree) {
        AtomNode last = null;
        while (patternTree.getRight() != null) {
            if (patternTree.getRight() instanceof StructNode) {
                patternTree = (StructNode) patternTree.getRight();
            } else {
                last = (AtomNode) patternTree.getRight();
                break;
            }
        }
        return last;
    }

    public AtomNode findEndPatternNode(Graph<Node, Edge> g, Node start, Collection<AtomNodeType> types, int nestingLevel, Collection<Node> nodes) {
        AtomNode atom = null;
        nodes.add(start);
        if (start.getType() == NodeType.ATOM) {
            atom = (AtomNode) start;
            if (g.getOutEdges(atom).size() == 2) {
                nestingLevel++;
            }
            if (g.getInEdges(atom).size() == 2) {
                if (nestingLevel == 0 && types.contains(atom.getAtomType())) {
                    nodes.remove(start);
                    return atom;
                } else {
                    nestingLevel--;
                }
            }
        }
        AtomNode endAtom = null;
        for (Node n : g.getSuccessors(start)) {
            endAtom = findEndPatternNode(g, n, types, nestingLevel, nodes);
        }

        return endAtom;
    }

    public AtomNode getEndNode(Graph<Node, Edge> g, Node start, Collection<Node> nodes) {
        AtomNode atom = null;
        nodes.add(start);
        if (start.getType() == NodeType.ATOM) {
            atom = (AtomNode) start;
            if (g.getOutEdges(atom).size() == 0) {
                return atom;
            }
        }
        AtomNode endAtom = null;
        for (Node n : g.getSuccessors(start)) {
            endAtom = getEndNode(g, n, nodes);
        }

        return endAtom;
    }

    public Node getFirstNode(Graph<Node, Edge> g, Node start, Collection<Node> nodes) {
        Node atom = null;
        nodes.add(start);
        atom = start;
        if (g.getInEdges(atom).size() == 0) {
            return atom;
        }
        Node endAtom = null;
        for (Node n : g.getPredecessors(start)) {
            endAtom = getFirstNode(g, n, nodes);
        }

        return endAtom;
    }

    protected static boolean isReversePattern(StructNode tree) {
        boolean isReverse = false;
        if (REVERSE_PATTERNS.contains(tree.getStructType())) {
            isReverse = true;
        }
        return isReverse;
    }
}
