package com.clickdelivery.appmospheric.clients.api;

import com.clickdelivery.appmospheric.model.rest.CitiesResponse;
import com.clickdelivery.appmospheric.model.rest.LocationResponse;

/**
 * Esta es la Interface para la comunicación con el servidor de DBD Plus
 *
 * @author <a href="mailto:antonio-jimenez@accionplus.com">Antonio A. Jimenez N.</a>
 */
public interface IWeatherClient {

    /**
     * This method gets a List of cities
     *
     * @param query
     *         Text to be queried
     *
     * @return The cities response. If an error occurs, then null is returned
     */
    CitiesResponse getCities(String query);

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
    LocationResponse getLocationWeather(double lat, double lng);

}
