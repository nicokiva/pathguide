package com.nicok.pathguide.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.services.BeaconService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public abstract class AppPathGuideActivity extends AppCompatActivity {
    private Intent serviceIntent;
    private BeaconService service;
    private ServiceConnection serviceConnection;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        onMessageReceived(bundle);
        }
    };

    protected abstract void onMessageReceived(Bundle bundle);
    protected abstract void onServiceLoaded();

    protected void startServiceAndBind() {
        serviceIntent = new Intent(getApplicationContext(), BeaconService.class);

        startService(serviceIntent);
        bindService();
    }

    @Override
    protected void onStop () {
        super.onStop();

        unBindService();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
    }

    protected void unBindService() {
        unbindService(serviceConnection);
    }

    protected BeaconService getService() {
        return service;
    }

    @Override
    protected void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ExtrasParameterNames.CURRENT_LOCATION));
    }

    public void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder iBinder) {
                    BeaconService.BeaconServiceBinder binder = (BeaconService.BeaconServiceBinder) iBinder;
                    service = binder.getService();

                    onServiceLoaded();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    unBindService();
                }
            };
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


}
