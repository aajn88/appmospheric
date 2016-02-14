package com.clickdelivery.appmospheric.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.BaseAdapter;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.ScaleInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingLeftInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;

/**
 * @author <a href="mailto:aajn88@gmail.com">Antonio Jimenez</a>
 */
public final class ViewUtils {

    /** Private constructor to avoid instances **/
    private ViewUtils(){}

    /**
     * This method makes a toast.
     *
     * @param context
     *         Current context
     * @param resId
     *         String to be shown
     * @param duration
     *         Toast duration
     * @param color
     *         SuperToast color. Selected from {@link Style} constants, such as, {@link Style#BLUE}
     *
     * @return SuperToast instance
     */
    @NonNull
    public static SuperToast makeToast(Context context,
                                       @StringRes
                                       int resId, int duration, int color) {
        return makeToast(context, context.getString(resId), duration, color);
    }

    /**
     * This method makes a toast.
     *
     * @param context
     *         Current context
     * @param text
     *         String to be shown
     * @param duration
     *         Toast duration
     * @param color
     *         SuperToast color. Selected from {@link Style} constants, such as, {@link Style#BLUE}
     *
     * @return SuperToast instance
     */
    @NonNull
    public static SuperToast makeToast(Context context, String text, int duration, int color) {
        return SuperToast.create(context, text, duration,
                Style.getStyle(color, SuperToast.Animations.FLYIN));
    }

}
