package com.nicok.pathguide.business_definitions;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class BaseEntityDefinition implements Serializable {

    public boolean visited = false;

    @JsonProperty("id")
    public String id;

    @JsonProperty("description")
    public String description;

    public String getId() {
        return this.id;
    }

}
