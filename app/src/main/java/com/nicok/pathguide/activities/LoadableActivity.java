package com.nicok.pathguide.activities;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class LoadableActivity extends AppCompatActivity {

    private boolean isLoading = false;

    protected void startLoading() {
        this.findViewById(R.id.loading_spinner).setVisibility(View.VISIBLE);
    }

    protected void finishLoading() {
        this.findViewById(R.id.loading_spinner).setVisibility(View.GONE);
    }

}
