package com.clickdelivery.appmospheric.controllers.common;

/**
 * This interface is implemented to offer a way to register and unregister to filter callbacks
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public interface IFilterRegister {

    /**
     * This method registers a "subscriber" from filter actions
     *
     * @param subscriber
     *         The subscriber
     */
    void register(IFilterSubscriber subscriber);

    /**
     * This method unregister a subscriber from filter actions
     *
     * @param subscriber
     *         The subscriber to be unregistered
     */
    void unregister(IFilterSubscriber subscriber);

}
