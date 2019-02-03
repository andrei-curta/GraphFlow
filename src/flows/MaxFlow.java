package flows;

import graph.Arc;
import graph.Graph;
import graph.Node;

public class MaxFlow extends EdmondsKarp {

    public MaxFlow(Graph graph) {
        super(graph);
        runEdmondsKarp();
    }

    @Override
    boolean checkResidualCapacity(Arc arc) {
        if(arc.getResidualCapacity() > 0)
            return true;
        return false;
    }

    @Override
    boolean updateFlow() {
        int bottleneckCapacity = Integer.MAX_VALUE;

        //find the bottleneck capacity
        for (Node node = graph.getFlush(); node != graph.getStart(); node = parent.get(node).getOtherNode(node)) {
            bottleneckCapacity = Math.min(bottleneckCapacity, parent.get(node).getResidualCapacity());

            System.out.print(node.getId() + " ");
        }
        System.out.println();

        if (bottleneckCapacity == Integer.MAX_VALUE)
            return false;

        //increase flow along the augmenting path ( aka DMF )
        for (Node node = graph.getFlush(); node != graph.getStart(); node = parent.get(node).getOtherNode(node)) {
            parent.get(node).addFlow(bottleneckCapacity);

            System.out.print("Arc (" + node.getId() + ", " + parent.get(node).getOtherNode(node) + ") " + parent.get(node).getFlow() + " ");
            //update the backward edge
            Node otherNode = parent.get(node).getOtherNode(node);
            // otherNode.getArcByEndNodeId(node.getId()).addFlow(bottleneckCapacity);

            System.out.println("Arc (" + otherNode.getId() + ", " + otherNode.getArcByEndNodeId(node.getId()).getOtherNode(otherNode) + ") " + otherNode.getArcByEndNodeId(node.getId()).getFlow());
            //System.out.print(node.getId() + " f:" + parent.get(node).getFlow() + "; ");
        }
        flowValue += bottleneckCapacity;
        return true;
    }
}
