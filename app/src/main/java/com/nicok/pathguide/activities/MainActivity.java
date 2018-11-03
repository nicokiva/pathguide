package com.nicok.pathguide.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nicok.pathguide.business_interfaces.MapDefinition;
import com.nicok.pathguide.reader.FileReader;


import com.nicok.pathguide.reader.IReader;
import com.nicok.pathguide.serializer.SerializeWrapper;

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
//            this.getApplicationContext()
            String map = reader.readAsset("map.json", this);

            return this.serializeWrapper.deserialize(map, MapDefinition.class);
        } catch (Exception e) {
            return null;
        }
    }


}
