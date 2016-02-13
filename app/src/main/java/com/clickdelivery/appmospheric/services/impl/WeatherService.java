package com.clickdelivery.appmospheric.services.impl;

import com.clickdelivery.appmospheric.clients.api.IWeatherClient;
import com.clickdelivery.appmospheric.model.City;
import com.clickdelivery.appmospheric.model.rest.CitiesResponse;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.List;

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

    /**
     * This method request a list of cities given a name
     *
     * @param city
     *         City to be queried
     *
     * @return List of matched names of cities
     */
    @Override
    public List<City> findCityByName(String city) {
        CitiesResponse response = weatherClient.getCities(city);
        return response == null ? null : response.getList();
    }

}
