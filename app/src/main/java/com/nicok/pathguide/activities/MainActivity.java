package com.nicok.pathguide.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import com.nicok.pathguide.constants.ExtrasParameterNames;
import com.nicok.pathguide.services.BeaconService;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppPathGuideActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        super.startServiceAndBind();
    }

    @Override
    protected void onServiceLoaded() {
        if (!getService().loadMap()) {
            return;
        }

        unBindService();
        Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
        MainActivity.this.startActivity(myIntent);
    }
}
