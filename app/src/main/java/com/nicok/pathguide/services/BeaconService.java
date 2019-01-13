package com.nicok.pathguide.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;
import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;

public class BeaconService extends Service {

    private MapDefinition map = null;
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int a = 0;
            }
        });

        return START_STICKY;
    }

    public MapDefinition getMap () {
        return map;
    }

    public boolean loadMap() {
        try {
            String serializedMap = reader.readAsset("map.json", this);

            map = serializeWrapper.deserialize(serializedMap, MapDefinition.class);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    setDestination() {
//
//    }
//
//    private calcPath() {
//        activity.changeLocation(node)
//    }
//
//    getCurrentLocation() {
//
//    }

}
