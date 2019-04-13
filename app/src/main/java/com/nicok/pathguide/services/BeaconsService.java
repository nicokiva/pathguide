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
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.nicok.pathguide.activities.R;

import java.util.List;

import kotlin.Unit;

public class BeaconsService extends Thread {

    private Context context;

    EstimoteCloudCredentials cloudCredentials;
    ProximityObserver.Handler observationHandler;
    TripService tripService;

    public BeaconsService(Context context) {
        cloudCredentials = new EstimoteCloudCredentials(context.getString(R.string.beacons_api_key), context.getString(R.string.beacons_api_app_token));
        this.context = context;

        tripService = TripService.getInstance(context);
    }

    private void observeBeacons() {
        ProximityObserver proximityObserver = new ProximityObserverBuilder(context, cloudCredentials)
                .withBalancedPowerMode()
                .onError(throwable -> null)
                .build();

        ProximityZone zone = new ProximityZoneBuilder()
            .forTag(context.getString(R.string.beacons_tag))
            .inCustomRange(1.0)
            .onContextChange(proximityZoneContexts -> {
                if(proximityZoneContexts.size() != 1) {
                    return null;
                }

                this.tryChangeLocation(proximityZoneContexts.iterator().next());
                return null;
            })
            .build();

        observationHandler = proximityObserver.startObserving(zone);

        // TODO: Remove when using beacons
        tripService.hasChangeLocationAndReachedToEnd("c00c78cad7f2a002a15a4d95887ed813");
    }

    @Override
    public void run() {
        this.observeBeacons();
    }

    public void tryChangeLocation(ProximityZoneContext proximityZoneContext) {
        if (tripService.hasChangeLocationAndReachedToEnd(proximityZoneContext.getDeviceId())) {
            observationHandler.stop();

            this.interrupt();
        }
    }

    public interface BeaconsServiceListener {
        Unit onRequirementsFulfilled();
        Unit onRequirementsMissing(List<? extends Requirement> requirements);
        Unit onError(Throwable error);
    }


    public static void isEnabled(Activity callerActivity, BeaconsServiceListener listener) {
        RequirementsWizardFactory.createEstimoteRequirementsWizard().fulfillRequirements(
                callerActivity,
                () -> {
                    return listener.onRequirementsFulfilled();
                },
                (requirements) -> {
                    /* scanning won't work, handle this case in your app */
                    return listener.onRequirementsMissing(requirements);
                },

                (throwable) -> {
                    /* Oops, some error occurred, handle it here! */
                    return listener.onError(throwable);
                }
        );
    }

}
