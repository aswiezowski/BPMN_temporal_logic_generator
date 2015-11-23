/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.data;

import java.util.Objects;

/**
 *
 * @author Adam Świeżowski
 */
public class Edge {

    AtomNode start, end;
    String id;

    public Edge(String id) {
        this.id = id;
    }

    public Edge() {

    }

    public AtomNode getStart() {
        return start;
    }

    public void setStart(AtomNode start) {
        this.start = start;
    }

    public AtomNode getEnd() {
        return end;
    }

    public void setEnd(AtomNode end) {
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.start);
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
        final Edge other = (Edge) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
