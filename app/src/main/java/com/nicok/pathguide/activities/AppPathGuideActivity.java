package com.nicok.pathguide.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.nicok.pathguide.services.BeaconService;

import androidx.appcompat.app.AppCompatActivity;

public abstract class AppPathGuideActivity extends AppCompatActivity {
    private Intent serviceIntent;
    private BeaconService service;
    private ServiceConnection serviceConnection;
    private boolean isServiceBound = false;

    protected abstract void onServiceLoaded();

    protected void startServiceAndBind() {
        serviceIntent = new Intent(getApplicationContext(), BeaconService.class);

        startService(serviceIntent);
        bindService();
    }

    protected void unBindService() {
        unbindService(serviceConnection);
    }

    protected BeaconService getService() {
        return service;
    }

    private void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {
                    BeaconService.BeaconServiceBinder binder = (BeaconService.BeaconServiceBinder) iBinder;
                    service = binder.getService();
                    isServiceBound = true;

                    onServiceLoaded();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


}
