package com.example.nicok.pathguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

import map.MapDefinition;
import reader.FileReader;
import java.io.IOException;


import map.Map;

import reader.IReader;
import serializer.SerializeWrapper;

import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private MapDefinition map = null;
    private IReader reader = new FileReader();
    private SerializeWrapper serializeWrapper = new SerializeWrapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = loadMap();

        TextView txtMapString = findViewById(R.id.txtMapString);
        try {
            txtMapString.setText(this.serializeWrapper.serialize(map));
        } catch (Exception e) {

        }
    }

    public MapDefinition loadMap() {
        try {
            String map = reader.read(getAssets().open("map.json"));
            return this.serializeWrapper.deserialize(map, MapDefinition.class);

        } catch (Exception e) {
            return null;
        }
    }


}
