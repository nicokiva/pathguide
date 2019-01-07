package com.nicok.pathguide.business_definitions;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.nicok.pathguide.business_definitions.NodeTypes.BathroomType;
import com.nicok.pathguide.business_definitions.NodeTypes.ClassroomType;
import com.nicok.pathguide.business_definitions.NodeTypes.GatewayType;
import com.nicok.pathguide.business_definitions.NodeTypes.NodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NodeTypesDeserializer extends JsonDeserializer<List<NodeType>> {

    @Override
    public List<NodeType> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
//        NodeType definition = NodeType.build();

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
