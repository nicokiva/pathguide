package com.nicok.pathguide.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;

public class CurrentLocationAdapter {

    public interface CurrentLocationAdapterListener {
        void onRepeatInstructionsClick(EdgeDefinition edge);
        void onCancelTripClick();
    }

    protected CurrentLocationAdapterListener listener;

    TextView description;
    TextView extra;
    TextView instructions;
    Button cancel;
    Button repeatInstructions;
    ViewGroup currentLocationKnown;
    ViewGroup currentLocationUnknown;


    public CurrentLocationAdapter(View view, CurrentLocationAdapterListener listener) {
        this.description = view.findViewById(R.id.tv_description);
        this.extra = view.findViewById(R.id.tv_extra);
        this.instructions = view.findViewById(R.id.tv_instructions);
        this.cancel = view.findViewById(R.id.bt_cancel);
        this.repeatInstructions = view.findViewById(R.id.bt_repeat_instructions);

        this.currentLocationKnown = view.findViewById(R.id.activity_current_location_known);
        this.currentLocationUnknown = view.findViewById(R.id.activity_current_location_unknown);

        this.listener = listener;

        // This button is shared between known and unknown
        this.cancel.setOnClickListener(v -> listener.onCancelTripClick());
    }

    public void setView(NodeDefinition node, EdgeDefinition edge) {
        if (node == null || edge == null) {
            this.currentLocationKnown.setVisibility(View.GONE);
            this.currentLocationUnknown.setVisibility(View.VISIBLE);

            return;
        }

        this.description.setText(node.getDescription());
        this.extra.setText(node.getExtra());
        this.instructions.setText(edge.getInstructions());

        this.repeatInstructions.setOnClickListener(v -> listener.onRepeatInstructionsClick(edge));

        this.currentLocationKnown.setVisibility(View.VISIBLE);
        this.currentLocationUnknown.setVisibility(View.GONE);

    }
}
