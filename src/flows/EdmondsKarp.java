package flows;

import graph.Arc;
import graph.Graph;
import graph.Node;

import java.util.*;

public abstract class EdmondsKarp {

    protected Map<Node, Arc> parent;
    protected Queue<Node> visited;

    int flowValue = 0;

    protected Graph graph;

    public EdmondsKarp(Graph graph) {
        this.graph = graph;

    }

    protected void runEdmondsKarp() {

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
                    if (checkResidualCapacity(arc) && !parent.containsKey(arc.getEndNode())) {
                        parent.put(arc.getEndNode(), arc);
                        visited.add(arc.getEndNode());
                    }
                }
            }


            if (parent.containsKey(graph.getFlush())) {
                hasAugmentingPath = updateFlow();
                // hasAugmentingPath = false;
            }
        } while (parent.containsKey(graph.getFlush()) && hasAugmentingPath);
    }

    abstract boolean checkResidualCapacity(Arc arc);

    abstract boolean updateFlow();

    public int getFlowValue() {
        return flowValue;
    }
}
