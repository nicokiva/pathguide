package com.nicok.pathguide.fragments.dialog;

import android.content.Context;

import java.io.Serializable;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public abstract class DialogFragmentBase extends DialogFragment {

    public interface DialogFragmentBaseListener {
        void onDialogPositiveClick(@Nullable Serializable entityData);
        void onDialogNegativeClick();
    }

    protected DialogFragmentBaseListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof DialogFragmentBaseListener)){
            return;
        }

        try {
            listener = (DialogFragmentBaseListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement SelectDestinationDialogListener");
        }
    }

}
