package com.nicok.pathguide.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.business_definitions.BaseEntityDefinition;
import com.nicok.pathguide.adapters.NodeEntityAdapter;
import com.nicok.pathguide.business_definitions.MapDefinition;
import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.fragments.SelectDestinationDialogFragment;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppPathGuideActivity implements SelectDestinationDialogFragment.SelectDestinationDialogListener {

    ListView destinationsList;

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

        destinationsList = findViewById(R.id.available_destination_list);
    }

    @Override
    protected void onServiceLoaded() {
        MapDefinition map = getService().getMap();

        prepareDestinationsList(map.getFinalNodes());

        // TODO: WILL REMOVE WHEN BEACONS INTEGRATED
        getService().setCurrentLocation(map.getFinalNodes().get(0));
    }

    private void prepareDestinationsList(List<NodeDefinition> nodes) {
        NodeEntityAdapter adapter = new NodeEntityAdapter(this, android.R.layout.simple_list_item_1, nodes.stream().collect(Collectors.toList()));
        destinationsList.setAdapter(adapter);
        destinationsList.setOnItemClickListener(this::onItemClickListener);
    }

    private void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        BaseEntityDefinition itemValue = (NodeDefinition)destinationsList.getItemAtPosition(position);

        Bundle data = new Bundle();
        data.putString(ExtrasParameterNames.ENTITY_NAME, itemValue.description);
        data.putInt(ExtrasParameterNames.ENTITY_ICON, ((NodeDefinition) itemValue).getIcon());
        data.putSerializable(ExtrasParameterNames.ENTITY_DATA, (NodeDefinition) itemValue);

        DialogFragment dialog = new SelectDestinationDialogFragment();
        dialog.setArguments(data);
        dialog.show(getSupportFragmentManager(), "SelectDestinationDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(Serializable destination) {
        if (destination == null) {
            return;
        }

        getService().setDestination((NodeDefinition) destination);
    }

    @Override
    public void onDialogNegativeClick(Serializable entityData) { }

}
