package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Clouds {

    /** Clouds percentage **/
    @SerializedName("all")
    private Integer percentage;

    /**
     * @return the percentage
     */
    public Integer getPercentage() {
        return percentage;
    }

    /**
     * @return percentage the percentage to set
     */
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
