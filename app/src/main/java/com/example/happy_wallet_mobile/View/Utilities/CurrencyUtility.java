package com.example.happy_wallet_mobile.View.Utilities;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyUtility {
    private static final Locale VIETNAMESE = new Locale("vi", "VN");

    // convert number to currency format
    public static String format(BigDecimal amount) {
        NumberFormat format = NumberFormat.getCurrencyInstance(VIETNAMESE);
        return format.format(amount);
    }

    // covert currency format to bigDecimal
    public static BigDecimal parse(String currencyText) {
        try {
            NumberFormat format = NumberFormat.getCurrencyInstance(VIETNAMESE);
            Number number = format.parse(currencyText);
            return new BigDecimal(number.toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }


    public static BigDecimal parseFromCleanString(String raw) {
        return new BigDecimal(raw.replaceAll("[^\\d]", ""));
    }
}

