package com.nicok.pathguide.services;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class SpeakService {

    private TextToSpeech mTts;
    private boolean ttsEnabled = false;

    public SpeakService(Context context) {
        this.mTts = new TextToSpeech(context, (int status) -> {
            ttsEnabled = status == TextToSpeech.SUCCESS;
        });
    }

    public void shutdown() {
        mTts.stop();
        mTts.shutdown();

        this.mTts = null;
        _instance = null;
    }

    public void speak(String message) {
        if (!ttsEnabled) {
            return;
        }

        mTts.speak(message, TextToSpeech.QUEUE_ADD, null, null);
    }


    private static SpeakService _instance = null;
    public static SpeakService getInstance(Context context) {
        if (_instance == null) {
            _instance = new SpeakService(context);
        }

        return _instance;
    }

    public static SpeakService getInstance() throws Exception {
        if (_instance == null) {
            throw new Exception("SpeakService has not been initialized.");
        }

        return _instance;
    }

}
