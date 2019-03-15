package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.adapters.DestinationRowAdapter;
import com.nicok.pathguide.fragments.dialog.selectDestinationDialog.Fragment;

import java.io.Serializable;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppCompatActivity implements Fragment.DialogFragmentBaseListener{

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
        DestinationRowAdapter adapter = new DestinationRowAdapter(this, android.R.layout.simple_list_item_1, nodes);
        destinationsList.setAdapter(adapter);
        destinationsList.setOnItemClickListener(this::onItemClickListener);
    }

    private void onItemClickListener(AdapterView<?> parent, View view, int position, long id) {
        NodeDefinition itemValue = (NodeDefinition)destinationsList.getItemAtPosition(position);

        Bundle data = new Bundle();
        data.putString(ExtrasParameterNames.ENTITY_NAME, itemValue.description);
        data.putInt(ExtrasParameterNames.ENTITY_ICON, ((NodeDefinition) itemValue).getIcon());
        data.putSerializable(ExtrasParameterNames.ENTITY_DATA, (NodeDefinition) itemValue);

        DialogFragment dialog = new Fragment();
        dialog.setArguments(data);
        dialog.show(getSupportFragmentManager(), "SelectDestinationDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(Serializable destination) {
        if (destination == null) {
            return;
        }

        PathFinder.setDestination((NodeDefinition) destination);

        Intent myIntent = new Intent(DestinationActivity.this, CurrentLocationActivity.class);
        DestinationActivity.this.startActivity(myIntent);
    }

    @Override
    public void onDialogNegativeClick() { }

}