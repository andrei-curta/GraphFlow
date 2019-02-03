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

    private ArrayList<Node> lastPath = new ArrayList<>();
    private HashSet<Integer> visited = new HashSet<>();


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

            for (Node n : lastPath) {
                System.out.print(n.getId() + " ");
            }
            System.out.print("bottleneck capacity: " + bottleneckCapacity);
            System.out.println();

            valueMaxFlow += bottleneckCapacity;
        }

    }

    public void edmodsKarp2(){
        while(hasAugmentingPath2()){
            int bottleneckCapacity = Integer.MAX_VALUE;

            //find the bottleneck capacity in the identified augmenting path
            Node n = lastPath.get(0);
            for(int i = 1; i< lastPath.size(); i++){
                ArrayList<Arc> adj = n.getAdjaciencies();

                //adj.
            }
        }
    }

    private boolean hasAugmentingPath2() {
        visited = new HashSet<>();
        lastPath = new ArrayList<>();

        HashMap<Node, Node> parent = new HashMap<>();

        Queue<Node> queue = new LinkedList<>();
        queue.add(graph.getStart());

        while (!queue.isEmpty() && !lastPath.contains(graph.getFlush())) {
            Node node = queue.remove();

            for (Arc arc : node.getAdjaciencies()) {
                Node otherNode = arc.getOtherNode(node);

                if (arc.getResidualCapacityTo(otherNode) > 0) {
                    if(!visited.contains(otherNode.getId())){
                        visited.add(otherNode.getId());
                        parent.put(otherNode, node);

                        queue.add(otherNode);
                    }
                }

            }
        }

        //lastPath.add(graph.getFlush());
        Node n = graph.getFlush();
        while (n != graph.getStart()){
            lastPath.add(n);
            n = parent.get(n);
        }

        lastPath.add(graph.getStart());

        return visited.contains(graph.getFlush().getId());
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

                lastPath.add(node);

                if (arc.getResidualCapacityTo(otherNode) > 0) {
                    if (!hasPathInResidualGraph.contains(otherNode.getId())) {
                        lastEdgeOnShortestResidualPath.put(otherNode.getId(), arc);
                        hasPathInResidualGraph.add(otherNode.getId());

                        //lastPath.add(otherNode);

                        queue.add(otherNode);
                    }
                }
            }
        }
        //System.out.println(hasPathInResidualGraph.contains(graph.getFlush().getId()));

        return hasPathInResidualGraph.contains(graph.getFlush().getId());
    }

    public void printResidualGraph() {
        graph.printGraph();
    }

}
