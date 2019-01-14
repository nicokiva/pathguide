package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class EdgeDefinition extends BaseEntityDefinition implements Serializable {

    @JsonProperty("from")
    public String from;
    public NodeDefinition fromNode;

    @JsonProperty("to")
    public String to;
    public NodeDefinition toNode;

    @JsonProperty("instructions")
    public String instructions;

    public void setFromTo(NodeDefinition from, NodeDefinition to) {
        fromNode = from;
        toNode = to;
    }

    public NodeDefinition getFromNode() {
        return fromNode;
    }

    public NodeDefinition getToNode() {
        return toNode;
    }

    public int getLength() {
        return 0;
    }

}
