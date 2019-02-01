package com.nicok.pathguide.business_logic;

import android.content.Context;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.Graph;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

public class PathFinder {

    private static MapDefinition map = null;
    private static NodeDefinition destination = null;
    private static NodeDefinition currentLocation = null;

    private static Graph graph;

    private static IReader reader = new FileReader();
    private static SerializeWrapper serializeWrapper = new SerializeWrapper();

    public static boolean loadMap(Context context) {
        try {
            String serializedMap = PathFinder.reader.readAsset("map.json", context);

            PathFinder.map = serializeWrapper.deserialize(serializedMap, MapDefinition.class);
            //PathFinder.map.setupEntities();

            PathFinder.graph = new Graph(PathFinder.map.getNodes(), PathFinder.map.getEdges());

            return map != null;
        } catch (Exception e) {
            return false;
        }
    }

//    public static EdgeDefinition getNextInstructions() {
//
//    }

    public static MapDefinition getMap () {
        return PathFinder.map;
    }

    public static boolean hasReachedDestination() {
        return PathFinder.currentLocation.equals(PathFinder.destination);
    }

    public static NodeDefinition getNodeById(String id) {
        return PathFinder.map.getNodeById(id);
    }

    public static boolean setCurrentLocationAndGetIfChanged(NodeDefinition currentLocation){
        if (PathFinder.currentLocation != null && PathFinder.currentLocation.equals(currentLocation)) {
            return false;
        }

        PathFinder.currentLocation = currentLocation;
        return true;
    }

    public static void setDestination(NodeDefinition destination) {
        PathFinder.destination = destination;

        if (PathFinder.destination == null) {
            return;
        }

        PathFinder.calculateDinstancesFromLocation();
    }

    private static void calculateDinstancesFromLocation() {
        if (PathFinder.destination == null || PathFinder.map == null || PathFinder.currentLocation == null) {
            return;
        }

        //PathFinder.graph.calculateDistanceFrom(currentLocation);
    }

    public static EdgeDefinition getNextPath() {
        return PathFinder.graph.getNextEdge();
    }

    public static NodeDefinition getCurrentLocation() {
        return PathFinder.currentLocation;
    }

}
