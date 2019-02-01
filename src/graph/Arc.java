package graph;

public class Arc {

    private int max_capacity;
    private int flow = 0;


    private  Node startNode;
    private Node endNode;

    public Node getOtherNode(Node node){
        if(node.equals(startNode)){
            return endNode;
        }else {
            return startNode;
        }
    }

    public int getResidualCapacityTo(Node node){
        if(node.getId() == startNode.id){
            return flow;    //forward edge
        }else{
            return max_capacity - flow; //backward edge
        }
    }

    public void addResiduaFlowTo(Node node, int deltaFlow){
        if(node.getId() == startNode.id){
            flow -= deltaFlow;  //forward edge
        }else{
            flow += deltaFlow; //backward edge
        }
    }

    public Arc(Node startNode, Node endNode, int capacity) {
        this.max_capacity = capacity;
        this.endNode = endNode;
        this.startNode = startNode;
    }

    public Arc(Node endNode, int capacity) {
        this.max_capacity = capacity;
        this.endNode = endNode;
    }

    public int getCapacity() {
        return max_capacity;
    }

    public int getResidualCapacity(){
        return max_capacity - flow;
    }

    public void setCapacity(int capacity) {
        this.max_capacity = capacity;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }


    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }
}

