package com.nicok.pathguide.fragments;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.activities.constants.ExtrasParameterNames;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SelectDestinationDialogFragment extends DialogFragment {

    public interface SelectDestinationDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    SelectDestinationDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof SelectDestinationDialogListener)){
            return;
        }

        try {
            listener = (SelectDestinationDialogListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SelectDestinationDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String destinationName = getArguments().getString(ExtrasParameterNames.SELECTED_DESTINATION_NAME);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.select_destination_dialog_fragment, null);
        TextView question = view.findViewById(R.id.select_destination_question);
        question.setText(getResources().getString(R.string.select_destination_dialog).replace("{{destination}}", destinationName));

        builder.setView(view)
            .setPositiveButton(android.R.string.yes, (dialog, id) -> {
                listener.onDialogPositiveClick(SelectDestinationDialogFragment.this);
            })
            .setNegativeButton(android.R.string.no, (dialog, id) -> {
                listener.onDialogNegativeClick(SelectDestinationDialogFragment.this);
            });

        return builder.create();
    }

}
