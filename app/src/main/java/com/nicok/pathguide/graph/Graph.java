package com.nicok.pathguide.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

// now we must create graph object and implement dijkstra algorithm
public class Graph {

    private Node[] nodes;
    private Edge[] edges;
    private int noOfEdges;

    public Graph(Node[] nodes, Edge[] edges) {
        this.edges = edges;

        // create all nodes ready to be updated with the edges
        this.nodes = nodes;

        // add all the edges to the nodes, each edge added to two nodes (to and from)
        this.noOfEdges = edges.length;

        for(Edge edge : edges) {
            edge.getFromNode().getEdges().add(edge);
            edge.getToNode().getEdges().add(edge);
        }

    }

    public void resetDistance() {
        for(Node node : this.nodes) {
            node.setDistanceFromSource(null);
        }
    }

    private void setDistance(Node initialNode, int distanceFromOrigin) {
        if (initialNode.getDistanceFromSource() != null && initialNode.getDistanceFromSource() <= distanceFromOrigin) {
            return;
        }

        initialNode.setDistanceFromSource(distanceFromOrigin);

        for(Edge edge : initialNode.getEdges()) {
            if (edge.getToNode().equals(initialNode)) {
                continue;
            }

            setDistance(edge.getToNode(), distanceFromOrigin + 1);
        }
    }

    public void calculateDistanceFrom(Node initialNode) {
        this.setDistance(initialNode, 0);
    }

    // now we're going to implement this method in next part !
    private int getNodeShortestDistanced() {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;

        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getDistanceFromSource();

            if (!this.nodes[i].isVisited() && currentDist < storedDist) {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }

        return storedNodeIndex;
    }

    // display result
    public void printResult() {
        String output = "Number of nodes = " + this.nodes.length;
        output += "\nNumber of edges = " + this.noOfEdges;

        for (int i = 0; i < this.nodes.length; i++) {
            output += ("\nThe shortest distance from node 0 to node " + nodes[i].getData().description + " is " + nodes[i].getDistanceFromSource());
        }

        System.out.println(output);
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }

}
