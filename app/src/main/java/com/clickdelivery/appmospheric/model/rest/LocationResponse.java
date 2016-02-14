package com.clickdelivery.appmospheric.model.rest;

import com.clickdelivery.appmospheric.model.WeatherInfo;
import com.google.gson.annotations.SerializedName;

/**
 * This is the expected Location Response
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class LocationResponse extends WeatherInfo {

    /** Response Code **/
    @SerializedName("cod")
    private Integer responseCode;

    /**
     * @return the responseCode
     */
    public Integer getResponseCode() {
        return responseCode;
    }

    /**
     * @return responseCode the responseCode to set
     */
    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }
}
