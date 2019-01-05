package com.nicok.pathguide.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.helpers.reader.FileReader;


import com.nicok.pathguide.helpers.reader.IReader;
import com.nicok.pathguide.helpers.serializer.SerializeWrapper;


public class MainActivity extends AppCompatActivity {
    private MapDefinition map = null;
    private IReader reader = new FileReader();
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        holdToDisplayScreen();

        loadDataAndRedirectToDestinationsList();
    }

    private void holdToDisplayScreen() {
        /* This is used to display the Splash screen */
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loadDataAndRedirectToDestinationsList() {
        map = loadMap();

        Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
        myIntent.putExtra("map", map);
        MainActivity.this.startActivity(myIntent);
    }

    private MapDefinition loadMap() {
        try {
            String map = reader.readAsset("map.json", this);

            return this.serializeWrapper.deserialize(map, MapDefinition.class);
        } catch (Exception e) {
            return null;
        }
    }


}
