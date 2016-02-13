package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

/**
 * Latitude and Longitude coordinates
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class LatLng {

    /** Latitude **/
    private double lat;

    /** Longitude **/
    @SerializedName("lon")
    private double lng;

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @return lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @return the lng
     */
    public double getLng() {
        return lng;
    }

    /**
     * @return lng the lng to set
     */
    public void setLng(double lng) {
        this.lng = lng;
    }
}
