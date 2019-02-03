package com.nicok.pathguide.services;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import com.nicok.pathguide.business_definitions.EdgeDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.business_logic.PathFinder;

import java.nio.file.Path;
import java.util.Locale;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BeaconsListener extends Thread {

    private Context context;
    private TextToSpeech mTts;
    private boolean ttsEnabled = false;

    public BeaconsListener(Context context) {
        this.context = context;

        this.mTts = new TextToSpeech(context, (int status) -> {
            ttsEnabled = status == TextToSpeech.SUCCESS;
        });
    }

    @Override
    public void run() {
        listen();
    }

    private void listen() {
        while(!PathFinder.hasReachedDestination()) {
            try {
                String currentLocationId = tryGetCurrentNode();
                if (currentLocationId == null) {
                    continue;
                }

                this.tryChangeLocation(currentLocationId);
                if (PathFinder.hasReachedDestination()) {
                    BeaconsListener.nextNodeId = null;

                    PathFinder.reset();
                    // Inform has arrived
                    this.speak("Has arribado a destino!");
                    this.shutDown();

                    this.interrupt();
                    return;
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // does nothing
            }
        }
    }

    private void shutDown() {
        mTts.stop();
        mTts.shutdown();
    }

    // Used to store next location until beacons are installed.
    private static String nextNodeId;

    private String tryGetCurrentNode() {
        /*
        * if something detected then returns new, else returns null
        */

        if (BeaconsListener.nextNodeId == null) {
            return "f001102001";
        }

        return BeaconsListener.nextNodeId;
    }

    private void tryChangeLocation(String currentLocationId) {
        EdgeDefinition edge = PathFinder.updateNodeAndGetInstructions(currentLocationId);

        if (edge == null) {
            return;
        }

        BeaconsListener.nextNodeId = edge.to;
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
