package com.nicok.pathguide.businessDefinitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class EdgeDefinition implements Serializable {

    @JsonProperty("distance")
    public Integer distance;

    @JsonProperty("from")
    public String from;

    @JsonProperty("to")
    public String to;

    @JsonProperty("instructions")
    public String instructions;

    public String getInstructions() {
        return instructions;
    }

    public Integer getDistance() {
        return distance;
    }
}
