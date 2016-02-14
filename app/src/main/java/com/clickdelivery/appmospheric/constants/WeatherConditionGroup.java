package com.clickdelivery.appmospheric.constants;

import android.support.annotation.DrawableRes;

import com.clickdelivery.appmospheric.R;

import java.util.Calendar;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public enum WeatherConditionGroup {

    /** Thunderstorm Group **/
    THUNDERSTORM(2, R.drawable.ic_thunderstorm, R.drawable.ic_thunderstorm_night),

    /** Drizzle Group **/
    DRIZZLE(3, R.drawable.ic_clouds_rain, R.drawable.ic_drizzle_night),

    /** Rain Group **/
    RAIN(5, R.drawable.ic_clouds_rain, R.drawable.ic_drizzle_night),

    /** Snow Group **/
    SNOW(6, R.drawable.ic_snow, R.drawable.ic_snow_night),

    /** Clear Group **/
    CLEAR_SKY(8, R.drawable.ic_sunny, R.drawable.ic_moon),

    /** Clouds Group **/
    CLOUDS(8, R.drawable.ic_sunny_one_cloud, R.drawable.ic_moon_cloud);

    /** Group Id **/
    private final int id;

    /** The icon res **/
    @DrawableRes
    private final int iconRes;

    /** Alternative icon res, mostly used when is at night **/
    @DrawableRes
    private final int altIconRes;

    /**
     * Constructor
     *
     * @param id
     *         Group Id
     * @param iconRes
     *         Icon res Id
     */
    WeatherConditionGroup(int id,
                          @DrawableRes
                          int iconRes,
                          @DrawableRes
                          int altIconRes) {
        this.id = id;
        this.iconRes = iconRes;
        this.altIconRes = altIconRes;
    }

    /**
     * This method finds the specific resource for a group given the weather code
     *
     * @param code
     *         The weather code
     *
     * @return The drawable Id
     */
    @DrawableRes
    public static int findResourceByCode(int code, Calendar calendar) {
        int groupId = code / 100;
        for (WeatherConditionGroup group : values()) {
            if (groupId == group.id) {
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                return hour >= 6 && hour < 18 ? group.iconRes : group.altIconRes;
            }
        }
        return -1;
    }
}
