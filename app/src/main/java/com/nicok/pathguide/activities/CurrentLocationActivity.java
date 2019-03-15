package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.dialog.cancelDialog.Fragment;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

public class CurrentLocationActivity extends AppPathGuideActivity implements com.nicok.pathguide.fragments.dialog.selectDestinationDialog.Fragment.DialogFragmentBaseListener {

    Toolbar mTopToolbar;

    TextView description;
    TextView extra;
    TextView instructions;
    Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_location);
        startServiceAndBind();

        this.description = findViewById(R.id.tv_description);
        this.extra = findViewById(R.id.tv_extra);
        this.instructions = findViewById(R.id.tv_instructions);
        this.cancel = findViewById(R.id.bt_cancel);
    }

    @Override
    protected void onMessageReceived(Bundle bundle) {
        NodeDefinition[] nodes = (NodeDefinition[])bundle.getSerializable(ExtrasParameterNames.NODES_ENTITY_DATA);
        EdgeDefinition edge = (EdgeDefinition)bundle.getSerializable(ExtrasParameterNames.EDGE_ENTITY_DATA);

        if (nodes == null || edge == null) {
            findViewById(R.id.activity_current_location_known).setVisibility(View.GONE);
            findViewById(R.id.activity_current_location_unknown).setVisibility(View.VISIBLE);

            return;
        }

        findViewById(R.id.activity_current_location_known).setVisibility(View.VISIBLE);
        findViewById(R.id.activity_current_location_unknown).setVisibility(View.GONE);


        this.setView(nodes[0], edge);
    }

    private void setView(NodeDefinition node, EdgeDefinition edge) {
        this.description.setText(node.getDescription());
        this.extra.setText(node.getExtra());
        this.instructions.setText(edge.getInstructions());

        cancel.setOnClickListener(v -> onTryCancelTrip());
    }

    private void onTryCancelTrip() {
        DialogFragment dialog = new Fragment();
        dialog.show(getSupportFragmentManager(), "cancelDialogFragment");
    }

    private void onCancelTrip() {
        PathFinder.reset();
        Intent myIntent = new Intent(CurrentLocationActivity.this, DestinationActivity.class);
        CurrentLocationActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onServiceLoaded() { }

    @Override
    public void onDialogPositiveClick(Serializable destination) {
        onCancelTrip();
    }

    @Override
    public void onDialogNegativeClick() { }
}