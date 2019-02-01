package graph;

import XML_reader.XML_reader;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * this class represents a weighted directed graph
 */
public class Graph {

    Map<Integer, Node> nodes = new HashMap<>();

    public Node getStart() {
        return start;
    }

    public Node getFlush() {
        return flush;
    }

    Node start;
    Node flush;

    public Graph() {
    }

    /**
     * function used for printing the graph to console in a manner resembling the one that it is stored in
     * use for testing
     */

    public void printGraph(){
        for(Map.Entry<Integer, Node> entry : nodes.entrySet()){

            for (Arc arc : entry.getValue().getAdjaciencies()) {
                System.out.print("( " + entry.getKey() + ", " + arc.getEndNode().getId() + ", flow: " + arc.getFlow() + ") ");
                System.out.println("( " + arc.getEndNode().id + ", " + arc.getStartNode().getId() + ", residual capacity: " +  (arc.getCapacity() - arc.getFlow()) + ") ");
            }
        }
    }


    /**
     * add nodes to the map of nodes, with the arc lists empty(will be populated by "addArcsToNodes")
     *
     * @param nodeList
     */
    private void addNodes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node node = nodeList.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;
                int id = Integer.parseInt(nodeElement.getAttribute("id"));

                nodes.put(id, new Node(id));
            }
        }
    }

    /**
     * populates the list of exiting arcs for every node
     *
     * @param nodeList
     */
    private void addArcsToNodes(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            org.w3c.dom.Node node = nodeList.item(i);

            if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element nodeElement = (Element) node;
                //get the atributes from the node element
                int idStart = Integer.parseInt(nodeElement.getAttribute("from"));
                int idEnd = Integer.parseInt(nodeElement.getAttribute("to"));
                int capacity = Integer.parseInt(nodeElement.getAttribute("max_capacity"));

                //get the startNode and endNode by the id specified in idEnd and create a new arc object
                Node endNode = nodes.get(idEnd);
                Node startNode = nodes.get(idStart);
                Arc arcToAdd = new Arc(startNode, endNode, capacity);

                //add the arc created to the corresponding node
                try {
                    nodes.get(idStart).addArc(arcToAdd);
                } catch (NullPointerException e) {
                    System.out.println("Arc miss");
                }

            }
        }
    }

    /**
     * reads the nodes and arcs from an XML file specified in "path"
     *
     * @param path
     */
    public void readFromFile(String path) {

        File fileToreadFrom = new File(path);

        if (!fileToreadFrom.exists()) {
            System.out.println("File does not exist!");
            return;
        }

        XML_reader xml_reader = new XML_reader(fileToreadFrom);


        //get the nodes
        NodeList nodeList = xml_reader.readByTagName("node");
        //add the nodes to the map of nodes
        addNodes(nodeList);

        //get the arcs
        NodeList arcList = xml_reader.readByTagName("arc");
        //populate the exitArcs field of each node
        addArcsToNodes(arcList);

        //get the start node id and set the start node of the graph to the node with the found id
        NodeList startNode = xml_reader.readByTagName("start");
        org.w3c.dom.Node node = startNode.item(0);

        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element nodeElement = (Element) node;
            int id = Integer.parseInt(nodeElement.getAttribute("id"));

            start = nodes.get(id);
        }

        //get the flush node id and set the flush node of the graph to the node with the found id
        NodeList endNode = xml_reader.readByTagName("flush");
        node = endNode.item(0);

        if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            Element nodeElement = (Element) node;
            int id = Integer.parseInt(nodeElement.getAttribute("id"));

            flush = nodes.get(id);
        }
    }

    public void addArc(Arc arc) {
        nodes.get(arc.getStartNode()).addArc(arc);
        nodes.get(arc.getEndNode()).addArc(arc);
    }

}
