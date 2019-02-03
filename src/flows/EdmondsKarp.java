package flows;

import graph.Arc;
import graph.Graph;
import graph.Node;

import java.util.*;

public class EdmondsKarp {

    Map<Node, Arc> parent;
    Queue<Node> visited;

    int valueMaxFlow = 0;

    Graph graph;

    public EdmondsKarp(Graph graph) {
        this.graph = graph;

        runEdmondsKarp();
    }

    private void runEdmondsKarp() {

        //reset parent and initialize the
        parent = new HashMap<>();
        //parent.put(graph.getFlush().getId(), graph.getStart());

        boolean hasAugmentingPath = false;
        do {

            //initialze p~ and V~
            parent = new HashMap<>();
            visited = new LinkedList<>();

            //set start node as visited
            visited.add(graph.getStart());

            //parent(s) = t

            //while there are nodes to visit and the end has not ben reached
            while (!visited.isEmpty() && !parent.containsKey(graph.getFlush())) {
                Node currentNode = visited.remove();

                for (Arc arc : currentNode.getAdjaciencies()) {
                    if (arc.getResidualCapacity() > 0 && !parent.containsKey(arc.getEndNode())) {
                        parent.put(arc.getEndNode(), arc);
                        visited.add(arc.getEndNode());
                    }
                }
            }


            if (parent.containsKey(graph.getFlush())) {
                hasAugmentingPath = increaseFlow();
               // hasAugmentingPath = false;
            }
        } while (parent.containsKey(graph.getFlush()) && hasAugmentingPath);
    }

    private boolean increaseFlow() {
        int bottleneckCapacity = Integer.MAX_VALUE;

        //find the bottleneck capacity
        for (Node node = graph.getFlush(); node != graph.getStart(); node = parent.get(node).getOtherNode(node)) {
            bottleneckCapacity = Math.min(bottleneckCapacity, parent.get(node).getResidualCapacity());

            System.out.print(node.getId() + " ");
        }
        System.out.println();

        if(bottleneckCapacity == Integer.MAX_VALUE)
            return false;

        //increase flow along the augmenting path ( aka DMF )
        for (Node node = graph.getFlush(); node != graph.getStart(); node = parent.get(node).getOtherNode(node)) {
            parent.get(node).addFlow(bottleneckCapacity);

            System.out.print("Arc (" + node.getId() + ", " + parent.get(node).getOtherNode(node) + ") " +  parent.get(node).getFlow() + " ");
            //update the backward edge
            Node otherNode = parent.get(node).getOtherNode(node);
           // otherNode.getArcByEndNodeId(node.getId()).addFlow(bottleneckCapacity);

            System.out.println("Arc (" + otherNode.getId() + ", " + otherNode.getArcByEndNodeId(node.getId()).getOtherNode(otherNode) + ") " + otherNode.getArcByEndNodeId(node.getId()).getFlow());
            //System.out.print(node.getId() + " f:" + parent.get(node).getFlow() + "; ");
        }
        valueMaxFlow += bottleneckCapacity;
        return true;
    }

    public int getValueMaxFlow(){
        return valueMaxFlow;
    }
}
