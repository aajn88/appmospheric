package com.clickdelivery.appmospheric.services.api;

import com.clickdelivery.appmospheric.model.WeatherInfo;

import java.util.List;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IWeatherService {

    /**
     * This method request a list of cities given a name
     *
     * @param city
     *         WeatherInfo to be queried
     *
     * @return List of matched names of cities
     */
    List<WeatherInfo> findCityByName(String city);

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
    WeatherInfo getWeatherByLatLng(double lat, double lng);

}
