/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.patterns;

import edu.uci.ics.jung.graph.Graph;
import pl.edu.agh.kis.core.data.Node;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public interface Pattern {
    
    Node findPattern(Graph g);
    
}
