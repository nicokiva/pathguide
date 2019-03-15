package com.nicok.pathguide.fragments.dialog.selectDestinationDialog;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.dialog.DialogFragmentBase;

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

public class Fragment extends DialogFragmentBase {
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
                listener.onDialogNegativeClick();
            });

        return builder.create();
    }

}
