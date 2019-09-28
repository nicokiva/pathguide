package com.nicok.pathguide.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import com.nicok.pathguide.viewHandlers.CurrentLocationViewHandler;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.services.AppPathGuideService;
import com.nicok.pathguide.services.TripService;
import com.nicok.pathguide.constants.ExtrasParameterNames;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CurrentLocationActivity extends LoadableActivity {

    TripService tripService;
    private CurrentLocationViewHandler currentLocationViewHandler;

    private Intent serviceIntent;
    private AppPathGuideService service;
    private ServiceConnection serviceConnection;

    private BroadcastReceiver mChangeLocationMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            updateLocation(bundle);
        }
    };

    private BroadcastReceiver mFinishTripMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            goToDestinationsList();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);

        this.tripService = TripService.getInstance(getApplicationContext());
        this.currentLocationViewHandler = new CurrentLocationViewHandler(this, getWindow().getDecorView().getRootView(), new CurrentLocationViewHandler.CurrentLocationViewHandlerListener() {
            @Override
            public void onRepeatInstructionsClick() { onRepeatInstructions(); }

            @Override
            public void onCancelTripClick() {
                onCancelTrip();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        this.startLoading();
        new Handler().postDelayed(() -> finishLoading(), 2000);
    }

    private void goToDestinationsList() {
        Intent myIntent = new Intent(CurrentLocationActivity.this, DestinationActivity.class);
        CurrentLocationActivity.this.startActivity(myIntent);
    }

    /* ACTIONS */
    private void onRepeatInstructions() {
        this.tripService.repeatInstructions();
    }

    private void onCancelTrip() {
        this.tripService.cancel();
        this.cancelService();

        this.goToDestinationsList();
    }

    protected void updateLocation(Bundle bundle) {
        this.finishLoading();

        NodeDefinition[] nodes = (NodeDefinition[])bundle.getSerializable(ExtrasParameterNames.NODES_ENTITY_DATA);
        EdgeDefinition edge = (EdgeDefinition)bundle.getSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA);

        NodeDefinition node = nodes != null && nodes.length > 0 ? nodes[0] : null;

        this.currentLocationViewHandler.setView(node, edge);
    }
    /* /ACTIONS */



    /* SERVICE CONNECTOR */
    // TODO: Every service stuff should be relocated.
    private void startServiceAndBind() {
        serviceIntent = new Intent(getApplicationContext(), AppPathGuideService.class);

        startService(serviceIntent);
        bindService();
    }

    @Override
    protected void onStop () {
        super.onStop();

        if(this.tripService.isTripActive()) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mChangeLocationMessageReceiver);
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mFinishTripMessageReceiver);
        }

        cancelService();
    }

    private void unBindService() {
        unbindService(serviceConnection);
    }

    private boolean isInBackground = false;

    @Override
    protected void onPause() {
        super.onPause();

        this.isInBackground = true;

        if (this.tripService.isTripActive()) {
            unBindService();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.tripService.isTripActive()) {
            startServiceAndBind();

            LocalBroadcastManager.getInstance(this).registerReceiver(mChangeLocationMessageReceiver, new IntentFilter(ExtrasParameterNames.CURRENT_LOCATION));
            LocalBroadcastManager.getInstance(this).registerReceiver(mFinishTripMessageReceiver, new IntentFilter(ExtrasParameterNames.FINISH_TRIP));
        }

        if (this.isInBackground) {
            this.isInBackground = false;

            if(!this.tripService.isTripActive()) {
                goToDestinationsList();
            }
        }
    }

    private interface ServiceLifecycle {
        void onServiceConnected(AppPathGuideService service);
    }

    private void bindService() {
        bindService(null);
    }

    private void cancelService() {
        bindService(service -> service.stop());
    }

    private void bindService(@Nullable ServiceLifecycle serviceLifecycle) {

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder iBinder) {
                AppPathGuideService.BeaconServiceBinder binder = (AppPathGuideService.BeaconServiceBinder) iBinder;
                service = binder.getService();

                if (serviceLifecycle != null) {
                    serviceLifecycle.onServiceConnected(service);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                unBindService();
            }
        };

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
    /* /SERVICE CONNECTOR */
}
