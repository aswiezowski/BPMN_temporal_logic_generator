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

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 * Represents BPMN node type
 */
public enum AtomNodeType {

    EVENT, START_EVENT, END_EVENT, TASK, USER_TASK, SEND_TASK, RECEIVE_TASK, MANUAL_TASK, BUSINESS_RULE_TASK, SERVICE_TASK, SCRIPT_TASK, PARALLEL_GATEWAY, INCLUSIVE_GATEWAY, EXCLUSIVE_GATEWAY;

    /**
     * Converts BPMN string to class type
     * @param BPMNTag BPMN tag as strng
     * @return the appropriate type
     */
    public static AtomNodeType convertBPMNtoEnum(String BPMNTag) {
        if ("startEvent".equalsIgnoreCase(BPMNTag)) {
            return START_EVENT;
        }
        if ("endEvent".equalsIgnoreCase(BPMNTag)) {
            return END_EVENT;
        }
        if (BPMNTag.toLowerCase().endsWith("event")) {
            return EVENT;
        }
        if ("task".equalsIgnoreCase(BPMNTag)) {
            return TASK;
        }
        if(BPMNTag.endsWith("Task")){
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
