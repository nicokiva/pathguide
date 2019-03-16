package com.nicok.pathguide.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

public class AppPathGuideService extends Service {

    private TextToSpeech mTts;

    public class BeaconServiceBinder extends Binder {
        public AppPathGuideService getService() {
            return AppPathGuideService.this;
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

    private BeaconsService listener;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        listener = new BeaconsService(this);
        listener.start();

        // will need to start beacons detection.
        return Service.START_NOT_STICKY;
    }


}
