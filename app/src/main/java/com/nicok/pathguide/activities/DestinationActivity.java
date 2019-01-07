package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.nicok.pathguide.activities.constants.ExtrasParameterNames;
import com.nicok.pathguide.business_definitions.BaseEntityDefinition;
import com.nicok.pathguide.adapters.BaseEntityAdapter;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.fragments.SelectDestinationDialogFragment;

import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppCompatActivity implements SelectDestinationDialogFragment.SelectDestinationDialogListener {

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        int a = 1;
        // Calculates path
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        int a = 1;
        // Do nothing
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.destination_title);
        setContentView(R.layout.activity_destination);

        Intent intent = getIntent();
        MapDefinition map = (MapDefinition) intent.getSerializableExtra(ExtrasParameterNames.MAP);

        List<NodeDefinition> nodes = map.getFinalNodes();

        ListView destinationsList = findViewById(R.id.available_destination_list);
        BaseEntityAdapter adapter = new BaseEntityAdapter(this, android.R.layout.simple_list_item_1, nodes.stream().collect(Collectors.toList()));
        destinationsList.setAdapter(adapter);

        destinationsList.setOnItemClickListener((parent, view, position, id) -> {
            BaseEntityDefinition itemValue = (BaseEntityDefinition)destinationsList.getItemAtPosition(position);

            Bundle data = new Bundle();
            data.putString(ExtrasParameterNames.SELECTED_DESTINATION_NAME, itemValue.description);

            DialogFragment dialog = new SelectDestinationDialogFragment();
            dialog.setArguments(data);
            dialog.show(getSupportFragmentManager(), "SelectDestinationDialogFragment");
        });
    }
}
