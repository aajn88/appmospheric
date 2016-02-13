package com.clickdelivery.appmospheric.controllers;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.services.api.ILocationService;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.clickdelivery.appmospheric.utils.StringUtils;
import com.clickdelivery.appmospheric.views.progress_bars.ProgressWheel;
import com.google.inject.Inject;

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

        mLoadingPw.spin();
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
        if (mLoadingPw.isSpinning()) {
            mLoadingPw.stopSpinning();
        }

        Log.d(TAG_LOG, StringUtils.format("The reached location is %s", location));
    }

    @Override
    public void onPause() {
        super.onPause();
        locationService.onPauseSyncState();
    }
}
