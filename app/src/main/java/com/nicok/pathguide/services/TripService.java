package com.nicok.pathguide.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.activities.R;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class TripService {

    private Context context;
    TextToSpeechService textToSpeechService;

    public TripService(Context context) {
        this.context = context;

        this.textToSpeechService = TextToSpeechService.getInstance(context);
    }

    private static TripService _instance = null;
    public static TripService getInstance(Context context) {
        if (_instance == null) {
            _instance = new TripService(context);
        }

        return _instance;
    }

    public List<NodeDefinition> getFinalNodes() {
        return PathFinder.getFinalNodes();
    }

    public void setDestination(NodeDefinition destination) {
        PathFinder.setDestination(destination);
    }

    public void finish() {
        this.textToSpeechService.speak(context.getString(R.string.arrived_message));
        try {
            Thread.sleep(4000); // Required to give app time to speak last message.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.cancel();
    }

    public boolean hasChangeLocationAndReachedToEnd(String deviceId) {
        this.tryChangeLocation(deviceId);
        boolean hasReachedToEnd = PathFinder.hasReachedDestination();

        if (hasReachedToEnd) {
            this.finish();
        }

        return hasReachedToEnd;
    }

    public void cancel() {
        PathFinder.reset();

        try {
            this.textToSpeechService.shutdown();
        } catch (Exception e) {

        }
    }

    public void repeatInstructions() {
        this.textToSpeechService.speak(PathFinder.getCurrentInstructions().instructions);
    }

    public void tryChangeLocation(String currentLocationId) {
        EdgeDefinition edge = PathFinder.updateNodeAndGetInstructions(currentLocationId);
        List<NodeDefinition> shortestPath = PathFinder.getShortestPath();

        NodeDefinition[] itemsArray = new NodeDefinition[shortestPath.size()];
        itemsArray = shortestPath.toArray(itemsArray);

        if (edge == null) {
            return;
        }

        this.inform(itemsArray, edge);
    }

    private void inform(NodeDefinition[] shortestPath, EdgeDefinition edge) {
        String instructions = edge.getInstructions();

        this.showCurrentLocation(shortestPath, edge);

        this.textToSpeechService.speak(instructions);
    }

    private void showCurrentLocation(NodeDefinition[] shortestPath, EdgeDefinition edge) {
        Bundle data = new Bundle();
        data.putSerializable(ExtrasParameterNames.NODES_ENTITY_DATA, shortestPath);
        data.putSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA, edge);
        Intent intent = new Intent(ExtrasParameterNames.CURRENT_LOCATION);
        intent.putExtras(data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
