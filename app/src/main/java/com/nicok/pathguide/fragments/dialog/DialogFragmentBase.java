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

    public DialogFragmentBase setListener(DialogFragmentBaseListener listener) {
        this.listener = listener;

        return this;
    }

}
