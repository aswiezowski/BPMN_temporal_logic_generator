/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core;

import edu.uci.ics.jung.graph.Graph;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.List;
import pl.edu.agh.kis.core.data.AtomNode;
import pl.edu.agh.kis.core.data.Node;
import pl.edu.agh.kis.core.data.StructNode;
import pl.edu.agh.kis.patterns.Pattern;
import pl.edu.agh.kis.patterns.Sequence;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public class PatternExtractor {

    public final static List<Pattern> PATTERN_PRIORITY;

    static {
        PATTERN_PRIORITY = new ArrayList<>();
        PATTERN_PRIORITY.add(new Sequence());
    }

    public Node extractPatterns(Graph g, AtomNode startNode) throws  UnexpectedException {
        if(startNode.getOutgoing().isEmpty()){
            return startNode;
        }
        StructNode patternTree = null;
        for (Pattern p : PATTERN_PRIORITY) {
           // while ((patternTree = p.findPattern(g, startNode)) != null) {
                patternTree = p.findPattern(g, startNode);
               // g = replaceGraphSegment(g, patternTree);
               // startNode = findLastNode(patternTree);
            //}
        }
        return patternTree;
    }



}
