package com.clickdelivery.appmospheric.services.impl;

import com.clickdelivery.appmospheric.clients.api.IGoogleApiClient;
import com.clickdelivery.appmospheric.clients.api.IWeatherClient;
import com.clickdelivery.appmospheric.constants.ResponseCode;
import com.clickdelivery.appmospheric.model.WeatherInfo;
import com.clickdelivery.appmospheric.model.rest.CitiesResponse;
import com.clickdelivery.appmospheric.model.rest.LocationResponse;
import com.clickdelivery.appmospheric.model.rest.TimeZoneResponse;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * This is the implementation of {@link IWeatherService} interface
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@Singleton
public class WeatherService implements IWeatherService {

    /** Weather client **/
    @Inject
    private IWeatherClient weatherClient;

    /** Google Maps API Client **/
    @Inject
    private IGoogleApiClient googleApiClient;

    /**
     * This method request a list of cities given a name
     *
     * @param city
     *         WeatherInfo to be queried
     *
     * @return List of matched names of cities
     */
    @Override
    public List<WeatherInfo> findCityByName(String city) {
        CitiesResponse response = weatherClient.getCities(city);
        return response == null ? null : response.getList();
    }

    /**
     * This method requests the Weather given a Latitude and Longitude
     *
     * @param lat
     *         Latitude
     * @param lng
     *         Longitude
     *
     * @return The resulting weather info. Returns null in case of error
     */
    public WeatherInfo getWeatherByLatLng(double lat, double lng) {
        LocationResponse response = weatherClient.getLocationWeather(lat, lng);
        if (response == null || !ResponseCode.SUCCESS_CODE.equals(response.getResponseCode())) {
            return null;
        }

        TimeZoneResponse timeZoneResponse = googleApiClient.getTimeZoneByLocation(lat, lng);
        if (timeZoneResponse != null && timeZoneResponse.getTimeZoneId() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeZone(TimeZone.getTimeZone(timeZoneResponse.getTimeZoneId()));
            response.setDate(calendar);
        }

        return response;
    }

}
