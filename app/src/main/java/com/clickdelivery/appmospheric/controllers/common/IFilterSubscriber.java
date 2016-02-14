package com.clickdelivery.appmospheric.controllers.common;

/**
 * The main goal of this interface is to offer a way to subscribe to filter feature performed by
 * this Activity.
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IFilterSubscriber {

    /**
     * This method will be called when the filter is changed
     *
     * @param s
     *         The new filter request
     */
    void filter(CharSequence s);

}
