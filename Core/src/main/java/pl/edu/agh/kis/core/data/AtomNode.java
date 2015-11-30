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
package pl.edu.agh.kis.core.data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 */
public class AtomNode implements Node {

    private String id;
    private AtomNodeType type;

    private Set<Edge> incoming;
    private Set<Edge> outgoing;

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

    @Override
    public String toTemporalLogic() {
        String temporalLogic = toString();
        return temporalLogic;
    }

}
