package graph;

import java.util.ArrayList;

/**
 * this class represents a node in a graph
 */
public class Node {

    int id;
    ArrayList<Arc> adjacencies = new ArrayList<>();

    public Arc getArcByEndNodeId(int endNodeID) {
        for (Arc arc : adjacencies) {
            if (arc.getEndNode().getId() == endNodeID)
                return arc;
        }
        return null;
    }

    public Node(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addArc(Arc arcToAdd) {
        adjacencies.add(arcToAdd);
    }

    public ArrayList<Arc> getAdjaciencies() {
        return adjacencies;
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
