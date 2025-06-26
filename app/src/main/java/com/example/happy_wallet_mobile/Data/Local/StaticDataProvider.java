package com.example.happy_wallet_mobile.Data.Local;

import com.example.happy_wallet_mobile.R;

import java.util.Arrays;
import java.util.List;

public class StaticDataProvider {

    public static List<String> getDateRanges() {
        return Arrays.asList(
                "Today", "Yesterday", "This Week", "Last Week",
                "This Month", "Last Month", "This Year", "Last Year",
                "Last 7 Days", "Last 30 Days"
        );
    }

    public static List<Integer> getColorList() {
        return Arrays.asList(
                R.color.color_1, R.color.color_2, R.color.color_3, R.color.color_4,
                R.color.color_5, R.color.color_6, R.color.color_7, R.color.color_8,
                R.color.color_9, R.color.color_10, R.color.color_11, R.color.color_12,
                R.color.color_13, R.color.color_14, R.color.color_15, R.color.color_16,
                R.color.color_17, R.color.color_18, R.color.color_19, R.color.color_20
        );
    }

    public static List<Integer> getIconList(){
        return Arrays.asList(
                R.drawable.ic_bell, R.drawable.ic_arrow_left_bold, R.drawable.ic_chats_circle, R.drawable.ic_gear_six, R.drawable.ic_house,
                R.drawable.ic_image_square_fill, R.drawable.ic_paper_plane_tilt, R.drawable.ic_plus_solid, R.drawable.ic_users_three, R.drawable.ic_wallet, R.drawable.ic_pen
        );
    }
}

