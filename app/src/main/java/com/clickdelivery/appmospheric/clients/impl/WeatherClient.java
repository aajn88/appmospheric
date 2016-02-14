package com.clickdelivery.appmospheric.clients.impl;

import android.util.Log;

import com.clickdelivery.appmospheric.clients.api.IWeatherClient;
import com.clickdelivery.appmospheric.model.rest.CitiesResponse;
import com.clickdelivery.appmospheric.model.rest.LocationResponse;
import com.clickdelivery.appmospheric.utils.RestUtils;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.google.inject.Singleton;

/**
 * This is the Weather client implementation of the interface {@link IWeatherClient}
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class WeatherClient implements IWeatherClient {

    /**
     * The API KEY
     * <p/>
     * Note: Just for this project I set the API key hard coded, normally I should put this API Key
     * into a hide file properties
     **/
    public static final String API_KEY = "&appid=44db6a862fba0b067b1930da0d769e98";

    /** Tag Logs **/
    private static final String TAG_LOG = WeatherClient.class.getName();

    /** The Basic Server Url **/
    private static final String SERVER_URL = "http://api.openweathermap.org/data/2.5";

    /** Url to query cities **/
    private static final String URL_CITIES = SERVER_URL + "/find?type=like&q=%s&cnt=20" + API_KEY;

    /** Url to query cities **/
    private static final String URL_LOCATION = SERVER_URL + "/weather?lat=%s&lon=%s" + API_KEY;

    /**
     * This method gets a List of cities
     *
     * @param query
     *         Text to be queried
     *
     * @return The cities response. If an error occurs, then null is returned
     */
    @Override
    public CitiesResponse getCities(String query) {
        CitiesResponse response = null;

        try {
            response = RestUtils.get(StringUtils.format(URL_CITIES, query), CitiesResponse.class);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An error has occurred while querying the cities", e);
        }

        return response;
    }

    /**
     * This method requests to the server the location weather given a lontidude and latitude
     *
     * @param lng
     *         Lotitude
     * @param lat
     *         Latitude
     *
     * @return The expected LocationResponse. Returns null in case of error
     */
    @Override
    public LocationResponse getLocationWeather(double lat, double lng) {
        LocationResponse response = null;

        try {
            response = RestUtils
                    .get(StringUtils.format(URL_LOCATION, lat, lng), LocationResponse.class);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An error has occurred while querying the cities", e);
        }

        return response;
    }

}
