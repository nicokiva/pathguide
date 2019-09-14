package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.nicok.pathguide.services.BeaconsService;
import com.nicok.pathguide.services.TextToSpeechService;
import com.nicok.pathguide.services.http.HttpService;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;


public class MainActivity extends AppCompatActivity {

    private TextToSpeechService textToSpeechService;

    private final int MAIN_ACTIVITY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MAIN_ACTIVITY) {
            if (resultCode != TextToSpeechService.CHECK_VOICE_DATA_PASS) {

                // missing data, redirects to install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeechService.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                return;
            }

            this.textToSpeechService = TextToSpeechService.getInstance(this);

            this.checkBeaconsRequirements(new BeaconsService.BeaconsServiceListener() {
                @Override
                public Unit onRequirementsFulfilled() {
                    return checkInternetConnectivity();
                }

                @Override
                public Unit onRequirementsMissing(List<? extends Requirement> requirements) {
                    // TODO: Harcoded as AVD does not support bluetooth.
                    informMissingRequirement();
                    return checkInternetConnectivity();
                }

                @Override
                public Unit onError(Throwable error) {
                    informMissingRequirement();
                    return null;
                }
            });

        }
    }

    private void informMissingRequirement(){
        this.textToSpeechService.speak(this.getApplicationContext().getString(R.string.missing_requirements_message));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Unit goToDestinationsList(){
        Intent myIntent = new Intent(MainActivity.this, DestinationActivity.class);
        MainActivity.this.startActivity(myIntent);

        return null;
    }

    private Unit checkInternetConnectivity() {
        boolean isConnected = HttpService.isConnected(this.getApplicationContext());

        if (!isConnected) {
            informMissingRequirement();
            return null;
        }

        return goToDestinationsList();
    }

    private void checkBeaconsRequirements(BeaconsService.BeaconsServiceListener listener) {
        BeaconsService.isEnabled(this, listener);
    }

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
