package com.nicok.pathguide.services;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class TextToSpeechService {

    public static final String ACTION_CHECK_TTS_DATA = TextToSpeech.Engine.ACTION_CHECK_TTS_DATA;
    public static final String ACTION_INSTALL_TTS_DATA = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA;
    public static final int CHECK_VOICE_DATA_PASS = TextToSpeech.Engine.CHECK_VOICE_DATA_PASS;

    private TextToSpeech mTts;
    private boolean ttsEnabled = false;

    public TextToSpeechService(Context context) {
        this.mTts = new TextToSpeech(context, (int status) -> {
            ttsEnabled = status == TextToSpeech.SUCCESS;
        });
    }

    private static TextToSpeechService _instance = null;
    public static TextToSpeechService getInstance(Context context) {
        if (_instance == null) {
            _instance = new TextToSpeechService(context);
        }

        return _instance;
    }

    public static TextToSpeechService getInstance() throws Exception {
        if (_instance == null) {
            throw new Exception("TextToSpeechService has not been initialized.");
        }

        return _instance;
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
}
