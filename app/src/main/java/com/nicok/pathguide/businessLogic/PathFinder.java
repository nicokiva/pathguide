package com.nicok.pathguide.businessLogic;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.services.MapService;

import java.util.List;

public class PathFinder {

    private static PathFinder _instance = null;
    public static PathFinder getInstance(Context context) {
        if (_instance == null) {
            _instance = new PathFinder(context);
        }

        return _instance;
    }

    public PathFinder(Context context) {
        this.mapService = MapService.getInstance(context);
    }

    private MapDefinition map = null;
    private MapService mapService = null;


    public interface LoadMapServiceListener {
        void onSuccess(MapDefinition map);
        void onFail(Exception e);
    }

    private void setMap(MapDefinition map) {
        this.map = map;
        this.map.setupEntities();
    }

    // TODO: This is a hack to allow infinite beacons and should be removed when project is done.
    public void loadMap() {
        NodeDefinition currentLocation = map.getCurrentLocation();
        NodeDefinition destination = map.getDestination();

        this.mapService.load(new MapService.LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                setMap(map);

                map.setDestination(map.getNodeByTag(destination.tag));
                map.setCurrentLocation(map.getNodeByTag(currentLocation.tag));
            }

            @Override
            public void onFail(Exception e) { }
        });
    }

    public void loadMap(LoadMapServiceListener listener) {
        this.mapService.load(new MapService.LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                setMap(map);

                if (listener != null) {
                    listener.onSuccess(map);
                }
            }

            @Override
            public void onFail(Exception e) {
                if (listener != null) {
                    listener.onFail(e);
                }
            }
        });
    }

    public List<NodeDefinition> getFinalNodes() {
        return map.getFinalNodes();
    }

    public void reset() {
        map.setDestination(null);
        map.setCurrentLocation(null);
    }

    public List<NodeDefinition> getShortestPath() {
        return map.getShortestPath();
    }

    public boolean hasReachedDestination() {
        return map.hasReachedDestination();
    }

    public EdgeDefinition getCurrentInstructions() {
        return map.getCurrentInstructions();
    }

    public EdgeDefinition updateNodeAndGetInstructions(String currentLocationId){
        return map.updateNodeAndGetInstructions(currentLocationId);
    }

    public void setDestination(NodeDefinition destination) {
        this.reset();
        map.setDestination(destination);
    }

}
