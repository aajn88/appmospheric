package com.clickdelivery.appmospheric.controllers.main_view;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.controllers.common.IFilterRegister;
import com.clickdelivery.appmospheric.controllers.common.IFilterSubscriber;
import com.clickdelivery.appmospheric.model.WeatherInfo;
import com.clickdelivery.appmospheric.services.api.ILocationService;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.clickdelivery.appmospheric.utils.AnimationsUtils;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.clickdelivery.appmospheric.utils.ViewUtils;
import com.clickdelivery.appmospheric.views.progress_bars.ProgressWheel;
import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.google.android.gms.maps.model.LatLng;
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
public class MainFragment extends RoboFragment
        implements ILocationService.LocationResultListener, IFilterSubscriber {

    /** Map Request ID **/
    public static final String RESULTING_LOCATION = "RESULTING_LOCATION";
    /** Map Request ID **/
    private static final int MAP_REQUEST_ID = 1;
    /** Tag for Logs **/
    private static final String TAG_LOG = MainFragment.class.getName();

    /** Loading Progress Wheel **/
    @InjectView(R.id.loading_pw)
    private ProgressWheel mLoadingPw;

    /** The Weather Cards ListView **/
    @InjectView(R.id.weather_cards_lv)
    private DynamicListView mWeatherCardsLv;

    /** Map Floation Action Button **/
    @InjectView(R.id.fab)
    private FloatingActionButton mMapFab;

    /** Weather Service **/
    @Inject
    private IWeatherService weatherService;

    /** Location Service **/
    @Inject
    private ILocationService locationService;

    /** Stored Locations **/
    private List<Location> mStoredLocations;

    /** Filter Register **/
    private IFilterRegister mFilterRegister;

    /** Weaher Adapter **/
    private WeatherGCardsAdapter mAdapter;

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
        if (!locationService.getLocation(getActivity(), this)) {
            ViewUtils.makeToast(getActivity(), R.string.location_error,
                    SuperToast.Duration.EXTRA_LONG, Style.RED).show();
            enableProgressWheel(false);
        }

        mMapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectLocationIntent = new Intent(getActivity(),
                        SelectLocationActivity.class);
                startActivityForResult(selectLocationIntent, MAP_REQUEST_ID);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mFilterRegister != null) {
            mFilterRegister.register(this);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFilterRegister) {
            mFilterRegister = ((IFilterRegister) context);
            mFilterRegister.register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mFilterRegister != null) {
            mFilterRegister.unregister(this);
        }
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

        if (mStoredLocations == null || mStoredLocations.isEmpty()) {
            mStoredLocations = getDefaultLocations();
            mStoredLocations.add(0, location);
        }
        Location[] locations = new Location[mStoredLocations.size()];
        new LoadWeatherInfoAsyncTask().execute(mStoredLocations.toArray(locations));
    }

    /**
     * This method gets defaults location to be shown
     *
     * @return List of default locations
     */
    private List<Location> getDefaultLocations() {
        List<Location> locations = new ArrayList<>();

        locations.add(buildLocation(6.2212876, -75.5760867));
        locations.add(buildLocation(3.45000, -76.53333));
        locations.add(buildLocation(12.583333, -81.7));
        locations.add(buildLocation(11.236111, -74.201667));

        return locations;
    }

    /**
     * This method builds a location instance given the latitude and longitude
     *
     * @param lat
     *         Latitude
     * @param lng
     *         Longitude
     *
     * @return The location instance
     */
    private Location buildLocation(double lat, double lng) {
        Location location = new Location((String) null);
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
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
        mMapFab.setVisibility(enable ? View.GONE : View.VISIBLE);
        mLoadingPw.setVisibility(enable ? View.VISIBLE : View.GONE);

        if (mLoadingPw.isSpinning()) {
            mLoadingPw.stopSpinning();
        }

        if (enable) {
            mLoadingPw.spin();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAP_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            enableProgressWheel(true);

            LatLng resultingLocation = data.getParcelableExtra(RESULTING_LOCATION);

            mStoredLocations
                    .add(0, buildLocation(resultingLocation.latitude, resultingLocation.longitude));

            Location[] locationsArr = new Location[mStoredLocations.size()];
            new LoadWeatherInfoAsyncTask().execute(mStoredLocations.toArray(locationsArr));
        }
    }

    /**
     * This method will be called when the filter is changed
     *
     * @param s
     *         The new filter request
     */
    @Override
    public void filter(CharSequence s) {
        if(mAdapter != null && mAdapter.getFilter() != null) {
            mAdapter.getFilter().filter(s);
        }
    }

    /**
     * This Async Task loads the weather information, from the Location lists passed as parameter
     */
    private class LoadWeatherInfoAsyncTask extends AsyncTask<Location, Void, List<WeatherInfo>> {

        @Override
        protected List<WeatherInfo> doInBackground(Location... params) {
            List<WeatherInfo> weathers = new ArrayList<>();
            for (Location location : params) {
                weathers.add(weatherService
                        .getWeatherByLatLng(location.getLatitude(), location.getLongitude()));
            }
            return weathers;
        }

        @Override
        protected void onPostExecute(List<WeatherInfo> basicInfos) {
            super.onPostExecute(basicInfos);
            enableProgressWheel(false);

            mAdapter = new WeatherGCardsAdapter(getActivity(), basicInfos);
            AnimationAdapter animAdapter = AnimationsUtils
                    .animateAdapter(new Random().nextInt(5), mAdapter);
            animAdapter.setAbsListView(mWeatherCardsLv);
            mWeatherCardsLv.setAdapter(animAdapter);
        }
    }
}
