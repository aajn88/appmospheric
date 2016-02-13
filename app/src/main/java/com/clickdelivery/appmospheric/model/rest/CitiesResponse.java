package com.clickdelivery.appmospheric.model.rest;

import com.clickdelivery.appmospheric.model.City;

import java.util.List;

/**
 * Response when querying cities
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class CitiesResponse extends BasicResponse {

    /** Response message **/
    private String message;

    /** Count of elements **/
    private Integer count;

    /** List of cities **/
    private List<City> list;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * @return count the count to set
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * @return the list
     */
    public List<City> getList() {
        return list;
    }

    /**
     * @return list the list to set
     */
    public void setList(List<City> list) {
        this.list = list;
    }
}
