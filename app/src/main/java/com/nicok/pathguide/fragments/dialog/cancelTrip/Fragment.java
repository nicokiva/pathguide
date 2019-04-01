package com.nicok.pathguide.fragments.dialog.cancelTrip;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.nicok.pathguide.activities.R;
import com.nicok.pathguide.fragments.dialog.DialogFragmentBase;

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
