package com.clickdelivery.appmospheric.utils;

import android.util.Log;

import com.clickdelivery.appmospheric.clients.AppmosphericGsonHttpMessageConverter;
import com.google.gson.Gson;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * Restful Utils class. This class implements requesting methods as GET and POST
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio A. Jimenez N.</a>
 */
public final class RestUtils {

    /** Default connection timeout **/
    private static final int CONNECT_TIMEOUT = 5000;

    /** Default read timeout **/
    private static final int READ_TIMEOUT = 5000;

    /** Private default constructor * */
    private RestUtils() {}

    /**
     * Given a URL, a Class Type and a map with variables, this method performs GET method request
     * to the given url
     *
     * @param url
     *         The URL where GET request will be done.
     * @param responseClass
     *         Expected response class
     * @param <T>
     *         Expected return class
     *
     * @return Get method response
     */
    public static <T> T get(String url, Class<T> responseClass) {
        return get(url, responseClass, null);
    }

    /**
     * Given a URL, a Class Type and a map with variables, this method performs GET method request
     * to the given url
     *
     * @param url
     *         The URL where GET request will be done.
     * @param responseClass
     *         Expected response class
     * @param urlVars
     *         Url Variables
     * @param <T>
     *         Expected return class
     *
     * @return Get method response
     */
    public static <T> T get(String url, Class<T> responseClass, Map<String, ?> urlVars) {
        RestTemplate template = buildRestTemplate();

        url = addParametersToUrl(url, urlVars);

        Log.d("Performing Get", url);

        return template.getForObject(url, responseClass);
    }

    /**
     * This method adds given params to URL
     *
     * @param url
     *         The URL
     * @param urlVars
     *         params to add to URL
     *
     * @return URL with added parameters
     */
    private static String addParametersToUrl(String url, Map<String, ?> urlVars) {
        if (urlVars != null) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
            for (Map.Entry<String, ?> entry : urlVars.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
            url = builder.build().encode().toUriString();
        }

        return url;
    }

    /**
     * Given a URL, a request and a Class Type, this method performs POST method request to the
     * given url
     *
     * @param url
     *         The URL where POST request will be done.
     * @param request
     *         Request to be sent. Could be null
     * @param responseClass
     *         Expected response class
     * @param <T>
     *         Expected response class
     *
     * @return POST request response
     */
    public static <T> T post(String url, Object request, Class<T> responseClass) {
        return post(url, request, responseClass, null);
    }

    /**
     * Given a URL, a request, a Class Type and a map with variables, this method performs POST
     * method request to the given url
     *
     * @param url
     *         The URL where POST request will be done.
     * @param request
     *         Request to be sent. Could be null
     * @param responseClass
     *         Expected response class
     * @param urlVars
     *         url Variables. Could be null
     * @param <T>
     *         Expected response class
     *
     * @return POST request response
     */
    public static <T> T post(String url, Object request, Class<T> responseClass,
                             Map<String, ?> urlVars) {
        RestTemplate template = buildRestTemplate();

        url = addParametersToUrl(url, urlVars);

        Log.d("Performing Post", url);

        if (request != null) {
            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            Log.d("Post Body", jsonRequest);
        }

        return template.postForObject(url, request, responseClass);
    }

    /**
     * Builds a rest template by default
     *
     * @return Default RestTemplate
     */
    private static RestTemplate buildRestTemplate() {
        return buildRestTemplate(Boolean.FALSE);
    }

    /**
     * Get a configured RestTemplate
     *
     * @return configured restTemplate
     */
    private static RestTemplate buildRestTemplate(boolean multipartContent) {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) template
                .getRequestFactory();
        if (requestFactory != null) {
            requestFactory.setConnectTimeout(CONNECT_TIMEOUT);
            requestFactory.setReadTimeout(READ_TIMEOUT);
        }
        template.getMessageConverters().add(0, new AppmosphericGsonHttpMessageConverter());
        if (multipartContent) {
            template.getMessageConverters().add(new FormHttpMessageConverter());
        }
        return template;
    }
}
