package com.nicok.pathguide.fragments.dialog.cancelDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.fragments.dialog.DialogFragmentBase;

import java.io.Serializable;

import androidx.fragment.app.DialogFragment;

public class Fragment extends DialogFragmentBase {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.cancel_trip_dialog_fragment, null);
        builder.setView(view)
                .setPositiveButton(android.R.string.yes, (dialog, id) -> {
                    listener.onDialogPositiveClick(null);
                })
                .setNegativeButton(android.R.string.no, (dialog, id) -> {
                    listener.onDialogNegativeClick();
                });


        return builder.create();
    }

}
