package com.nicok.pathguide.businessLogic;

import android.content.Context;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.services.MapService;

import java.util.List;
import java.util.Optional;

public class PathFinder {

    private static PathFinder _instance = null;
    public static PathFinder getInstance(Context context) {
        if (_instance == null) {
            _instance = new PathFinder(context);
        }

        return _instance;
    }

    private PathFinder(Context context) {
        this.mapService = MapService.getInstance(context);
    }

    private MapDefinition map = null;
    private MapService mapService;


    public interface LoadMapServiceListener {
        void onSuccess(MapDefinition map);
        void onFail(Exception e);
    }

    private void setMap(MapDefinition map) {
        this.map = map;
        this.map.setupEntities();
    }

    // TODO: This is a hack to allow infinite beacons and should be removed when project is done.
    public void reloadMapAndSet(LoadMapServiceListener listener) {
        NodeDefinition currentLocation = map.getCurrentLocation();
        NodeDefinition destination = map.getDestination();

        this.loadMapAndSet(new LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                map.setDestination(map.getNodeByTag(destination.tag));
                map.updateCurrentLocation(currentLocation.id);
                map.getInstructionsTo(currentLocation.id);

                listener.onSuccess(map);
            }

            @Override
            public void onFail(Exception e) {
                listener.onFail(e);
            }
        });

    }

    public void loadMapAndSet(LoadMapServiceListener listener) {
        this.loadMap(new PathFinder.LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                setMap(map);

                listener.onSuccess(map);
            }

            @Override
            public void onFail(Exception e) {
                listener.onFail(e);
            }
        });
    }

    public void loadMap(LoadMapServiceListener listener) {
        this.mapService.load(newMap -> {
            if (newMap.isPresent()) {
                listener.onSuccess(newMap.get());
            } else {
                listener.onSuccess(map);
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
        return map.getInstructionsTo(currentLocationId);
    }

    public void updateCurrentLocation(String currentLocationId) {
        map.updateCurrentLocation(currentLocationId);
    }

    public void setDestination(NodeDefinition destination) {
        this.reset();
        map.setDestination(destination);
    }

}
