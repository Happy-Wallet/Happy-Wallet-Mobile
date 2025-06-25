package com.example.happy_wallet_mobile.View.Utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;

public class CurrencyTextWatcher implements TextWatcher {

    private final EditText editText;
    private String current = "";

    public CurrencyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[^\\d]", "");

            try {
                BigDecimal parsed = new BigDecimal(cleanString);
                String formatted = CurrencyUtility.format(parsed);
                current = formatted;

                editText.setText(formatted);
                editText.setSelection(formatted.length());
            } catch (Exception e) {
                e.printStackTrace();
            }

            editText.addTextChangedListener(this);
        }
    }
}
