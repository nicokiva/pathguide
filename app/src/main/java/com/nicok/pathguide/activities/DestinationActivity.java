package com.nicok.pathguide.activities;

import android.os.Bundle;

import com.nicok.pathguide.business_definitions.NodeDefinition;
import com.nicok.pathguide.business_logic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.selectDestinationDialog.Fragment;
import com.nicok.pathguide.fragments.selectDestinationDialog.Fragment.SelectDestinationDialogListener;
import com.nicok.pathguide.fragments.selectDestinationList.Fragment.OnListFragmentInteractionListener;

import java.io.Serializable;

import androidx.fragment.app.DialogFragment;

public class DestinationActivity extends AppPathGuideActivity implements SelectDestinationDialogListener, OnListFragmentInteractionListener {

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.destination_title);
        setContentView(R.layout.activity_destination);
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

    @Override
    public void onListFragmentInteraction(NodeDefinition destination) {
        Bundle data = new Bundle();
        data.putString(ExtrasParameterNames.ENTITY_NAME, destination.description);
        data.putInt(ExtrasParameterNames.ENTITY_ICON, ((NodeDefinition) destination).getIcon());
        data.putSerializable(ExtrasParameterNames.ENTITY_DATA, (NodeDefinition) destination);

        DialogFragment dialog = new Fragment();
        dialog.setArguments(data);
        dialog.show(getSupportFragmentManager(), "Fragment");
    }

}
