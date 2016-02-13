package com.clickdelivery.appmospheric.controllers.main_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.model.BasicInfo;

import java.util.List;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class WeatherGCardsAdapter extends ArrayAdapter<BasicInfo> {

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
    public WeatherGCardsAdapter(Context context, List<BasicInfo> objects) {
        super(context, R.layout.weather_google_card, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.weather_google_card, parent, false);
            holder = new Holder();

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        return convertView;
    }

    /**
     * This is the Holder for Holder View pattern
     */
    private class Holder {

    }
}
