package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.nicok.pathguide.businessLogic.PathFinder;
import com.nicok.pathguide.services.BeaconsService;
import com.nicok.pathguide.services.TextToSpeechService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;


public class MainActivity extends AppCompatActivity {

    private final int MAIN_ACTIVITY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_ACTIVITY) {
            if (resultCode != TextToSpeechService.CHECK_VOICE_DATA_PASS) {

                // missing data, redirects to install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeechService.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                return;

            }

            this.checkBeaconsRequirements();
        }
    }

    private Unit goToDestinationsList(){
        if (!PathFinder.loadMap(getApplicationContext())) {
            return null;
        }

        Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
        MainActivity.this.startActivity(myIntent);

        return null;
    }

    private void checkBeaconsRequirements() {
        BeaconsService.isEnabled(this, new BeaconsService.BeaconsServiceListener() {
            @Override
            public Unit onRequirementsFulfilled() {
                return goToDestinationsList();
            }

            @Override
            public Unit onRequirementsMissing(List<? extends Requirement> requirements) {
                return goToDestinationsList();
            }

            @Override
            public Unit onError(Throwable error) {
                return null;
            }
        });
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeechService.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MAIN_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
