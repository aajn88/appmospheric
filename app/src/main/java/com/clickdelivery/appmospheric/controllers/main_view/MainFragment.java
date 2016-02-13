package com.clickdelivery.appmospheric.controllers.main_view;


import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.model.BasicInfo;
import com.clickdelivery.appmospheric.services.api.ILocationService;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.clickdelivery.appmospheric.utils.AnimationsUtils;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.clickdelivery.appmospheric.views.progress_bars.ProgressWheel;
import com.google.inject.Inject;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass. Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends RoboFragment implements ILocationService.LocationResultListener {

    /** Tag for Logs **/
    private static final String TAG_LOG = MainFragment.class.getName();

    /** Loading Progress Wheel **/
    @InjectView(R.id.loading_pw)
    private ProgressWheel mLoadingPw;

    /** The Weather Cards ListView **/
    @InjectView(R.id.weather_cards_lv)
    private DynamicListView mWeatherCardsLv;

    /** Weather Service **/
    @Inject
    private IWeatherService weatherService;

    /** Location Service **/
    @Inject
    private ILocationService locationService;

    /** Default Constructor **/
    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enableProgressWheel(true);
        locationService.getLocation(getActivity(), this);
    }

    /**
     * This method is called when the best location is reached
     *
     * @param location
     *         Location reached
     */
    @Override
    public void gotLocation(Location location) {
        Log.d(TAG_LOG, StringUtils.format("The reached location is %s", location));

        Location[] locations = {null, null, null, null};
        new LoadWeatherInfoAsyncTask().execute(locations);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationService.onPauseSyncState();
    }

    /**
     * This method enables/disables the Progress Wheel and all its related views, such as, Weather
     * Google Cards ListView
     *
     * @param enable
     *         True enables the ProgressWheel. False disables it.
     */
    private void enableProgressWheel(boolean enable) {
        mWeatherCardsLv.setVisibility(enable ? View.GONE : View.VISIBLE);
        mLoadingPw.setVisibility(enable ? View.VISIBLE : View.GONE);

        if (mLoadingPw.isSpinning()) {
            mLoadingPw.stopSpinning();
        }

        if (enable) {
            mLoadingPw.spin();
        }

    }

    /**
     * This Async Task loads the weather information, from the Location lists passed as parameter
     */
    private class LoadWeatherInfoAsyncTask extends AsyncTask<Location, Void, List<BasicInfo>> {

        @Override
        protected List<BasicInfo> doInBackground(Location... params) {
            List<BasicInfo> weathers = new ArrayList<>();
            for (Location location : params) {
                weathers.add(null);
            }
            return weathers;
        }

        @Override
        protected void onPostExecute(List<BasicInfo> basicInfos) {
            super.onPostExecute(basicInfos);
            enableProgressWheel(false);

            WeatherGCardsAdapter adapter = new WeatherGCardsAdapter(getActivity(), basicInfos);
            AnimationAdapter animAdapter = AnimationsUtils
                    .animateAdapter(new Random().nextInt(5), adapter);
            animAdapter.setAbsListView(mWeatherCardsLv);
            mWeatherCardsLv.setAdapter(animAdapter);
        }
    }
}
