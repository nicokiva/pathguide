package com.nicok.pathguide.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nicok.pathguide.business_interfaces.MapDefinition;
import com.nicok.pathguide.reader.FileReader;


import com.nicok.pathguide.reader.IReader;
import com.nicok.pathguide.serializer.SerializeWrapper;


public class MainActivity extends AppCompatActivity {
    private MapDefinition map = null;
    private IReader reader = new FileReader();
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = loadMap();

        Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
        myIntent.putExtra("map", map);
        MainActivity.this.startActivity(myIntent);
    }

    public MapDefinition loadMap() {
        try {
            String map = reader.readAsset("map.json", this);

            return this.serializeWrapper.deserialize(map, MapDefinition.class);
        } catch (Exception e) {
            return null;
        }
    }


}
