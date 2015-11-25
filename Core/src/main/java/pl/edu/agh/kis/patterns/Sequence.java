/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.patterns;

import edu.uci.ics.jung.graph.Graph;
import java.rmi.UnexpectedException;
import pl.edu.agh.kis.core.PatternExtractor;
import pl.edu.agh.kis.core.data.AtomNode;
import pl.edu.agh.kis.core.data.StructNode;
import pl.edu.agh.kis.core.data.StructNodeType;
import pl.edu.agh.kis.core.utilities.GraphUtils;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public class Sequence extends Pattern {

    @Override
    public StructNode findPattern(Graph g, AtomNode start) throws  UnexpectedException {
        StructNode snode = null;
        if (start.getOutgoing().size() == 1) {
            PatternExtractor pe = new PatternExtractor();
            snode = new StructNode(start, pe.extractPatterns(g, (AtomNode) g.getSuccessors(start).iterator().next()), null, null, StructNodeType.SEQENCE);
        }
        return snode;
    }

}
