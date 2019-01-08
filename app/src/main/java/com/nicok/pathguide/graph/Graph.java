package com.nicok.pathguide.graph;

public class Graph {

    private Node[] nodes;
    private Edge[] edges;
    private int noOfEdges;

    public Graph(Node[] nodes, Edge[] edges) {
        this.edges = edges;
        this.nodes = nodes;
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

    public Integer getDistanceTo(Node toNode) {
        return toNode.getDistanceFromSource();
    }

    public void calculateDistanceFrom(Node initialNode) {
        this.setDistance(initialNode, 0);
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

    public Edge[] getEdges() {
        return edges;
    }

}
