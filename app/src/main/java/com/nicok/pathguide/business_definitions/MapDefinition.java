package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "nodes",
    "edges"
})

public class MapDefinition implements Serializable {

    @JsonProperty("nodes")
    public List<NodeDefinition> nodes;

    @JsonProperty("edges")
    public List<EdgeDefinition> edges;

    public List<NodeDefinition> getFinalNodes() {
        if (this.nodes == null) {
            return new ArrayList<>();
        }


        return nodes.stream().filter(node -> Arrays.stream(node.types).anyMatch("final"::equalsIgnoreCase)).collect(Collectors.toList());
    }

}
