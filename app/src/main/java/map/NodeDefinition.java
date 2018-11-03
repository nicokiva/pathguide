package map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;

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
