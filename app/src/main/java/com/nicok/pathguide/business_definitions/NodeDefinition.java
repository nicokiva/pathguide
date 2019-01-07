package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nicok.pathguide.business_definitions.NodeTypes.NodeType;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class NodeDefinition extends BaseEntityDefinition implements Serializable {

    @JsonProperty("extra")
    public String extra;

    @JsonProperty("floor")
    public int floor;

    @JsonProperty("types")
    @JsonDeserialize(using = NodeTypesDeserializer.class)
    public List<NodeType> types;

}