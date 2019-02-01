package flows;

import graph.Arc;
import graph.Graph;
import graph.Node;

import java.util.*;

public class FordFulkerson {

    Graph graph;
    private HashSet<Integer> hasPathInResidualGraph;
    private HashMap<Integer, Arc> lastEdgeOnShortestResidualPath;
    private int valueMaxFlow;


    public FordFulkerson(Graph graph) {
        this.graph = graph;
    }

    public boolean isInCut(int nodeId) {
        return hasPathInResidualGraph.contains(nodeId);
    }

    public int getValueMaxFlow() {
        return valueMaxFlow;
    }

    public void edmondsKarp_max() {
        while (hasAugmentingPath()) {
            int bottleneckCapacity = Integer.MAX_VALUE;

            //find the bottleneck capacity
            for (Node node = graph.getFlush(); node != graph.getStart();
                 node = lastEdgeOnShortestResidualPath.get(node.getId()).getOtherNode(node)) {
                bottleneckCapacity = Math.min(bottleneckCapacity, lastEdgeOnShortestResidualPath.get(node.getId()).getCapacity());
            }

            //
            for (Node node = graph.getFlush(); node != graph.getStart();
                 node = lastEdgeOnShortestResidualPath.get(node.getId()).getOtherNode(node)) {
                lastEdgeOnShortestResidualPath.get(node.getId()).addResiduaFlowTo(node, bottleneckCapacity);
            }

            for(Arc a : lastEdgeOnShortestResidualPath.values()){
                System.out.print(a.getEndNode() + " ");
            }
            System.out.print("bottleneck capacity: " + bottleneckCapacity);
            System.out.println();

            valueMaxFlow += bottleneckCapacity;
        }

    }

    private boolean hasAugmentingPath() {

        hasPathInResidualGraph = new HashSet<>();
        lastEdgeOnShortestResidualPath = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(graph.getStart());
        hasPathInResidualGraph.add(graph.getStart().getId());

        while (!queue.isEmpty() && !hasPathInResidualGraph.contains(graph.getFlush().getId())) {
            Node node = queue.remove();

            for (Arc arc : node.getAdjaciencies()) {
                Node otherNode = arc.getOtherNode(node);

                if (arc.getResidualCapacityTo(otherNode) > 0) {
                    if (!hasPathInResidualGraph.contains(otherNode.getId())) {
                        lastEdgeOnShortestResidualPath.put(otherNode.getId(), arc);
                        hasPathInResidualGraph.add(otherNode.getId());

                        queue.add(otherNode);
                    }
                }
            }
        }
        //System.out.println(hasPathInResidualGraph.contains(graph.getFlush().getId()));

        return hasPathInResidualGraph.contains(graph.getFlush().getId());
    }

    public void printResidualGraph(){
        graph.printGraph();
    }

}
