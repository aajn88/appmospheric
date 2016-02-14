package com.clickdelivery.appmospheric.controllers;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.clickdelivery.appmospheric.R;
import com.clickdelivery.appmospheric.controllers.common.BaseActivity;
import com.clickdelivery.appmospheric.controllers.common.IFilterRegister;
import com.clickdelivery.appmospheric.controllers.common.IFilterSubscriber;
import com.clickdelivery.appmospheric.services.api.IWeatherService;
import com.clickdelivery.appmospheric.utils.AnimationsUtils;
import com.google.inject.Inject;

import java.util.HashSet;
import java.util.Set;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

/**
 * The Home activity is where all begins. Here is where the Weather google cards are shown
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
@ContentView(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements IFilterRegister {

    /** Duration of animation search in millis **/
    private static final int DURATION_ANIM_SEARCH = 400;

    /** Weather Service **/
    @Inject
    private IWeatherService weatherService;

    /** Searcher LinearLayout **/
    @InjectView(R.id.searcher_ll)
    private View mSearcherLl;

    /** Searcher EditText **/
    @InjectView(R.id.searcher_et)
    private EditText mSearcherEt;

    /** Searcher Back Button **/
    @InjectView(R.id.searcher_back_mditv)
    private TextView mSearcherBackTv;

    /** Clear Search Box Tv **/
    @InjectView(R.id.clear_mditv)
    private TextView mClearSearchTv;

    /** The Filter subscribers **/
    private Set<IFilterSubscriber> subscribers = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFstActionTv.setVisibility(View.VISIBLE);
        mFstActionTv.setText(R.string.material_icon_magnify);
        mFstActionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableSearcher();
            }
        });
        mSearcherBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSearcher();
            }
        });

        setUpSearch();

        MenuItem item = mNavigationView.getMenu().getItem(0);
        item.setChecked(true);
        onNavigationItemSelected(item);

        mSearcherLl.setVisibility(View.GONE);
    }

    /**
     * This method sets up the search action
     */
    private void setUpSearch() {
        mSearcherEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClearSearchTv.setVisibility(count == 0 ? View.GONE : View.VISIBLE);
                for (IFilterSubscriber subscriber : subscribers) {
                    subscriber.filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mClearSearchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearcherEt.setText("");
            }
        });

        mSearcherBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableSearcher();
            }
        });
    }

    /**
     * This method disables the Searcher Bar
     */
    private void disableSearcher() {
        int[] location = new int[2];
        mFstActionTv.getLocationOnScreen(location);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = AnimationsUtils
                    .animateCircleRevealShow(mSearcherLl, location[0], location[1],
                            DURATION_ANIM_SEARCH, true);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {}

                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearcherLl.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {}

                @Override
                public void onAnimationRepeat(Animator animation) {}
            });
            anim.start();
            mSearcherEt.setText("");
        } else {
            mSearcherLl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mSearcherLl.isShown()) {
            disableSearcher();
        } else {
            finishAffinity();
        }
    }

    /**
     * This method enables the Searcher bar
     */
    private void enableSearcher() {
        int[] location = new int[2];
        mFstActionTv.getLocationOnScreen(location);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AnimationsUtils.animateCircleRevealShow(mSearcherLl, location[0], location[1],
                    DURATION_ANIM_SEARCH).start();
        } else {
            mSearcherLl.setVisibility(View.VISIBLE);
        }

    }

    /**
     * This method registers a "subscriber" from filter actions
     *
     * @param subscriber
     *         The subscriber
     */
    @Override
    public void register(IFilterSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * This method unregister a subscriber from filter actions
     *
     * @param subscriber
     *         The subscriber to be unregistered
     */
    @Override
    public void unregister(IFilterSubscriber subscriber) {
        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
        }
    }
}
