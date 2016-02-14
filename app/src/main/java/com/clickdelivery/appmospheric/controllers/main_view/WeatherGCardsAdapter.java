package com.clickdelivery.appmospheric.controllers.main_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.constants.WeatherConditionGroup;
import com.clickdelivery.appmospheric.model.Weather;
import com.clickdelivery.appmospheric.model.WeatherInfo;
import com.clickdelivery.appmospheric.model.WeatherMainContent;
import com.clickdelivery.appmospheric.utils.StringUtils;

import org.apache.commons.lang3.text.WordUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class WeatherGCardsAdapter extends ArrayAdapter<WeatherInfo> {

    /** Simple Date Format **/
    private static final SimpleDateFormat formatter = new SimpleDateFormat("MMM, HH:mm a");

    /** Separator **/
    private static final String SEPARATOR = ",";

    /** Space **/
    private static final String SPACE = " ";

    /** Difference between Kelvin and Celsius **/
    private static final int KELVIN_DIF = 273;

    /** The Layout Inflater **/
    private LayoutInflater mInflater;

    /**
     * Constructor
     *
     * @param context
     *         The current context.
     * @param objects
     *         The objects to represent in the ListView.
     */
    public WeatherGCardsAdapter(Context context, List<WeatherInfo> objects) {
        super(context, R.layout.weather_google_card, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.weather_google_card, parent, false);
            holder = new Holder();

            holder.cityName = (TextView) convertView.findViewById(R.id.city_name_rtv);
            holder.date = (TextView) convertView.findViewById(R.id.date_rtv);
            holder.weatherIcon = (ImageView) convertView.findViewById(R.id.weather_icon_iv);
            holder.temperature = (TextView) convertView.findViewById(R.id.temperature_rtv);
            holder.windSpeed = (TextView) convertView.findViewById(R.id.wind_speed_rtv);
            holder.humidity = (TextView) convertView.findViewById(R.id.humidity_rtv);
            holder.showMap = (TextView) convertView.findViewById(R.id.show_in_map_tv);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        WeatherInfo weatherInfo = getItem(position);

        holder.cityName.setText(weatherInfo.getName());

        Weather weather = getWeather(weatherInfo);

        setDate(holder, weather, weatherInfo);
        classifyIcon(holder, weather, weatherInfo);

        WeatherMainContent main = weatherInfo.getMainContent();
        holder.temperature.setText(Integer.toString((int) (main.getTemperature() - KELVIN_DIF)));

        String speed = getContext().getString(R.string.km_per_hour);
        holder.windSpeed.setText(StringUtils.format(speed, weatherInfo.getWind().getSpeed()));

        String humidity = getContext().getString(R.string.humidity);
        holder.humidity.setText(StringUtils.format(humidity, main.getHumidity()));

        return convertView;
    }

    /**
     * This method classifies the weather condition to the specific icon
     *
     * @param holder
     *         Holder View
     * @param weather
     *         Weather
     */
    private void classifyIcon(Holder holder, Weather weather, WeatherInfo weatherInfo) {
        if (weather == null) {
            return;
        }
        int drawableId = WeatherConditionGroup
                .findResourceByCode(weather.getId(), weatherInfo.getDate());
        holder.weatherIcon.setImageResource(drawableId != -1 ? drawableId : R.drawable.ic_unknown);
    }

    /**
     * This method extracts the weather data
     *
     * @param weatherInfo
     *         Weather Info
     *
     * @return The weather data
     */
    private Weather getWeather(WeatherInfo weatherInfo) {
        List<Weather> weathers = weatherInfo.getWeather();
        return weathers != null && !weathers.isEmpty() ? weathers.get(0) : null;
    }

    /**
     * This method sets the date format
     *
     * @param holder
     *         Holder View
     * @param weather
     *         Weather
     */
    private void setDate(Holder holder, Weather weather, WeatherInfo weatherInfo) {
        Calendar date = weatherInfo.getDate();
        formatter.setTimeZone(date.getTimeZone());
        StringBuilder finalDate = new StringBuilder(
                WordUtils.capitalizeFully(formatter.format(weatherInfo.getDate().getTime())));

        if (weather != null) {
            finalDate.append(SEPARATOR);
            finalDate.append(SPACE);
            String description = weather.getDescription();
            finalDate.append(description.substring(0, 1).toUpperCase());
            finalDate.append(description.substring(1));
        }

        holder.date.setText(finalDate.toString());
    }

    /**
     * This is the Holder for Holder View pattern
     */
    private class Holder {
        TextView cityName;
        TextView date;
        TextView temperature;
        ImageView weatherIcon;
        TextView windSpeed;
        TextView humidity;
        TextView showMap;
    }
}
