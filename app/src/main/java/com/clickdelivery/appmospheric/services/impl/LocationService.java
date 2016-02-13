package com.clickdelivery.appmospheric.services.impl;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.clickdelivery.appmospheric.services.api.ILocationService;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.google.inject.Singleton;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the Location Service implementation where the Location is requested
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class LocationService implements ILocationService {

    /** This the delay time in case neither GPS nor Network provider have notified a Location **/
    public static final int MAX_TIMER_DELAY = 10000;

    /** Tag for Logs **/
    private static final String TAG_LOG = LocationService.class.getName();

    /** Timer Provider **/
    private static final String TIMER_PROVIDER = "Timer";

    /** Gps Provider **/
    private static final String GPS_PROVIDER = "GPS";

    /** Network Provider **/
    private static final String NETWORK_PROVIDER = "Network Provider";

    /** Timer for location request **/
    private Timer timer;

    /** The location manager **/
    private LocationManager locationManager;

    /** The result listener **/
    private LocationResultListener locationResult;
    /** Listener for GPS Location **/
    private LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            publishLocation(location, GPS_PROVIDER);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerNetwork);
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    /** Listener for Network Location **/
    private LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            timer.cancel();
            publishLocation(location, NETWORK_PROVIDER);
            locationManager.removeUpdates(this);
            locationManager.removeUpdates(locationListenerGps);
        }

        public void onProviderDisabled(String provider) {}

        public void onProviderEnabled(String provider) {}

        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
    /** Indicates whether GPS is enabled or not **/
    private boolean gpsEnabled = false;

    /** Indicates whether Network location is enabled or not **/
    private boolean networkEnabled = false;

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
    public boolean getLocation(Context context, LocationResultListener result) {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        locationResult = result;
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }

        //exceptions will be thrown if provider is not permitted.
        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An occurred while enabling GPS provider", e);
        }
        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An occurred while enabling Network Location provider", e);
        }

        //don't start listeners if no provider is enabled
        if (!gpsEnabled && !networkEnabled) {
            return false;
        }

        if (gpsEnabled) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                    locationListenerGps);
        }
        if (networkEnabled) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0,
                    locationListenerNetwork);
        }
        timer = new Timer();
        timer.schedule(new GetLastLocation(), MAX_TIMER_DELAY);
        return true;
    }

    /**
     * This method publishes the reached location and Logs who was the provider
     *
     * @param location
     *         Reached location
     * @param provider
     *         Provider
     */
    private void publishLocation(Location location, String provider) {
        Log.d(TAG_LOG, StringUtils.format("Location reached by %s: %s", provider, location));
        locationResult.gotLocation(location);
    }

    /**
     * This method should be called when the activity or fragment is onPause. This will avoid
     * crashes
     */
    @Override
    public void onPauseSyncState() {
        cancelTimer();
    }

    /**
     * This method cancels avery listener in GPS
     */
    public void cancelTimer() {
        timer.cancel();
        locationManager.removeUpdates(locationListenerGps);
        locationManager.removeUpdates(locationListenerNetwork);
    }

    /**
     * This timer is invoked when neither GPS provider nor Network provider have reached a location
     * update
     */
    private class GetLastLocation extends TimerTask {
        @Override
        public void run() {
            locationManager.removeUpdates(locationListenerGps);
            locationManager.removeUpdates(locationListenerNetwork);

            Location networkLocation = null;
            Location gpsLocation = null;
            if (gpsEnabled) {
                gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if (networkEnabled) {
                networkLocation = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            //if there are both values, then uses the latest one
            if (gpsLocation != null && networkLocation != null) {
                if (gpsLocation.getTime() > networkLocation.getTime()) {
                    publishLocation(gpsLocation, TIMER_PROVIDER);
                } else {
                    publishLocation(networkLocation, TIMER_PROVIDER);
                }
                return;
            }

            if (gpsLocation != null) {
                publishLocation(gpsLocation, TIMER_PROVIDER);
                return;
            }
            if (networkLocation != null) {
                publishLocation(networkLocation, TIMER_PROVIDER);
                return;
            }

            publishLocation(null, TIMER_PROVIDER);
        }

    }

}
