package com.nicok.pathguide.businessLogic;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

import java.nio.file.Path;
import java.util.List;

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

    public static List<NodeDefinition> getShortestPath() {
        return PathFinder.map.getShortestPath();
    }

    public static boolean hasReachedDestination() {
        return PathFinder.map.hasReachedDestination();
    }

    public static EdgeDefinition getCurrentInstructions() {
        return PathFinder.map.getCurrnentInstructions();
    }

    public static EdgeDefinition updateNodeAndGetInstructions(String currentLocationId){
        return PathFinder.map.updateNodeAndGetInstructions(currentLocationId);
    }

    public static void setDestination(NodeDefinition destination) {
        PathFinder.reset();
        PathFinder.map.setDestination(destination);
    }

}
