package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.business_definitions.BaseEntityDefinition;
import com.nicok.pathguide.adapters.NodeEntityAdapter;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.fragments.SelectDestinationDialogFragment;

import java.util.List;
import java.util.stream.Collectors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppPathGuideActivity implements SelectDestinationDialogFragment.SelectDestinationDialogListener {

    @Override
    protected void onServiceLoaded() {
        Intent intent = getIntent();
        MapDefinition map = getService().getMap();

        List<NodeDefinition> nodes = map.getFinalNodes();

        ListView destinationsList = findViewById(R.id.available_destination_list);
        NodeEntityAdapter adapter = new NodeEntityAdapter(this, android.R.layout.simple_list_item_1, nodes.stream().collect(Collectors.toList()));
        destinationsList.setAdapter(adapter);

        destinationsList.setOnItemClickListener((parent, view, position, id) -> {
            BaseEntityDefinition itemValue = (NodeDefinition)destinationsList.getItemAtPosition(position);

            Bundle data = new Bundle();
            data.putString(ExtrasParameterNames.SELECTED_DESTINATION_NAME, itemValue.description);
            data.putInt(ExtrasParameterNames.SELECTED_DESTINATION_ICON, ((NodeDefinition) itemValue).getIcon());

            DialogFragment dialog = new SelectDestinationDialogFragment();
            dialog.setArguments(data);
            dialog.show(getSupportFragmentManager(), "SelectDestinationDialogFragment");
        });
    }

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
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.destination_title);
        setContentView(R.layout.activity_destination);

        super.startServiceAndBind();
    }
}
