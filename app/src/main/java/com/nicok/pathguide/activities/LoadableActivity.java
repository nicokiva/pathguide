package com.nicok.pathguide.activities;

import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class LoadableActivity extends AppCompatActivity {

    private View spinner;

    protected void onStart() {
        super.onStart();
        spinner = this.findViewById(R.id.loading_spinner);

        spinner.setOnClickListener(null);
    }

    protected void startLoading() {
        this.spinner.setVisibility(View.VISIBLE);
    }

    protected void finishLoading() {
        this.spinner.setVisibility(View.GONE);
    }

}
