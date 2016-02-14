package com.clickdelivery.appmospheric.clients.api;

import com.clickdelivery.appmospheric.model.rest.TimeZoneResponse;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IGoogleApiClient {

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
    TimeZoneResponse getTimeZoneByLocation(double lat, double lng);

}
