package com.clickdelivery.appmospheric.controllers.main_view;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.services.api.ILocationService;
import com.clickdelivery.appmospheric.views.progress_bars.ProgressWheel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_select_location)
public class SelectLocationActivity extends RoboActionBarActivity
        implements OnMapReadyCallback, ILocationService.LocationResultListener {

    /** First Action Btn **/
    @InjectView(R.id.fst_action_mditv)
    protected TextView mFstActionTv;

    /** Second Action Btn **/
    @InjectView(R.id.snd_action_btn)
    protected TextView mSndActionTv;

    /** Loading Progress Wheel **/
    @InjectView(R.id.loading_pw)
    protected ProgressWheel mLoadingPw;

    /** Marker Icon **/
    @InjectView(R.id.marker_mditv)
    protected TextView mMarkerIcon;

    /** Google Map **/
    private GoogleMap mMap;

    /** Appmospheric Toolbar **/
    @InjectView(R.id.toolbar)
    private Toolbar mToolbar;

    /** Location Services **/
    @Inject
    private ILocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Hide actions
        mSndActionTv.setVisibility(View.GONE);

        mFstActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                showConfirmation(cameraPosition.target);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        enableProgressWheel(true);
    }

    /**
     * This method enables the Progress Wheel and hide the related views
     *
     * @param enable
     *         Enable/Disable
     */
    private void enableProgressWheel(boolean enable) {
        mLoadingPw.setVisibility(enable ? View.VISIBLE : View.GONE);
        mMarkerIcon.setVisibility(enable ? View.GONE : View.VISIBLE);

        if(mLoadingPw.isSpinning()) {
            mLoadingPw.stopSpinning();
        }

        if(enable) {
            mLoadingPw.spin();
        }
    }

    /**
     * This method shows the location confirmation. If the user confirms the location, then it is
     * returned as a result of this activity and finish it. Otherwise just hide the dialog
     *
     * @param latLng
     *         Selected Latitude and Longitude
     */
    private void showConfirmation(final LatLng latLng) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.StyledDialog);
        builder.setMessage(R.string.location_confirmation).setCancelable(true)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(R.string.add_location, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent resultData = new Intent();
                resultData.putExtra(MainFragment.RESULTING_LOCATION, latLng);
                setResult(RESULT_OK, resultData);
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Manipulates the map once available. This callback is triggered when the map is ready to be
     * used. This is where we can add markers or lines, add listeners or move the camera. In this
     * case, we just add a marker near Sydney, Australia. If Google Play services is not installed
     * on the device, the user will be prompted to install it inside the SupportMapFragment. This
     * method will only be triggered once the user has installed Google Play services and returned
     * to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMyLocationEnabled(Boolean.TRUE);
        if (!locationService.getLocation(this, this)) {
            enableProgressWheel(false);
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
        CameraUpdate cameraUpdate = CameraUpdateFactory
                .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 17f);
        mMap.animateCamera(cameraUpdate);
        enableProgressWheel(false);
    }
}
