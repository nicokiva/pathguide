package com.nicok.pathguide.business_logic;

import android.content.Context;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

public class PathFinder {

    private static MapDefinition map = null;

    private static IReader reader = new FileReader();
    private static SerializeWrapper serializeWrapper = new SerializeWrapper();

    public static boolean loadMap(Context context) {
        try {
            String serializedMap = PathFinder.reader.readAsset("map.json", context);

            PathFinder.map = serializeWrapper.deserialize(serializedMap, MapDefinition.class);
            PathFinder.map.setupEntities();

            return map != null;
        } catch (Exception e) {
            return false;
        }
    }

    public static MapDefinition getMap () {
        return PathFinder.map;
    }

    public static void reset() {
        PathFinder.map.setDestination(null);
        PathFinder.map.setCurrentLocation(null);
    }

    public static boolean hasReachedDestination() {
        return PathFinder.map.hasReachedDestination();
    }

    public static EdgeDefinition updateNodeAndGetInstructions(String currentLocationId){
        return PathFinder.map.updateNodeAndGetInstructions(currentLocationId);
    }

    public static void setDestination(NodeDefinition destination) {
        PathFinder.map.setDestination(destination);
    }

}
