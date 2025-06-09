package com.example.happy_wallet_mobile.View.Utilities;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

public class IconHelper {

    public enum Position {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public static void setIconTextView(Context context, TextView tv, int iconSizeDp, @DrawableRes int drawableResId, Position position) {
        Drawable icon = ContextCompat.getDrawable(context, drawableResId);
        if (icon == null) return;

        int sizePx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, iconSizeDp, context.getResources().getDisplayMetrics());
        icon.setBounds(0, 0, sizePx, sizePx);

        Drawable start = null, top = null, end = null, bottom = null;
        switch (position) {
            case LEFT:
                start = icon;
                break;
            case TOP:
                top = icon;
                break;
            case RIGHT:
                end = icon;
                break;
            case BOTTOM:
                bottom = icon;
                break;
        }

        int paddingDp = -10;
        int paddingPx = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, paddingDp, context.getResources().getDisplayMetrics());
        tv.setCompoundDrawablePadding(paddingPx);

        tv.setCompoundDrawablesRelative(start, top, end, bottom);
    }


}
