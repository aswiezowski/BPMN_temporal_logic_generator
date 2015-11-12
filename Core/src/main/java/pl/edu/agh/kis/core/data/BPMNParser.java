/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Adam Świeżowski
 */
public class BPMNParser {

    File f;
    Graph<Node, Edge> g;
    final private static String namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL";

    class BPMNHandler extends DefaultHandler {

        final private Pattern taskPattern = Pattern.compile(".*task", Pattern.CASE_INSENSITIVE);
        final private Pattern eventPattern = Pattern.compile(".*Event", Pattern.CASE_INSENSITIVE);
        final private Pattern gatewayPattern = Pattern.compile(".*Gateway", Pattern.CASE_INSENSITIVE);

        final private Map<String, Node> nodes = new HashMap<>();
        final private Map<String, Edge> edges = new HashMap<>();

        private Node getNode(String id) {
            Node n = nodes.get(id);
            if (n == null) {
                nodes.put(id, n = new Node(id));
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
                Node n = null;
                Edge e = null;
                Matcher taskMatcher = taskPattern.matcher(localName);
                Matcher eventMatcher = eventPattern.matcher(localName);
                Matcher gatewayMatcher = gatewayPattern.matcher(localName);
                if (taskMatcher.matches() || eventMatcher.matches() || gatewayMatcher.matches()) {
                    n = getNode(attributes.getValue("id"));
                    n.setType(Node.NodeType.convertBPMNtoEnum(localName));
                }
                if ("sequenceFlow".equalsIgnoreCase(localName)) {
                    e = getEdge(attributes.getValue("id"));
                    Node sourceNode = getNode(attributes.getValue("sourceRef"));
                    Node targetNode = getNode(attributes.getValue("targetRef"));
                    e.setStart(sourceNode);
                    e.setEnd(targetNode);
                    sourceNode.getOutgoing().add(e);
                    targetNode.getIncoming().add(e);
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            for (Node n : nodes.values()) {
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
