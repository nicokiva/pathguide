package com.nicok.pathguide.services;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.business_logic.PathFinder;

import java.util.ArrayList;
import java.util.List;

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
g                    if(proximityZoneContexts.size() != 1) {
                        return null;
                    }

                    this.changeLocation(proximityZoneContexts.iterator().next());
                    return null;
                })
                .build();

            observationHandler = proximityObserver.startObserving(zone);
    }

    public void changeLocation(ProximityZoneContext proximityZoneContext) {
        this.tryChangeLocation(proximityZoneContext.getDeviceId());

        try {
            if (PathFinder.hasReachedDestination()) {
                PathFinder.reset();
                // Inform has arrived
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

        if (edge == null) {
            return;
        }

        String instructions = edge.getInstructions();

        this.speak(instructions);
    }

    private void speak(String message) {
        if (!ttsEnabled) {
            return;
        }

        mTts.speak(message, TextToSpeech.QUEUE_ADD, null, null);
    }

        /*
            LocalBroadcastManager.getInstance(context).sendBroadcast(); --> https://stackoverflow.com/questions/30629071/sending-a-simple-message-from-service-to-activity
            mTts = new TextToSpeech(getApplicationContext(), (int status) -> {

                mTts.speak("Qué andás haciendo? Y por qué estás haciendo eso?", TextToSpeech.QUEUE_FLUSH, null, null);
            });

            mTts.shutdown();
        */

}
