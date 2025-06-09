package com.example.happy_wallet_mobile.View.Utilities;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class CustomTypefaceSpan extends TypefaceSpan {

    private final Typeface customTypeface;

    public CustomTypefaceSpan(String family, Typeface type) {
        super(family);
        this.customTypeface = type;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        applyCustomTypeFace(ds, customTypeface);
    }

    @Override
    public void updateMeasureState(TextPaint paint) {
        applyCustomTypeFace(paint, customTypeface);
    }

    private static void applyCustomTypeFace(Paint paint, Typeface tf) {
        Typeface old = paint.getTypeface();
        int oldStyle = old != null ? old.getStyle() : 0;

        int fake = oldStyle & ~tf.getStyle();
        if ((fake & Typeface.BOLD) != 0) {
            paint.setFakeBoldText(true);
        }

        if ((fake & Typeface.ITALIC) != 0) {
            paint.setTextSkewX(-0.25f);
        }

        paint.setTypeface(tf);
    }
}

