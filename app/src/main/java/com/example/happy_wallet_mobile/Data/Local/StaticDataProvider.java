package com.example.happy_wallet_mobile.Data.Local;

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
                R.color.Red, R.color.Green, R.color.Blue,
                R.color.Orange, R.color.Yellow, R.color.Purple
        );
    }
}

