package com.nicok.pathguide.businessDefinitions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nicok.pathguide.businessDefinitions.NodeTypes.NodeType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class NodeDefinition implements Serializable {

    @JsonProperty("tag")
    public String tag;

    @JsonProperty("extra")
    public String extra;

    @JsonProperty("floor")
    public int floor;

    @JsonProperty("types")
    @JsonDeserialize(using = NodeTypesDeserializer.class)
    public List<NodeType> types;

    @JsonProperty("description")
    public String description;

    @JsonProperty("id")
    public String id;

    public String getId() {
        return id;
    }

    @JsonIgnore()
    private Integer distance = null;

    @JsonIgnore()
    private String name = null;

    @JsonIgnore()
    Map<NodeDefinition, EdgeDefinition> adjacentNodes = new HashMap<>();

    @JsonIgnore()
    private List<NodeDefinition> shortestPath = new LinkedList<>();

    public Integer getIcon() {
        if (this.types == null) {
            return null;
        }

        return types.get(0).getImageSource();
    }

    public List<NodeDefinition> getShortestPath() {
        return this.shortestPath;
    }

    public void setShortestPath(List<NodeDefinition> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public Map<NodeDefinition, EdgeDefinition> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addDestination(NodeDefinition destination, EdgeDefinition edge) {
        adjacentNodes.put(destination, edge);
    }

    public NodeDefinition() { }

    public NodeDefinition(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }
}