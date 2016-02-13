package com.clickdelivery.appmospheric.services.api;

import android.content.Context;
import android.location.Location;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface ILocationService {

    /**
     * This method is called when the device's location is required. This works using a Listener
     * that is called when the best location is accurate
     *
     * @param context
     *         The app context
     * @param result
     *         Listener for location result
     *
     * @return True if was possible to start location requests
     */
    boolean getLocation(Context context, LocationResultListener result);

    /**
     * This method should be called when the activity or fragment is onPause. This will avoid
     * crashes
     */
    void onPauseSyncState();

    /**
     * This is the Location Result Listener which its method is called when a location update is
     * done
     */
    interface LocationResultListener {

        /**
         * This method is called when the best location is reached
         *
         * @param location
         *         Location reached
         */
        void gotLocation(Location location);
    }

}
