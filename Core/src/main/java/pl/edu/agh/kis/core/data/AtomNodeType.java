/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.data;

/**
 *
 * @author Adam Świeżowski <adam.swiezowski+projects [at] gmail [dot] com>
 */
public enum AtomNodeType {

    START_EVENT, END_EVENT, TASK, USER_TASK, SEND_TASK, RECEIVE_TASK, MANUAL_TASK, BUSINESS_RULE_TASK, SERVICE_TASK, SCRIPT_TASK, PARALLEL_GATEWAY, INCLUSIVE_GATEWAY, EXCLUSIVE_GATEWAY;

    public static AtomNodeType convertBPMNtoEnum(String BPMNTag) {
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
