package com.nicok.pathguide.businessDefinitions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.nicok.pathguide.businessDefinitions.nodeTypes.BathroomType;
import com.nicok.pathguide.businessDefinitions.nodeTypes.ClassroomType;
import com.nicok.pathguide.businessDefinitions.nodeTypes.GatewayType;
import com.nicok.pathguide.businessDefinitions.nodeTypes.NodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodeTypesDeserializer extends JsonDeserializer<List<NodeType>> {

    @Override
    public List<NodeType> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        JsonNode rawTypes = jsonParser.getCodec().readTree(jsonParser);
        if (!rawTypes.isArray()) {
            return null;
        }

        ArrayList<NodeType> types = new ArrayList<>();

        for (JsonNode objNode : rawTypes) {
            String type = objNode.textValue();
            if (type.equals(NodeType.BATHROOM_TYPE)) {
                types.add(new BathroomType());
            } else if (type.equals(NodeType.CLASSROOM_TYPE)) {
                types.add(new ClassroomType());
            } else if (type.equals(NodeType.GATEWAY_TYPE)) {
                types.add(new GatewayType());
            }
        }

        return types;
    }

}
