package com.nicok.pathguide.viewHandlers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.businessDefinitions.EdgeDefinition;
import com.nicok.pathguide.businessDefinitions.NodeDefinition;
import com.nicok.pathguide.fragments.dialog.DialogFragmentBase;
import com.nicok.pathguide.fragments.dialog.cancelTrip.Fragment;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class CurrentLocationViewHandler extends ViewHandlerBase implements IViewHandler {

    public interface CurrentLocationViewHandlerListener {
        void onRepeatInstructionsClick();
        void onCancelTripClick();
    }

    private CurrentLocationViewHandlerListener listener;

    TextView description;
    TextView extra;
    TextView instructions;
    Button cancel;
    Button repeatInstructions;
    ViewGroup currentLocationKnown;
    ViewGroup currentLocationUnknown;


    public CurrentLocationViewHandler(Context context, View view, CurrentLocationViewHandlerListener listener) {
        super(context);

        this.description = view.findViewById(R.id.tv_description);
        this.extra = view.findViewById(R.id.tv_extra);
        this.instructions = view.findViewById(R.id.tv_instructions);
        this.cancel = view.findViewById(R.id.bt_cancel);
        this.repeatInstructions = view.findViewById(R.id.bt_repeat_instructions);

        this.currentLocationKnown = view.findViewById(R.id.activity_current_location_known);
        this.currentLocationUnknown = view.findViewById(R.id.activity_current_location_unknown);

        this.listener = listener;

        // This button is shared between known and unknown
        this.cancel.setOnClickListener(v -> onCancelTripClick());
    }

    private void onCancelTripClick() {
        DialogFragment dialog = new Fragment()
            .setListener(new DialogFragmentBase.DialogFragmentBaseListener() {
                @Override
                public void onDialogPositiveClick(@Nullable Serializable entityData) {
                    listener.onCancelTripClick();
                }

                @Override
                public void onDialogNegativeClick() { }
            });
            dialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "cancelDialogFragment");
    }

    public IViewHandler setView(NodeDefinition node, EdgeDefinition edge) {
        if (node == null || edge == null) {
            this.currentLocationKnown.setVisibility(View.GONE);
            this.currentLocationUnknown.setVisibility(View.VISIBLE);

            return this;
        }

        this.description.setText(node.getDescription());
        this.extra.setText(node.getExtra());
        this.instructions.setText(edge.getInstructions());

        this.repeatInstructions.setOnClickListener(v -> listener.onRepeatInstructionsClick());

        this.currentLocationKnown.setVisibility(View.VISIBLE);
        this.currentLocationUnknown.setVisibility(View.GONE);

        return this;
    }
}
