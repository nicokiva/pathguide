package com.nicok.pathguide.services;

import android.app.Activity;
import android.content.Context;

import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.nicok.pathguide.activities.R;
import java.util.List;

import kotlin.Unit;

public class BeaconsService extends Thread {

    private Context context;

    private EstimoteCloudCredentials cloudCredentials;
    private ProximityObserver.Handler observationHandler;
    private TripService tripService;
    private boolean detectedFirst = false;

    BeaconsService(Context context) {
        cloudCredentials = new EstimoteCloudCredentials(context.getString(R.string.beacons_api_key), context.getString(R.string.beacons_api_app_token));
        this.context = context;

        tripService = TripService.getInstance(context);

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if (detectedFirst == false) {
                        tripService.informStationNotDetected();
                    }
                }
            },
5000);
    }

    private void observeBeacons() {
        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .withBalancedPowerMode()
                .onError(throwable -> null)
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
            .forTag(context.getString(R.string.beacons_tag))
            .inCustomRange(0.5)
            .onContextChange(proximityZoneContexts -> {
                if(proximityZoneContexts.size() != 1) {
                    return null;
                }

                this.detectedFirst = true;
                String deviceId = proximityZoneContexts.iterator().next().getDeviceId();
                this.tryChangeLocation(deviceId);

                return null;
            })
            .build();

        observationHandler = proximityObserver.startObserving(zone);

        // TODO: Remove when using beacons
//        String deviceId = "48b341c52ec673e69750ea5fbf850c30";
//        this.tryChangeLocation(deviceId);
    }

    @Override
    public void run() {
        this.observeBeacons();
    }

    public void end() {
        observationHandler.stop();
    }


    private void tryChangeLocation(String deviceId) {
        tripService.tryChangeLocation(deviceId, () -> end());
    }

    public interface BeaconsServiceListener {
        Unit onRequirementsFulfilled();
        Unit onRequirementsMissing(List<? extends Requirement> requirements);
        Unit onError(Throwable error);
    }

    public static void isEnabled(Activity callerActivity, BeaconsServiceListener listener) {
        RequirementsWizardFactory.createEstimoteRequirementsWizard().fulfillRequirements(
                callerActivity,
                listener::onRequirementsFulfilled,
                listener::onRequirementsMissing, /* scanning won't work, handle this case in your app */
                listener::onError /* Oops, some error occurred, handle it here! */

        );
    }

}
