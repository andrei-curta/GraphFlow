package main;

import flows.EdmondsKarp;
import flows.FordFulkerson;
import flows.MaxFlow;
import flows.MinFlow;
import graph.Graph;

public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph();
        graph.readFromFile("graph.xml");
        //graph.printGraph();

        /*
        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        fordFulkerson.edmondsKarp_max();
        System.out.println("======");
        System.out.println();
        System.out.println("The maximum value of the flow thought the graph is: " + fordFulkerson.getValueMaxFlow());
        System.out.println("======");
        fordFulkerson.printResidualGraph();
        */

        //MaxFlow maxFlow = new MaxFlow(graph);
        //System.out.println("max flow: " + maxFlow.getFlowValue());

        MinFlow minFlow = new MinFlow(graph);
        System.out.println("Min flow: " + minFlow.getFlowValue());

    }
}
