package com.clickdelivery.appmospheric.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.BaseAdapter;

import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

/**
 * This Utils class offers some tools for Animations
 *
 * @author <a href="mailto:aajn88@gmail.com">Antonio A. Jimenez N.</a>
 */
public final class AnimationsUtils {

    /** Private constructor to avoid instances **/
    private AnimationsUtils() {}

    /**
     * This method returns an animator with Circle Reveal animation
     *
     * @param viewRoot
     *         View to be revealed
     * @param cx
     *         Central X of the circle reveal
     * @param cy
     *         Central Y of the circle reveal
     * @param duration
     *         Animation duration
     * @param inverse
     *         If is True, then the circle reveal animation works from outside to inside. False, the
     *         regular animation is performed
     *
     * @return Created Animator
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator animateCircleRevealShow(View viewRoot, int cx, int cy, int duration,
                                                   boolean inverse) {
        int initialRadius = 0;
        int finalRadius = Math.max(viewRoot.getWidth(), viewRoot.getHeight());
        if (inverse) {
            initialRadius = finalRadius;
            finalRadius = 0;
        }

        Animator anim = ViewAnimationUtils
                .createCircularReveal(viewRoot, cx, cy, initialRadius, finalRadius);
        viewRoot.setVisibility(View.VISIBLE);
        anim.setDuration(duration);
        anim.setInterpolator(new AccelerateInterpolator());
        return anim;
    }

    /**
     * This method returns an animator with Circle Reveal animation
     *
     * @param viewRoot
     *         View to be revealed
     * @param cx
     *         Central X of the circle reveal
     * @param cy
     *         Central Y of the circle reveal
     * @param duration
     *         Animation duration
     *
     * @return Created Animator
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator animateCircleRevealShow(View viewRoot, int cx, int cy, int duration) {
        return animateCircleRevealShow(viewRoot, cx, cy, duration, false);
    }

    /**
     * This method returns an animation for an adapter given the base adapter and a key
     *
     * @param key
     *         Key from 0 to 4 to select an animation
     * @param adapter
     *         The base adapter that will take the animation adapter
     *
     * @return Animated Adapter
     */
    public static AnimationAdapter animateAdapter(int key, BaseAdapter adapter) {
        AnimationAdapter animAdapter;
        switch (key) {
            default:
            case 0:
                animAdapter = new AlphaInAnimationAdapter(adapter);
                break;
            case 1:
                animAdapter = new ScaleInAnimationAdapter(adapter);
                break;
            case 2:
                animAdapter = new SwingBottomInAnimationAdapter(adapter);
                break;
            case 3:
                animAdapter = new SwingLeftInAnimationAdapter(adapter);
                break;
            case 4:
                animAdapter = new SwingRightInAnimationAdapter(adapter);
                break;
        }
        return animAdapter;
    }

}
