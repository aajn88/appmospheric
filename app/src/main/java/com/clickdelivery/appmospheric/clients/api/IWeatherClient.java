package com.clickdelivery.appmospheric.clients.api;

import com.clickdelivery.appmospheric.model.rest.CitiesResponse;

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

}
