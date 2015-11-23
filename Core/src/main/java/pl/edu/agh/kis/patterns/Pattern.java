/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.patterns;

import edu.uci.ics.jung.graph.Graph;
import java.rmi.UnexpectedException;
import java.util.Collection;
import pl.edu.agh.kis.core.data.AtomNode;
import pl.edu.agh.kis.core.data.AtomNodeType;
import pl.edu.agh.kis.core.data.Edge;
import pl.edu.agh.kis.core.data.Node;
import pl.edu.agh.kis.core.data.NodeType;
import pl.edu.agh.kis.core.data.StructNode;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public abstract class Pattern {

    public abstract StructNode findPattern(Graph g, AtomNode start) throws UnexpectedException;

    private Graph replaceGraphSegment(Graph<Node, Edge> g, StructNode patternTree) {
        g.getInEdges(patternTree.getLeft()).stream().forEach((Edge e) -> e.setEnd(patternTree));
        AtomNode last = findLastNode(patternTree);
        if (!last.getOutgoing().isEmpty()) {
            g.getOutEdges(last).stream().forEach((Edge e) -> e.setStart(patternTree));
        }
        return g;
    }

    public AtomNode findLastNode(StructNode patternTree) {
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

    public AtomNode findEndPatternNode(Graph<Node, Edge> g, Node start, Collection<AtomNodeType> types, Integer nestingLevel, Collection<Node> nodes) {
        AtomNode atom = null;
        nodes.add(start);
        if (start.getType() == NodeType.ATOM) {
            atom = (AtomNode) start;
            if (types.contains(atom.getAtomType())) {
                if (atom.getOutgoing().size() == 2) {
                    nestingLevel++;
                } else if (atom.getIncoming().size() == 2) {
                    nestingLevel--;
                    if (nestingLevel == 0) {
                        nodes.remove(start);
                        return atom;
                    }
                }
            }
        }
        for (Node n : g.getSuccessors(start)) {
            atom = findEndPatternNode(g, n, types, nestingLevel, nodes);
        }

        return atom;
    }
}
