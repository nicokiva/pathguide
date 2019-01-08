package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.NodeDefinition;

import java.util.ArrayList;

public class Node {

    private Integer distanceFromSource = null;
    private ArrayList<Edge> edges = new ArrayList<Edge>();
    private NodeDefinition data;

    public Node(NodeDefinition data) {
        this.data = data;
    }

    public NodeDefinition getData() {
        return data;
    }

    public Integer getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(Integer distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

}
