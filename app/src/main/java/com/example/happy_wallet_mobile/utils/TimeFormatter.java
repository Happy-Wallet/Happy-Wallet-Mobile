package com.example.happy_wallet_mobile.utils; // Đảm bảo đúng package của bạn

import android.text.format.DateUtils; // Import DateUtils cho định dạng thời gian tương đối
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFormatter {

    // Định dạng phổ biến mà backend Node.js trả về (ISO 8601)
    // Ví dụ: "2025-07-04T08:30:00.000Z" (có thể có hoặc không có phần .000Z)
    // Hoặc "2025-07-04 08:30:00" nếu đó là định dạng từ MySQL DATETIME
    private static final String API_DATE_FORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"; // UTC
    private static final String API_DATE_FORMAT_MYSQL = "yyyy-MM-dd HH:mm:ss"; // Local time (from MySQL financialtrackingapp.sql)


    /**
     * Chuyển đổi chuỗi thời gian từ API thành đối tượng Date.
     * @param dateString Chuỗi thời gian từ API.
     * @return Đối tượng Date hoặc null nếu lỗi phân tích cú pháp.
     */
    public static Date parseApiDate(String dateString) {
        if (dateString == null || dateString.isEmpty()) {
            return null;
        }
        // Thử parse theo ISO 8601
        try {
            SimpleDateFormat isoFormat = new SimpleDateFormat(API_DATE_FORMAT_ISO, Locale.getDefault());
            isoFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Đảm bảo xử lý đúng múi giờ UTC
            return isoFormat.parse(dateString);
        } catch (ParseException e) {
            // Log.e("TimeFormatter", "Failed to parse ISO date: " + dateString, e);
            // Thử parse theo định dạng MySQL DATETIME nếu ISO không thành công
            try {
                SimpleDateFormat mysqlFormat = new SimpleDateFormat(API_DATE_FORMAT_MYSQL, Locale.getDefault());
                // Không cần set múi giờ nếu database lưu local time và backend không chuyển đổi
                return mysqlFormat.parse(dateString);
            } catch (ParseException e2) {
                // Log.e("TimeFormatter", "Failed to parse MySQL date: " + dateString, e2);
                return null;
            }
        }
    }

    /**
     * Định dạng thời gian thành dạng tương đối (ví dụ: "5 phút trước", "Hôm qua").
     * @param date Đối tượng Date.
     * @return Chuỗi thời gian tương đối.
     */
    public static String getRelativeTimeSpanString(Date date) {
        if (date == null) {
            return "";
        }
        long now = System.currentTimeMillis();
        long then = date.getTime();

        // Sử dụng DateUtils để định dạng thời gian tương đối một cách thông minh
        // flags: FORMAT_ABBREV_RELATIVE (ví dụ: 5 min. ago), FORMAT_SHOW_DATE, FORMAT_SHOW_TIME
        return DateUtils.getRelativeTimeSpanString(
                then,
                now,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_RELATIVE | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME
        ).toString();
    }

    /**
     * Định dạng thời gian thành dạng ngắn gọn (ví dụ: "3 phút", "1 giờ", "Thg 7 4").
     * Thường dùng cho các hiển thị gọn như "3m" trong hình ảnh.
     * @param date Đối tượng Date.
     * @return Chuỗi thời gian ngắn gọn.
     */
    public static String getShortRelativeTime(Date date) {
        if (date == null) {
            return "";
        }
        long now = System.currentTimeMillis();
        long diff = now - date.getTime();

        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30; // Approximation
        long years = days / 365; // Approximation

        if (years > 0) return years + " năm";
        if (months > 0) return months + " thg";
        if (weeks > 0) return weeks + " tuần";
        if (days > 0) return days + " ngày";
        if (hours > 0) return hours + " giờ";
        if (minutes > 0) return minutes + " phút";
        return seconds + " giây";
    }
}