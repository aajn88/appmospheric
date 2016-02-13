package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

/**
 * The country information
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Country {

    /** Country Name **/
    @SerializedName("country")
    private String name;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}
