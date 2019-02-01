package com.nicok.pathguide.business_definitions;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import java.util.LinkedList;
import java.util.List;

public class Graph {

    private List<NodeDefinition> nodes;
    private List<EdgeDefinition> edges;
    private int noOfEdges;

    public Graph(List<NodeDefinition> nodes, List<EdgeDefinition> edges) {
        this.edges = edges;
        this.nodes = nodes;
        this.noOfEdges = edges.size();

        for(EdgeDefinition edge : edges) {
            edge.getFromNode().getEdges().add(edge);
            edge.getToNode().getEdges().add(edge);
        }
    }

    public void resetDistance() {
        for(NodeDefinition node : this.nodes) {
            node.setDistanceFromSource(null);
        }
    }

    public EdgeDefinition getNextEdge() {
        return null;
    }

    private void setDistance(NodeDefinition initialNode, int distanceFromOrigin) {
        if (initialNode.getDistanceFromSource() != null && initialNode.getDistanceFromSource() <= distanceFromOrigin) {
            return;
        }

        initialNode.setDistanceFromSource(distanceFromOrigin);

        for(EdgeDefinition edge : initialNode.getEdges()) {
            if (edge.getToNode().equals(initialNode)) {
                continue;
            }

            setDistance(edge.getToNode(), distanceFromOrigin + 1);
        }
    }

    public Integer getDistanceTo(NodeDefinition toNode) {
        return toNode.getDistanceFromSource();
    }

    public void calculateDistanceFrom(NodeDefinition initialNode) {
        /*
            THIS METHOD SHOULD ONLY CALCULATE DISTANCE TO GENERATE A SIMPLE PATH.
         */

        this.setDistance(initialNode, 0);
    }

    private LinkedList<BaseEntityDefinition> path;
    public void calculatePath(NodeDefinition from, NodeDefinition to) {
        if (from == null || to == null) {
            return;
        }


    }

    public List<EdgeDefinition> getEdges() {
        return edges;
    }

}
