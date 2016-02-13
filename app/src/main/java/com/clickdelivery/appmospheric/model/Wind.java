package com.clickdelivery.appmospheric.model;

import com.google.gson.annotations.SerializedName;

/**
 * This is the representation of the Wind information
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class Wind {

    /** Wind speed **/
    private Float speed;

    /** Wind speed **/
    @SerializedName("deg")
    private Float degrees;

    /**
     * @return the speed
     */
    public Float getSpeed() {
        return speed;
    }

    /**
     * @return speed the speed to set
     */
    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    /**
     * @return the degrees
     */
    public Float getDegrees() {
        return degrees;
    }

    /**
     * @return degrees the degrees to set
     */
    public void setDegrees(Float degrees) {
        this.degrees = degrees;
    }
}
