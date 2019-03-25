package com.nicok.pathguide.activities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nicok.pathguide.adapters.CurrentLocationAdapter;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.services.AppPathGuideService;
import com.nicok.pathguide.services.TripService;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.dialog.cancelDialog.Fragment;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class CurrentLocationActivity extends AppCompatActivity implements com.nicok.pathguide.fragments.dialog.selectDestinationDialog.Fragment.DialogFragmentBaseListener {

    TripService tripService;

    private CurrentLocationAdapter currentLocationAdapter;

    private Intent serviceIntent;
    private AppPathGuideService service;
    private ServiceConnection serviceConnection;

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        onMessageReceived(bundle);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        startServiceAndBind();

        this.tripService = TripService.getInstance(getApplicationContext());
        this.currentLocationAdapter = new CurrentLocationAdapter(getWindow().getDecorView().getRootView(), new CurrentLocationAdapter.CurrentLocationAdapterListener() {
            @Override
            public void onRepeatInstructionsClick(EdgeDefinition edge) {
                onRepeatInstructions(edge);
            }

            @Override
            public void onCancelTripClick() {
                onTryCancelTrip();
            }
        });
    }

    /* ACTIONS */
    private void onRepeatInstructions(EdgeDefinition edge) {
        this.tripService.repeatInstructions();
    }

    private void onTryCancelTrip() {
        DialogFragment dialog = new Fragment();
        dialog.show(getSupportFragmentManager(), "cancelDialogFragment");
    }

    private void onCancelTrip() {
        this.tripService.cancel();
        Intent myIntent = new Intent(CurrentLocationActivity.this, DestinationActivity.class);
        CurrentLocationActivity.this.startActivity(myIntent);
    }

    @Override
    public void onDialogPositiveClick(Serializable destination) {
        onCancelTrip();
    }

    @Override
    public void onDialogNegativeClick() { }
    /* /ACTIONS */



    /* SERVICE CONNECTOR */
    protected void startServiceAndBind() {
        serviceIntent = new Intent(getApplicationContext(), AppPathGuideService.class);

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
                    AppPathGuideService.BeaconServiceBinder binder = (AppPathGuideService.BeaconServiceBinder) iBinder;
                    service = binder.getService();
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    unBindService();
                }
            };
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    protected void onMessageReceived(Bundle bundle) {
        NodeDefinition[] nodes = (NodeDefinition[])bundle.getSerializable(ExtrasParameterNames.NODES_ENTITY_DATA);
        EdgeDefinition edge = (EdgeDefinition)bundle.getSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA);

        NodeDefinition node = nodes != null && nodes.length > 0 ? nodes[0] : null;

        this.currentLocationAdapter.setView(node, edge);
    }

    /* /SERVICE CONNECTOR */
}
