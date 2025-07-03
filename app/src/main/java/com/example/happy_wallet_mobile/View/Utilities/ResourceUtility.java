package com.example.happy_wallet_mobile.View.Utilities;

import android.content.Context;

public class ResourceUtility {
    public static int getDrawableResId(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return id != 0 ? id : getDefaultDrawable();
    }

    public static int getColorResId(Context context, String name) {
        int id = context.getResources().getIdentifier(name, "color", context.getPackageName());
        return id != 0 ? id : getDefaultColor();
    }

    private static int getDefaultDrawable() {
        return android.R.drawable.ic_menu_help; // fallback drawable
    }

    private static int getDefaultColor() {
        return android.R.color.darker_gray; // fallback color
    }
}
