package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.EdgeDefinition;

public class Edge {

    private Node fromNode;
    private Node toNode;
    private EdgeDefinition data;

    public Edge(Node fromNode, Node toNode, EdgeDefinition data) {
        this.fromNode = fromNode;
        this.toNode = toNode;
        this.data = data;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public int getLength() {
        return 0;
    }

    // determines the neighbouring node of a supplied node, based on the two nodes connected by this edge
    public Node getNeighbourIndex(Node node) {
        if (this.fromNode == node) {
            return this.toNode;
        } else {
            return this.fromNode;
        }
    }

}