package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

/**
 * This class contains the weather main content such as the temperature, pressure, humidity, etc.
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class WeatherMainContent {

    /** Temperature **/
    @SerializedName("temp")
    private Float temperature;

    /** Min Temperature **/
    @SerializedName("temp_min")
    private Float minTemperature;

    /** Max Temperature **/
    @SerializedName("temp_max")
    private Float maxTemperature;

    /** Pressure **/
    private Float pressure;

    /** Sea level **/
    @SerializedName("sea_level")
    private Float seaLevel;

    /** Ground level **/
    @SerializedName("grnd_level")
    private Float groundLevel;

    /** Humidity percentage **/
    private Integer humidity;

    /**
     * @return the temperature
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     * @return temperature the temperature to set
     */
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    /**
     * @return the minTemperature
     */
    public Float getMinTemperature() {
        return minTemperature;
    }

    /**
     * @return minTemperature the minTemperature to set
     */
    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }

    /**
     * @return the maxTemperature
     */
    public Float getMaxTemperature() {
        return maxTemperature;
    }

    /**
     * @return maxTemperature the maxTemperature to set
     */
    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    /**
     * @return the pressure
     */
    public Float getPressure() {
        return pressure;
    }

    /**
     * @return pressure the pressure to set
     */
    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    /**
     * @return the seaLevel
     */
    public Float getSeaLevel() {
        return seaLevel;
    }

    /**
     * @return seaLevel the seaLevel to set
     */
    public void setSeaLevel(Float seaLevel) {
        this.seaLevel = seaLevel;
    }

    /**
     * @return the groundLevel
     */
    public Float getGroundLevel() {
        return groundLevel;
    }

    /**
     * @return groundLevel the groundLevel to set
     */
    public void setGroundLevel(Float groundLevel) {
        this.groundLevel = groundLevel;
    }

    /**
     * @return the humidity
     */
    public Integer getHumidity() {
        return humidity;
    }

    /**
     * @return humidity the humidity to set
     */
    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
