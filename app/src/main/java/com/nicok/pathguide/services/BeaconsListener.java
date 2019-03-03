package com.nicok.pathguide.services;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;

import java.util.List;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BeaconsListener extends Thread {

    private Context context;
    private TextToSpeech mTts;
    private boolean ttsEnabled = false;

    EstimoteCloudCredentials cloudCredentials;
    ProximityObserver.Handler observationHandler;

    public BeaconsListener(Context context) {
        cloudCredentials = new EstimoteCloudCredentials(context.getString(R.string.beacons_api_key), context.getString(R.string.beacons_api_app_token));
        this.context = context;

        this.mTts = new TextToSpeech(context, (int status) -> {
            ttsEnabled = status == TextToSpeech.SUCCESS;
        });
    }

    private void observeBeacons() {
        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .withBalancedPowerMode()
                .onError(throwable -> { return null; })
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
            .forTag(context.getString(R.string.beacons_tag))
            .inCustomRange(1.0)
            .onContextChange(proximityZoneContexts -> {
                if(proximityZoneContexts.size() != 1) {
                    return null;
                }

                this.changeLocation(proximityZoneContexts.iterator().next());
                return null;
            })
            .build();

        observationHandler = proximityObserver.startObserving(zone);

//        this.tryChangeLocation("c5f8eb0b3d42236c47b0d4c3eb048904");
    }

    public void changeLocation(ProximityZoneContext proximityZoneContext) {
        this.tryChangeLocation(proximityZoneContext.getDeviceId());

        try {
            if (PathFinder.hasReachedDestination()) {
                PathFinder.reset();

                this.speak(context.getString(R.string.arrived_message));
                Thread.sleep(4000); // Required to give app time to speak last message.
                this.shutDown();

                this.interrupt();
            }
        } catch (InterruptedException e) {
            // does nothing
        }
    }

    @Override
    public void run() {
        this.observeBeacons();
    }

    private void shutDown() {
        mTts.stop();
        mTts.shutdown();

        observationHandler.stop();
    }

    private void tryChangeLocation(String currentLocationId) {
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

        Bundle data = new Bundle();
        data.putSerializable(ExtrasParameterNames.NODES_ENTITY_DATA, shortestPath);
        data.putSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA, edge);
        Intent intent = new Intent(ExtrasParameterNames.CURRENT_LOCATION);
        intent.putExtras(data);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        this.speak(instructions);
    }

    private void speak(String message) {
        if (!ttsEnabled) {
            return;
        }

        mTts.speak(message, TextToSpeech.QUEUE_ADD, null, null);
    }

}
