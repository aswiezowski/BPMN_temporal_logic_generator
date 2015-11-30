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

import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import java.io.File;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pl.edu.agh.kis.core.utilities.GraphUtils;

/**
 *
 * @author Adam Świeżowski, Jakub Piotrowski
 */
public class BPMNParser {

    private File f;
    private Graph<Node, Edge> g;
    final private static String namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL";

    class BPMNHandler extends DefaultHandler {

        final private Pattern taskPattern = Pattern.compile(".*task", Pattern.CASE_INSENSITIVE);
        final private Pattern eventPattern = Pattern.compile(".*Event", Pattern.CASE_INSENSITIVE);
        final private Pattern gatewayPattern = Pattern.compile(".*Gateway", Pattern.CASE_INSENSITIVE);
        final private Pattern eventDefPattern = Pattern.compile(".*EventDefinition", Pattern.CASE_INSENSITIVE);

        final private Map<String, AtomNode> nodes = new HashMap<>();
        final private Map<String, Edge> edges = new HashMap<>();

        private AtomNode lastNode = null;

        private AtomNode getNode(String id) {
            AtomNode n = nodes.get(id);
            if (n == null) {
                nodes.put(id, n = new AtomNode(id));
            }
            return n;
        }

        private Edge getEdge(String id) {
            Edge e = edges.get(id);
            if (e == null) {
                edges.put(id, e = new Edge(id));
            }
            return e;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (namespace.equals(uri)) {
                AtomNode n = null;
                Edge e = null;
                Matcher taskMatcher = taskPattern.matcher(localName);
                Matcher eventMatcher = eventPattern.matcher(localName);
                Matcher gatewayMatcher = gatewayPattern.matcher(localName);
                Matcher eventDefMatcher = eventDefPattern.matcher(localName);
                if (taskMatcher.matches() || eventMatcher.matches() || gatewayMatcher.matches()) {
                    n = getNode(attributes.getValue("id"));
                    n.setAtomType(AtomNodeType.convertBPMNtoEnum(localName));
                    if(eventMatcher.matches()){
                        lastNode = n;
                    }
                }
                if ("sequenceFlow".equalsIgnoreCase(localName)) {
                    e = getEdge(attributes.getValue("id"));
                    AtomNode sourceNode = getNode(attributes.getValue("sourceRef"));
                    AtomNode targetNode = getNode(attributes.getValue("targetRef"));
                    e.setStart(sourceNode);
                    e.setEnd(targetNode);
                    sourceNode.getOutgoing().add(e);
                    targetNode.getIncoming().add(e);
                }
                if (lastNode != null && lastNode.getAtomType() == AtomNodeType.START_EVENT && eventDefMatcher.matches()) {
                    lastNode.setAtomType(AtomNodeType.EVENT);
                }
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (namespace.equals(uri)) {
                Matcher eventMatcher = eventPattern.matcher(localName);
                if (eventMatcher.matches()) {
                    lastNode = null;
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            for (AtomNode n : nodes.values()) {
                if (n.getAtomType() == AtomNodeType.START_EVENT) {
                    GraphUtils.START_NODE = n;
                } else if (n.getAtomType() == AtomNodeType.END_EVENT) {
                    GraphUtils.END_NODES.add(n);
                }
                g.addVertex(n);
            }
            for (Edge e : edges.values()) {
                g.addEdge(e, e.getStart(), e.getEnd());
            }
        }

    }

    public Graph<Node, Edge> parseBPMN(File BPMNFile) {
        g = new DirectedSparseGraph<>();
        f = BPMNFile;
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        try {
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(new BPMNHandler());
            xmlReader.parse(f.getAbsolutePath());
        } catch (SAXException e) {

        } catch (IOException e) {

        } catch (ParserConfigurationException e) {

        }
        return g;
    }
}
