package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;

import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.viewHandlers.DestinationViewHandler;
import com.nicok.pathguide.viewHandlers.IViewHandler;
import com.nicok.pathguide.fragments.dialog.selectDestinationDialog.Fragment;
import com.nicok.pathguide.services.TripService;

import java.io.Serializable;

import androidx.appcompat.app.AppCompatActivity;

public class DestinationActivity extends AppCompatActivity implements DestinationViewHandler.DestinationViewHandlerListener {

    TripService tripService;
    IViewHandler viewHandler;

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.destination_title);
        setContentView(R.layout.activity_destination);

        this.tripService = TripService.getInstance(getApplicationContext());
        this.viewHandler = new DestinationViewHandler(this, getWindow().getDecorView().getRootView(), destination -> {
            tripService.setDestination(destination);

            Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
            DestinationActivity.this.startActivity(myIntent);
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