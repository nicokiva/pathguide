package com.nicok.pathguide.businessDefinitions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {

    private List<NodeDefinition> nodes;

    private List<NodeDefinition> shortestPath = new ArrayList<>();

    private NodeDefinition destination = null;
    private NodeDefinition currentLocation = null;

    public boolean hasReachedDestination() {
        return this.currentLocation != null && this.destination.equals(this.currentLocation);
    }

    public NodeDefinition getDestination() {
        return destination;
    }

    public void setDestination(NodeDefinition destination) {
        this.destination = destination;
    }

    public NodeDefinition getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation (NodeDefinition currentLocation) {
        this.currentLocation = currentLocation;
        this.shortestPath = new LinkedList<>();
    }

    public List<NodeDefinition> getShortestPath() {
        return shortestPath;
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
                    calculateMinimumDistance(adjacentNode, adjacencyPair.getValue().getDistance(), currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }

    }

    public EdgeDefinition getCurrentInstructions() {
        NodeDefinition nextLocation = this.shortestPath.get(this.shortestPath.indexOf(this.currentLocation) + 1);

        return this.currentLocation.getAdjacentNodes().get(nextLocation);
    }

    public void updateCurrentLocation(NodeDefinition currentLocation) {
        this.currentLocation = currentLocation;
    }

    public EdgeDefinition getInstructionsTo(NodeDefinition currentLocation) {
        if (!this.shortestPath.contains(currentLocation) || !this.shortestPath.contains(this.destination) || this.shortestPath.get(0) != currentLocation) {
            this.calculateShortestPath(currentLocation, this.destination);
        }

        return this.getCurrentInstructions();
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
