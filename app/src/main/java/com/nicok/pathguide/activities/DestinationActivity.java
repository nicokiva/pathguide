package com.nicok.pathguide.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.nicok.pathguide.business_definitions.BaseEntityDefinition;
import com.nicok.pathguide.adapters.BaseEntityAdapter;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;

import java.util.List;
import java.util.stream.Collectors;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        Intent intent = getIntent();
        MapDefinition map = (MapDefinition) intent.getSerializableExtra("map");

        List<NodeDefinition> nodes = map.getAvailableNodes();

        ListView destinationsList = findViewById(R.id.available_destination_list);
        BaseEntityAdapter adapter = new BaseEntityAdapter(this, android.R.layout.simple_expandable_list_item_1, nodes.stream().collect(Collectors.toList()));
        destinationsList.setAdapter(adapter);

        destinationsList.setOnItemClickListener((parent, view, position, id) -> {
            BaseEntityDefinition itemValue = (BaseEntityDefinition)destinationsList.getItemAtPosition(position);
        });
    }
}
