package com.map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "floor",
    "description",
    "types"
})

public class NodeDefinition {

    @JsonProperty("floor")
    public String floor;

    @JsonProperty("description")
    public String description;


    @JsonProperty("types")
    public String[] types;

}
