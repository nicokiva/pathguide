package com.nicok.pathguide.business_interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "floor",
    "description",
    "types"
})

public class NodeDefinition extends BaseEntity implements Serializable {

    @JsonProperty("floor")
    public String floor;

    @JsonProperty("types")
    public String[] types;

}
