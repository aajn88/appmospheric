package com.clickdelivery.appmospheric.clients.impl;

import android.util.Log;

import com.clickdelivery.appmospheric.clients.api.IWeatherClient;
import com.clickdelivery.appmospheric.model.rest.CitiesResponse;
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

    /** Tag Logs **/
    private static final String TAG_LOG = WeatherClient.class.getName();

    /** Url to query cities **/
    private static final String URL_CITIES = "http://api.openweathermap.org/data/2" +
            ".5/find?type=like&q=%s&cnt=20&appid=44db6a862fba0b067b1930da0d769e98";

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

}
