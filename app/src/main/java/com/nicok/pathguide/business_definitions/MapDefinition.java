package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicok.pathguide.business_definitions.NodeTypes.NodeType;

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

    public List<NodeDefinition> getFinalNodes() {
        return getNodes().stream().filter(node -> Arrays.stream(node.types.toArray()).anyMatch(x -> ((NodeType)x).isFinal())).collect(Collectors.toList());
    }

    public List<NodeDefinition> getNodes() {
        if (nodes == null) {
            return new ArrayList<>();
        }

        return nodes;
    }

    public List<EdgeDefinition> getEdges() {
        if(edges == null) {
            return new ArrayList<>();
        }

        return edges;
    }

    public void setupEntities() {
        for (EdgeDefinition edge: edges) {
            NodeDefinition from = nodes.stream().filter(node -> node.id.equals(edge.from)).findFirst().get();
            NodeDefinition to = nodes.stream().filter(node -> node.id.equals(edge.to)).findFirst().get();

            edge.setFromTo(from, to);

            from.addEdge(edge);
            to.addEdge(edge);
        }
    }

}
