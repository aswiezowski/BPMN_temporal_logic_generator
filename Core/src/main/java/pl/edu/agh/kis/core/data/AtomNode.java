/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Adam Świeżowski
 */
public class AtomNode implements Node {

    String id;
    AtomNodeType type;

    Set<Edge> incoming;
    Set<Edge> outgoing;

    public AtomNode() {
        this.incoming = new HashSet<>();
        this.outgoing = new HashSet<>();
    }

    public AtomNode(String id) {
        this();
        this.id = id;
    }

    @Override
    public NodeType getType() {
        return NodeType.ATOM;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AtomNodeType getAtomType() {
        return type;
    }

    public void setAtomType(AtomNodeType type) {
        this.type = type;
    }

    public Set<Edge> getIncoming() {
        return incoming;
    }

    public void setIncoming(Set<Edge> incoming) {
        this.incoming = incoming;
    }

    public Set<Edge> getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(Set<Edge> outgoing) {
        this.outgoing = outgoing;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AtomNode other = (AtomNode) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        return id;
    }

}
