package com.nicok.pathguide.graph;

import com.nicok.pathguide.business_definitions.NodeDefinition;

import java.util.ArrayList;

public class Node {

    private Integer distanceFromSource = null;
    private boolean visited;
    private ArrayList<Edge> edges = new ArrayList<Edge>(); // now we must create edges
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

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

}
