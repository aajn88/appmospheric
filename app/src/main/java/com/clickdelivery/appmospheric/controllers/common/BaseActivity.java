package com.clickdelivery.appmospheric.controllers.common;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.clickdelivery.appmospheric.R;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/**
 * This is the base activity that must be extended if is desired to get the basic functions across
 * the app
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public class BaseActivity extends RoboActionBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /** Appmospheric Toolbar **/
    @InjectView(R.id.toolbar)
    protected Toolbar mToolbar;

    /** First Action Btn **/
    @InjectView(R.id.fst_action_mditv)
    protected TextView mFstActionTv;

    /** Second Action Btn **/
    @InjectView(R.id.snd_action_btn)
    protected TextView mSndActionTv;

    /** The Drawer Layout **/
    @InjectView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;

    /** The Navigation View **/
    @InjectView(R.id.nav_view)
    protected NavigationView mNavigationView;

    /** The Drawer Layout **/
    protected ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Hiden by default
        mFstActionTv.setVisibility(View.GONE);
        mSndActionTv.setVisibility(View.GONE);

        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item
     *         The selected item
     *
     * @return true to display the item as the selected item
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
