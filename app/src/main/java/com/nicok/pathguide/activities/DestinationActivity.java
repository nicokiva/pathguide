package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;

import com.nicok.pathguide.businessDefinitions.MapDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.viewHandlers.DestinationViewHandler;
import com.nicok.pathguide.viewHandlers.IViewHandler;
import com.nicok.pathguide.services.TripService;

import java.util.Timer;
import java.util.TimerTask;

public class DestinationActivity extends LoadableActivity implements DestinationViewHandler.DestinationViewHandlerListener {

    TripService tripService;
    IViewHandler viewHandler;

    @Override
    public void onBackPressed() {}

    @Override
    protected void finishLoading() {
        super.finishLoading();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        this.tripService = TripService.getInstance(getApplicationContext());
    }


    @Override
    protected void onStart() {
        super.onStart();

        this.startLoading();
        tripService.loadMap(new TripService.LoadMapServiceListener() {
            @Override
            public void onSuccess(MapDefinition map) {
                setView();

                finishLoading();
            }

            @Override
            public void onFail(Exception e) {
                finishLoading();
            }
        });
    }

    private void setView() {
        this.viewHandler = new DestinationViewHandler(this, getWindow().getDecorView().getRootView(), destination -> {
            tripService.setDestination(destination);

            Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
            DestinationActivity.this.startActivity(myIntent);

            this.finishLoading();
        })
        .setView(tripService.getFinalNodes());
    }


    @Override
    public void onSelectedItem(NodeDefinition destination) {
        tripService.setDestination(destination);

        Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
        DestinationActivity.this.startActivity(myIntent);
    }

}