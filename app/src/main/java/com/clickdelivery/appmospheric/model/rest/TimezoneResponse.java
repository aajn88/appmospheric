package com.clickdelivery.appmospheric.model.rest;

/**
 * The expected response from Google API
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class TimeZoneResponse {

    /** The Time Zone Id **/
    private String timeZoneId;

    /**
     * @return the timeZoneId
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * @return timeZoneId the timeZoneId to set
     */
    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
}
