package com.nicok.pathguide.services;

import android.content.Context;

import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import com.nicok.pathguide.activities.R;

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
                .onError(throwable -> { return null; })
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

        // TODO: Remove when everything is ok
        tripService.tryChangeLocation("c5f8eb0b3d42236c47b0d4c3eb048904");
    }

    public void tryChangeLocation(ProximityZoneContext proximityZoneContext) {
        if (tripService.hasChangeLocationAndReachedToEnd(proximityZoneContext.getDeviceId())) {
            observationHandler.stop();

            this.interrupt();
        }
    }

    @Override
    public void run() {
        this.observeBeacons();
    }

}
