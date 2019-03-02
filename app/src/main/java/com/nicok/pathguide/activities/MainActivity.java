package com.nicok.pathguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.nicok.pathguide.businessLogic.PathFinder;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Unit;


public class MainActivity extends AppCompatActivity {

    private final int MAIN_ACTIVITY = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MAIN_ACTIVITY) {
            if (resultCode != TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {

                // missing data, redirects to install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
                return;

            }

            this.checkEstimoteRequirements();
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

    private void checkEstimoteRequirements () {
        RequirementsWizardFactory.createEstimoteRequirementsWizard().fulfillRequirements(
                this,
                () -> {
                    return this.goToDestinationsList();
                },
                (requirements) -> {
                    /* scanning won't work, handle this case in your app */
                    return this.goToDestinationsList();
//                    return null;
                },

                (throwable) -> {
                    /* Oops, some error occurred, handle it here! */
                    return null;
                }
            );
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, MAIN_ACTIVITY);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
