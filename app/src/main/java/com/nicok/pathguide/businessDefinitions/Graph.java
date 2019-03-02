package com.nicok.pathguide.businessDefinitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private List<NodeDefinition> nodes = new ArrayList<>();

    private List<NodeDefinition> shortestPath = new ArrayList<>();

    private NodeDefinition destination = null;
    private NodeDefinition currentLocation = null;

    public boolean hasReachedDestination() {
        return this.currentLocation != null && this.destination.equals(this.currentLocation);
    }

    public void setDestination(NodeDefinition destination) {
        this.destination = destination;
    }

    public void setCurrentLocation (NodeDefinition currentLocation) {
        this.currentLocation = currentLocation;
        this.shortestPath = new LinkedList<>();
    }

    public List<NodeDefinition> getNodes() {
        return nodes;
    }

    public Graph(List<NodeDefinition> nodes) {
        this.nodes = nodes;
    }

    private static NodeDefinition getLowestDistanceNode(Set<NodeDefinition> unsettledNodes) {
        NodeDefinition lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (NodeDefinition node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(NodeDefinition evaluationNode, Integer edgeWeigh, NodeDefinition sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<NodeDefinition> shortestPath = new LinkedList<>(sourceNode.getShortestPath());


            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private void resetPath() {
        for (NodeDefinition node: this.getNodes()) {
            node.setDistance(Integer.MAX_VALUE);
            node.setShortestPath(new LinkedList<>());
        }
    }

    private void calculateShortestPathFromSource(NodeDefinition source) {
        this.resetPath();
        source.setDistance(0);

        Set<NodeDefinition> settledNodes = new HashSet<>();
        Set<NodeDefinition> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            NodeDefinition currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<NodeDefinition, EdgeDefinition> adjacencyPair: currentNode.getAdjacentNodes().entrySet()) {
                NodeDefinition adjacentNode = adjacencyPair.getKey();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, 1, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

    }

    public EdgeDefinition updateNodeAndGetInstructions(NodeDefinition currentLocation) {
        if (this.currentLocation != null && this.currentLocation.equals(currentLocation)) {
            return null;
        }

        if (this.destination.equals(currentLocation)) {
            this.currentLocation = currentLocation;
            return null;
        }

        this.currentLocation = currentLocation;

        if (!this.shortestPath.contains(currentLocation) || !this.shortestPath.contains(this.destination)) {
            this.calculateShortestPath(currentLocation, this.destination);
        }

        NodeDefinition nextLocation = this.shortestPath.get(this.shortestPath.indexOf(currentLocation) + 1);

        return currentLocation.getAdjacentNodes().get(nextLocation);
    }

    public Integer calculateShortestPath(NodeDefinition from, NodeDefinition to) {
        if (this.getNodes().size() == 0) {
            return null;
        }

        this.calculateShortestPathFromSource(from);

        NodeDefinition destination = this.getNodes().stream().filter(node -> node.equals(to)).findFirst().get();
        this.shortestPath = destination.getShortestPath();
        this.shortestPath.add(destination);
        this.destination = to;

        return this.shortestPath.size() - 1;
    }

}
