package com.nicok.pathguide.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.activities.R;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TripService {

    private PathFinder pathFinder = null;
    private Context context;
    private TextToSpeechService textToSpeechService;
    private String currentLocationId;

    public TripService(Context context) {
        this.context = context;

        this.pathFinder = PathFinder.getInstance(context);
        this.textToSpeechService = TextToSpeechService.getInstance(context);
    }

    private static TripService _instance = null;
    public static TripService getInstance(Context context) {
        if (_instance == null) {
            _instance = new TripService(context);
        }

        return _instance;
    }

    public interface LoadMapServiceListener {
        void onSuccess(MapDefinition map);
        void onFail(Exception e);
    }

    public void loadMap(LoadMapServiceListener listener) {
        pathFinder.loadMap(new PathFinder.LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                listener.onSuccess(map);
            }

            @Override
            public void onFail(Exception e) {
                listener.onFail(e);
            }
        });
    }

    public List<NodeDefinition> getFinalNodes() {
        return pathFinder.getFinalNodes();
    }

    public void setDestination(NodeDefinition destination) {
        pathFinder.setDestination(destination);
    }

    public void finish() {
        this.textToSpeechService.speak(context.getString(R.string.arrived_message));
        _instance = null;

        try {
            Thread.sleep(4000); // Required to give app time to speak last message.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.cancel();
    }

    public boolean hasReachedToEnd(String deviceId) {
        boolean hasReachedToEnd = pathFinder.hasReachedDestination();

        if (hasReachedToEnd) {
            this.finish();
        }

        return hasReachedToEnd;
    }

    public void cancel() {
        _instance = null;
        currentLocationId = null;
        pathFinder.reset();

        try {
            this.textToSpeechService.shutdown();
        } catch (Exception e) {

        }
    }

    public void repeatInstructions() {
        this.textToSpeechService.speak(pathFinder.getCurrentInstructions().instructions);
    }

    public void tryChangeLocation(String currentLocationId) {
        if (this.currentLocationId != null && currentLocationId.equals(this.currentLocationId)) {
            return;
        }

        this.currentLocationId = currentLocationId;
        EdgeDefinition edge = pathFinder.updateNodeAndGetInstructions(currentLocationId);
        List<NodeDefinition> shortestPath = pathFinder.getShortestPath();

        NodeDefinition[] itemsArray = new NodeDefinition[shortestPath.size()];
        itemsArray = shortestPath.toArray(itemsArray);

        if (edge == null) {
            this.informFinishTrip();

            return;
        }

        this.informChangeCurrentLocation(itemsArray, edge);
    }

    private void informChangeCurrentLocation(NodeDefinition[] shortestPath, EdgeDefinition edge) {
        String instructions = edge.getInstructions();

        this.sendMessageCurrentLocation(shortestPath, edge);

        this.textToSpeechService.speak(instructions);
    }

    private void informFinishTrip() {
        this.sendMessageFinishTrip();
    }

    private void sendMessageFinishTrip() {
        this.sendMessage(ExtrasParameterNames.FINISH_TRIP);
    }

    private void sendMessageCurrentLocation(NodeDefinition[] shortestPath, EdgeDefinition edge) {
        Bundle data = new Bundle();

        data.putSerializable(ExtrasParameterNames.NODES_ENTITY_DATA, shortestPath);
        data.putSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA, edge);

        this.sendMessage(ExtrasParameterNames.CURRENT_LOCATION, data);
    }


    private void sendMessage(String action) {
        sendMessage(action, null);
    }

    private void sendMessage(String action, @Nullable Bundle data) {
        Intent intent = new Intent(action);
        if (data != null) {
            intent.putExtras(data);
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
