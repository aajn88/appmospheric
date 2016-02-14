package com.clickdelivery.appmospheric.controllers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.controllers.common.BaseActivity;
import com.clickdelivery.appmospheric.model.WeatherInfo;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.google.inject.Inject;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * The Home activity is where all begins. Here is where the Weather google cards are shown
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity {

    /** Map Floation Action Button **/
    @InjectView(R.id.fab)
    private FloatingActionButton mMapFab;

    /** Weather Service **/
    @Inject
    private IWeatherService weatherService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFstActionTv.setVisibility(View.VISIBLE);
        mFstActionTv.setText(R.string.material_icon_magnify);
        mFstActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<WeatherInfo> cities = weatherService.findCityByName("Bogo");
                        for (WeatherInfo weatherInfo : cities) {
                            Log.d("City", weatherInfo.getName());
                        }
                    }
                }).start();
            }
        });

        mMapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        MenuItem item = mNavigationView.getMenu().getItem(0);
        item.setChecked(true);
        onNavigationItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
        }
    }

}
