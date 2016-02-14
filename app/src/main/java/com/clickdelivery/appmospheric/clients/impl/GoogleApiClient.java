package com.clickdelivery.appmospheric.clients.impl;

import android.util.Log;

import com.clickdelivery.appmospheric.clients.api.IGoogleApiClient;
import com.clickdelivery.appmospheric.model.rest.TimeZoneResponse;
import com.clickdelivery.appmospheric.utils.RestUtils;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.google.inject.Singleton;

/**
 * This is the implementation of the {@link IGoogleApiClient} interface
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class GoogleApiClient implements IGoogleApiClient {

    /** Tag for Logs **/
    private static final String TAG_LOG = GoogleApiClient.class.getName();

    /**
     * Google API KEY
     *
     * Note: Just for this project I set the API key hard coded, normally I should put this API
     * Key into a hide file properties
     */
    private static final String API_KEY = "AIzaSyBbF98HgBMUOflr8o7NH55fIcAfmc3AAUI";

    /** Google Maps Apis URL **/
    private static final String GOOGLE_APIS_URL = "https://maps.googleapis.com/maps/api";

    /** Time Zone URL **/
    private static final String TIME_ZONE_URL =
            GOOGLE_APIS_URL + "/timezone/json?location=%s,%s&timestamp=1331161200&key=" + API_KEY;

    /**
     * This method requests the time zone by location
     *
     * @param lat
     *         Latitude
     * @param lng
     *         Longitude
     *
     * @return The timezone, if an error occurs, then null is returned
     */
    @Override
    public TimeZoneResponse getTimeZoneByLocation(double lat, double lng) {
        TimeZoneResponse response = null;

        try {
            response = RestUtils.get(StringUtils.format(TIME_ZONE_URL, lat, lng), TimeZoneResponse.class);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An error has occurred while querying the cities", e);
        }

        return response;
    }
}
