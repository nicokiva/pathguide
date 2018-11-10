package com.nicok.pathguide.business_interfaces;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)

public abstract class BaseEntity implements Serializable {

    @JsonProperty("id")
    public String id;

    @JsonProperty("description")
    public String description;

}
