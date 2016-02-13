package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class BasicInfo {

    /** Coordinates **/
    @SerializedName("coord")
    private LatLng coordinates;

    /** The Weather main content **/
    @SerializedName("main")
    private WeatherMainContent mainContent;

    /** Wind information **/
    private Wind wind;

    /** Country **/
    @SerializedName("sys")
    private Country country;

    /** Clouds information **/
    private Clouds clouds;

    /** All weather information **/
    private List<Weather> weather;

    /**
     * @return the coordinates
     */
    public LatLng getCoordinates() {
        return coordinates;
    }

    /**
     * @return coordinates the coordinates to set
     */
    public void setCoordinates(LatLng coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @return the mainContent
     */
    public WeatherMainContent getMainContent() {
        return mainContent;
    }

    /**
     * @return mainContent the mainContent to set
     */
    public void setMainContent(WeatherMainContent mainContent) {
        this.mainContent = mainContent;
    }

    /**
     * @return the wind
     */
    public Wind getWind() {
        return wind;
    }

    /**
     * @return wind the wind to set
     */
    public void setWind(Wind wind) {
        this.wind = wind;
    }

    /**
     * @return the country
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @return country the country to set
     */
    public void setCountry(Country country) {
        this.country = country;
    }

    /**
     * @return the clouds
     */
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * @return clouds the clouds to set
     */
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * @return the weather
     */
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * @return weather the weather to set
     */
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}
