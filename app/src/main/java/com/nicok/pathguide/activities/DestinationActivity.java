package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;

import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.viewHandlers.DestinationViewHandler;
import com.nicok.pathguide.viewHandlers.IViewHandler;
import com.nicok.pathguide.services.TripService;

public class DestinationActivity extends LoadableActivity implements DestinationViewHandler.DestinationViewHandlerListener {

    TripService tripService;
    IViewHandler viewHandler;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        this.tripService = TripService.getInstance(getApplicationContext());
        this.viewHandler = new DestinationViewHandler(this, getWindow().getDecorView().getRootView(), destination -> {
            tripService.setDestination(destination);

            Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
            DestinationActivity.this.startActivity(myIntent);

            this.finishLoading();
        })
        .setView(tripService.getFinalNodes());
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Map will be fetch and loaded at this point.
        this.startLoading();
        this.finishLoading();
    }


    @Override
    public void onSelectedItem(NodeDefinition destination) {
        tripService.setDestination(destination);

        Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
        DestinationActivity.this.startActivity(myIntent);
    }

}