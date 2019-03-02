package com.nicok.pathguide.businessDefinitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicok.pathguide.businessDefinitions.NodeTypes.NodeType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class MapDefinition implements Serializable {

    @JsonProperty("nodes")
    public List<NodeDefinition> nodes;

    @JsonProperty("edges")
    public List<EdgeDefinition> edges;

    private Graph graph;

    public EdgeDefinition updateNodeAndGetInstructions(String currentLocationId) {
        NodeDefinition currentLocation = this.getNodeById(currentLocationId);
        return this.graph.updateNodeAndGetInstructions(currentLocation);
    }

    public List<NodeDefinition> getFinalNodes() {
        return nodes.stream()
                .filter(
                    node -> Arrays.stream(node.types.toArray()).anyMatch(x -> ((NodeType)x).isFinal()) &&
                    edges.stream().anyMatch(edge -> edge.to.equals(node.getTag()))
                )
                .collect(Collectors.toList());
    }

    public void setDestination(NodeDefinition destination) {
        this.graph.setDestination(destination);
    }

    public void setCurrentLocation(NodeDefinition currentLocation) {
        this.graph.setCurrentLocation(currentLocation);
    }

    public List<NodeDefinition> getNodes() {
        if (nodes == null) {
            return new ArrayList<>();
        }

        return nodes;
    }

    public NodeDefinition getNodeById(String id) {
        return this.nodes.stream().filter(node -> node.getId().equals(id)).findFirst().get();
    }

    public boolean hasReachedDestination() {
        return this.graph.hasReachedDestination();
    }

    public void setupEntities() {
        for (EdgeDefinition edge: edges) {
            NodeDefinition from = this.nodes.stream().filter(node -> node.getTag().equals(edge.from)).findFirst().get();
            NodeDefinition to = this.nodes.stream().filter(node -> node.getTag().equals(edge.to)).findFirst().get();

            from.addDestination(to, edge);
        }

        this.graph = new Graph(this.nodes);
    }

}
