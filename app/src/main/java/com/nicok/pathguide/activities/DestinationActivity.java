package com.nicok.pathguide.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.adapters.NodeEntityAdapter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppPathGuideActivity implements com.nicok.pathguide.fragments.selectDestinationDialog.Fragment.SelectDestinationDialogListener {

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

        destinationsList = findViewById(R.id.available_destination_list);
        prepareDestinationsList(PathFinder.getMap().getFinalNodes());
    }

    private void prepareDestinationsList(List<NodeDefinition> nodes) {
        NodeEntityAdapter adapter = new NodeEntityAdapter(this, android.R.layout.simple_list_item_1, nodes.stream().collect(Collectors.toList()));
        destinationsList.setAdapter(adapter);
        destinationsList.setOnItemClickListener(this::onItemClickListener);
    }

    private void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        NodeDefinition itemValue = (NodeDefinition)destinationsList.getItemAtPosition(position);

        Bundle data = new Bundle();
        data.putString(ExtrasParameterNames.ENTITY_NAME, itemValue.description);
        data.putInt(ExtrasParameterNames.ENTITY_ICON, ((NodeDefinition) itemValue).getIcon());
        data.putSerializable(ExtrasParameterNames.ENTITY_DATA, (NodeDefinition) itemValue);

        DialogFragment dialog = new com.nicok.pathguide.fragments.selectDestinationDialog.Fragment();
        dialog.setArguments(data);
        dialog.show(getSupportFragmentManager(), "SelectDestinationDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(Serializable destination) {
        if (destination == null) {
            return;
        }

        PathFinder.setDestination((NodeDefinition) destination);
        startServiceAndBind();
    }

    @Override
    public void onDialogNegativeClick(Serializable entityData) { }


    @Override
    protected void onServiceLoaded() {
        //
    };

}