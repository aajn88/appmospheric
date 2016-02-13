package com.clickdelivery.appmospheric.services.api;

import com.clickdelivery.appmospheric.model.City;

import java.util.List;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IWeatherService {

    /**
     * This method request a list of cities given a name
     *
     * @param city
     *         City to be queried
     *
     * @return List of matched names of cities
     */
    List<City> findCityByName(String city);

}
