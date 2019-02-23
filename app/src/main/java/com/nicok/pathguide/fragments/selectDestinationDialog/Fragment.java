package com.nicok.pathguide.fragments.selectDestinationDialog;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.constants.ExtrasParameterNames;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class Fragment extends DialogFragment {

    public interface SelectDestinationDialogListener {
        void onDialogPositiveClick(Serializable entityData);
        void onDialogNegativeClick(Serializable entityData);
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
        String entityName = getArguments().getString(ExtrasParameterNames.ENTITY_NAME);
        Integer entityIcon = getArguments().getInt(ExtrasParameterNames.ENTITY_ICON);

        Serializable entityData = getArguments().getSerializable(ExtrasParameterNames.ENTITY_DATA);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.select_destination_dialog_fragment, null);
        TextView question = view.findViewById(R.id.tv_select_destination_question);
        question.setText(getResources().getString(R.string.select_destination_dialog).replace("{{destination}}", entityName));
        ((ImageView)view.findViewById(R.id.img_icon)).setImageResource(entityIcon);

        builder.setView(view)
            .setPositiveButton(android.R.string.yes, (dialog, id) -> {
                listener.onDialogPositiveClick(entityData);
            })
            .setNegativeButton(android.R.string.no, (dialog, id) -> {
                listener.onDialogNegativeClick(entityData);
            });

        return builder.create();
    }

}
