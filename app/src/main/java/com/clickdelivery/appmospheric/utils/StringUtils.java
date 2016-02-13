package com.clickdelivery.appmospheric.utils;

/**
 * String Utils class
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class StringUtils {

    private static final String STRING_NULL = "null";

    /** Private constructor to avoid instanes **/
    private StringUtils() {}

    /**
     * This method simulates the {@code String#format()} but if the object is null, then null is
     * dislayed
     *
     * @param string
     *         Target String
     * @param params
     *         String parameters
     *
     * @return Formatted String
     */
    public static String format(String string, Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (params[i] == null) {
                params[i] = STRING_NULL;
            }
        }
        return String.format(string, params);
    }
}
