package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nicok.pathguide.business_definitions.NodeTypes.NodeType;

import java.io.Serializable;
import java.util.ArrayList;
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

    public Integer getIcon() {
        if (this.types == null) {
            return null;
        }

        return types.get(0).getImageSource();
    }

    public void addEdge(EdgeDefinition edge) {
        edges.add(edge);
    }

    private Integer distanceFromSource = null;
    private ArrayList<EdgeDefinition> edges = new ArrayList<>();

    public Integer getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(Integer distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public ArrayList<EdgeDefinition> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<EdgeDefinition> edges) {
        this.edges = edges;
    }

}