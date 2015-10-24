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
public class Node {

    public enum NodeType {

        START_EVENT, END_EVENT, TASK, USER_TASK, SEND_TASK, RECEIVE_TASK, MANUAL_TASK, BUSINESS_RULE_TASK, SERVICE_TASK, SCRIPT_TASK, PARALLEL_GATEWAY, INCLUSIVE_GATEWAY, EXCLUSIVE_GATEWAY;

        public static NodeType convertBPMNtoEnum(String BPMNTag) {
            if ("startEvent".equalsIgnoreCase(BPMNTag)) {
                return START_EVENT;
            }
            if ("endEvent".equalsIgnoreCase(BPMNTag)) {
                return END_EVENT;
            }
            if ("task".equalsIgnoreCase(BPMNTag)) {
                return TASK;
            }
            if ("userTask".equalsIgnoreCase(BPMNTag)) {
                return USER_TASK;
            }
            if ("sendTask".equalsIgnoreCase(BPMNTag)) {
                return SEND_TASK;
            }
            if ("receiveTask".equalsIgnoreCase(BPMNTag)) {
                return RECEIVE_TASK;
            }
            if ("manualTask".equalsIgnoreCase(BPMNTag)) {
                return MANUAL_TASK;
            }
            if ("businessRuleTask".equalsIgnoreCase(BPMNTag)) {
                return BUSINESS_RULE_TASK;
            }
            if ("serviceTask".equalsIgnoreCase(BPMNTag)) {
                return SERVICE_TASK;
            }
            if ("scriptTask".equalsIgnoreCase(BPMNTag)) {
                return SCRIPT_TASK;
            }
            if ("parallelGateway".equalsIgnoreCase(BPMNTag)) {
                return PARALLEL_GATEWAY;
            }
            if ("inclusiveGateway".equalsIgnoreCase(BPMNTag)) {
                return INCLUSIVE_GATEWAY;
            }
            if ("exclusiveGateway".equalsIgnoreCase(BPMNTag)) {
                return EXCLUSIVE_GATEWAY;
            }
            return null;
        }
    }

    String id;
    NodeType type;

    Set<Edge> incoming;
    Set<Edge> outgoing;

    public Node() {
        this.incoming = new HashSet<>();
        this.outgoing = new HashSet<>();
    }

    public Node(String id) {
        this();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
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
        final Node other = (Node) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
