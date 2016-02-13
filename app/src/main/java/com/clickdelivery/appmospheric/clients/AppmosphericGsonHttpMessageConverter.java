package com.clickdelivery.appmospheric.clients;

import android.util.Log;

import com.clickdelivery.appmospheric.utils.StringUtils;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is a custom Gson Http Message Converter for Server responses
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio A. Jimenez N.</a>
 */
public class AppmosphericGsonHttpMessageConverter extends GsonHttpMessageConverter {

    /** Logs Tag **/
    private static final String TAG_LOG = AppmosphericGsonHttpMessageConverter.class.getName();

    public AppmosphericGsonHttpMessageConverter() {
        List<MediaType> types = new ArrayList<>();
        types.add(new MediaType("text", "javascript", DEFAULT_CHARSET));
        types.add(new MediaType("application", "json", DEFAULT_CHARSET));
        types.add(new MediaType("application", "*+json", DEFAULT_CHARSET));
        super.setSupportedMediaTypes(types);
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws
            IOException, HttpMessageNotReadableException {
        inputMessage = logResponse(inputMessage);
        return super.read(type, contextClass, inputMessage);
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        inputMessage = logResponse(inputMessage);
        return super.readInternal(clazz, inputMessage);
    }

    protected HttpInputMessage logResponse(HttpInputMessage inputMessage) {
        HttpInputMessage httpInputMessage = null;
        try {
            HttpHeaders headers = inputMessage.getHeaders();
            Charset charset = getCharset(headers);
            StringWriter writer = new StringWriter();
            IOUtils.copy(inputMessage.getBody(), writer, charset);
            String value = writer.toString();
            Log.d(TAG_LOG, StringUtils.format("Body Response: %s", value));
            InputStream is = new ByteArrayInputStream(value.getBytes());
            httpInputMessage = new LookAppHttpInputMessage(is, headers);
        } catch (Exception e) {
            Log.e(TAG_LOG, "An error occurs while requesting to server", e);
        }
        return httpInputMessage;
    }

    /**
     * Gets Charset
     *
     * @param headers
     *         Http Headers
     *
     * @return The specified Charset
     */
    private Charset getCharset(HttpHeaders headers) {
        if (headers == null || headers.getContentType() == null ||
                headers.getContentType().getCharSet() == null) {
            return DEFAULT_CHARSET;
        }
        return headers.getContentType().getCharSet();
    }

    /**
     * This class holds the InputStream and Http helpers sent into a Response
     */
    private class LookAppHttpInputMessage implements HttpInputMessage {

        /** InputStream **/
        private InputStream mIs;

        /** Headers **/
        private HttpHeaders mHeaders;

        /**
         * Constructor
         *
         * @param mIs
         *         InputStream
         * @param mHeaders
         *         Http Headers
         */
        public LookAppHttpInputMessage(InputStream mIs, HttpHeaders mHeaders) {
            this.mIs = mIs;
            this.mHeaders = mHeaders;
        }

        /**
         * Return the body of the message as an input stream.
         *
         * @return the input stream body
         *
         * @throws IOException
         *         in case of I/O Errors
         */
        @Override
        public InputStream getBody() throws IOException {
            return mIs;
        }

        /**
         * Return the headers of this message.
         *
         * @return a corresponding HttpHeaders object
         */
        @Override
        public HttpHeaders getHeaders() {
            return mHeaders;
        }
    }
}
