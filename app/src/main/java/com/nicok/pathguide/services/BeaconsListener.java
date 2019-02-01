package com.nicok.pathguide.services;

import android.content.Context;

import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.business_logic.PathFinder;

import java.nio.file.Path;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BeaconsListener extends Thread {

    private Context context;

    public BeaconsListener(Context context) {
        this.context = context;
    }

    @Override
    public void run() {
        listen();
    }

    private void listen() {
        while(!PathFinder.hasReachedDestination()) {
            try {
                String currentLocationId = tryGetCurrentNode();
                if (currentLocationId != null) {
                    this.tryChangeLocation(currentLocationId);
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // does nothing
            }
        }
        // listen to beacons

    }

    private String tryGetCurrentNode() {
        /*
        * if something detected then returns new, else returns null
        */

        return "f001102001";
    }

    private void tryChangeLocation(String currentLocationId) {
        NodeDefinition currentLocation = PathFinder.getNodeById(currentLocationId);

        boolean hasChangedLocation = PathFinder.setCurrentLocationAndGetIfChanged(currentLocation);
        if (hasChangedLocation) {
            PathFinder.getNextPath();
        }
    }

        /*
            LocalBroadcastManager.getInstance(context).sendBroadcast(); --> https://stackoverflow.com/questions/30629071/sending-a-simple-message-from-service-to-activity
            mTts = new TextToSpeech(getApplicationContext(), (int status) -> {
                mTts.setLanguage(new Locale("es_AR"));
                mTts.speak("Qué andás haciendo? Y por qué estás haciendo eso?", TextToSpeech.QUEUE_FLUSH, null, null);
            });

            mTts.shutdown();
        */

}
