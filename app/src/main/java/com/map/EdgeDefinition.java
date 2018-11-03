package com.map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@JsonPropertyOrder({
    "id",
    "from",
    "to",
    "instructions"
})

public class EdgeDefinition {

    @JsonProperty("id")
    public String id;

    @JsonProperty("from")
    public String from;

    @JsonProperty("to")
    public String to;

    @JsonProperty("instructions")
    public String instructions;

}
