package com.nicok.pathguide.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.business_definitions.Graph;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

public class BeaconService extends Service {

    private MapDefinition map = null;
    private NodeDefinition destination = null;
    private NodeDefinition currentLocation = null;

    private Graph graph;

    private IReader reader = new FileReader();
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    public class BeaconServiceBinder extends Binder {
        public BeaconService getService() {
            return BeaconService.this;
        }
    }

    private IBinder binder = new BeaconServiceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public MapDefinition getMap () {
        return map;
    }

    public boolean loadMap() {
        try {
            String serializedMap = reader.readAsset("map.json", this);

            map = serializeWrapper.deserialize(serializedMap, MapDefinition.class);
            map.setupEntities();

            graph = new Graph(map.getNodes(), map.getEdges());

            return map != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void setCurrentLocation(NodeDefinition currentLocation){
        this.currentLocation = currentLocation;
    }

    public void setDestination(NodeDefinition destination) {
        this.destination = destination;

        if (destination == null) {
            return;
        }

        this.calculateDinstancesFromLocation();
    }

    private void calculateDinstancesFromLocation() {
        if (destination == null || map == null || currentLocation == null) {
            return;
        }

        graph.calculateDistanceFrom(currentLocation);
    }

    private NodeDefinition getCurrentLocation() {
        return this.currentLocation;
    }

}
