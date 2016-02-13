package com.clickdelivery.appmospheric.config;

import com.clickdelivery.appmospheric.clients.api.IWeatherClient;
import com.clickdelivery.appmospheric.clients.impl.WeatherClient;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.clickdelivery.appmospheric.services.impl.WeatherService;
import com.google.inject.AbstractModule;

/**
 * This is the RoboGuice configuration class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio A. Jimenez N.</a>
 */
public class ConfigurationModules extends AbstractModule {

    @Override
    protected void configure() {
        bindServices();
        bindOthers();
    }

    /**
     * This method binds all services
     */
    private void bindServices() {
        bind(IWeatherService.class).to(WeatherService.class);
    }

    /**
     * This method binds other classes, such as clients and internal classes
     */
    private void bindOthers() {
        bind(IWeatherClient.class).to(WeatherClient.class);
    }

}
