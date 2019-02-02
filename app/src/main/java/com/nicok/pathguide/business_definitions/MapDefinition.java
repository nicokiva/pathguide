package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nicok.pathguide.business_definitions.NodeTypes.NodeType;

import org.w3c.dom.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)


public class MapDefinition implements Serializable {

//    @JsonProperty("nodes")
//    public List<NodeDefinition> nodes;
//
//    @JsonProperty("edges")
//    public List<EdgeDefinition> edges;
//
//    private Graph graph;

    public List<NodeDefinition> getFinalNodes() {
        //return getNodes().stream().filter(node -> Arrays.stream(node.types.toArray()).anyMatch(x -> ((NodeType)x).isFinal())).collect(Collectors.toList());
        return null;
    }

    public List<NodeDefinition> getNodes() {
//        if (nodes == null) {
//            return new ArrayList<>();
//        }
//
//        return nodes;

        return null;
    }

    public List<EdgeDefinition> getEdges() {
//        if(edges == null) {
//            return new ArrayList<>();
//        }
//
//        return edges;
        return null;
    }

    public NodeDefinition getNodeById(String id) {
//        return this.nodes.stream().filter(node -> node.id.equals(id)).findFirst().get();
        return null;
    }

    public void setupEntities() {
//        for (EdgeDefinition edge: edges) {
//            NodeDefinition from = this.nodes.stream().filter(node -> node.id.equals(edge.from)).findFirst().get();
//            NodeDefinition to = this.nodes.stream().filter(node -> node.id.equals(edge.to)).findFirst().get();
//
//            from.addDestination(to, edge);
//        }
//
//        this.graph = new Graph(this.nodes);
    }

}
