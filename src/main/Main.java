package main;

import flows.FordFulkerson;
import graph.Graph;

public class Main {
    public static void main(String[] args){

        Graph graph = new Graph();
        graph.readFromFile("graph.xml");
        //graph.printGraph();

        FordFulkerson fordFulkerson = new FordFulkerson(graph);
        fordFulkerson.edmondsKarp_max();
        System.out.println("======");
        System.out.println();
        System.out.println("The maximum value of the flow thought the graph is: " + fordFulkerson.getValueMaxFlow());
        System.out.println("======");
        fordFulkerson.printResidualGraph();
        }
}
