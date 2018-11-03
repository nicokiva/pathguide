package com.nicok.pathguide.business_interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "nodes",
    "edges"
})

public class MapDefinition {

    @JsonProperty("nodes")
    public HashMap<String, NodeDefinition>[] nodes;

    @JsonProperty("edges")
    public HashMap[] edges;



}
