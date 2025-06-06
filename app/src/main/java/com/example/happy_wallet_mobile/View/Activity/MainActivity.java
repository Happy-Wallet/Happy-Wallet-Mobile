package com.example.happy_wallet_mobile.View.Activity;

import static androidx.core.view.ViewCompat.setBackground;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.happy_wallet_mobile.Model.Destination;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.HomeFragment;
import com.example.happy_wallet_mobile.View.Utilities.CustomTypefaceSpan;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    TextView tvHome, tvWallet, tvGroups, tvSetting, tvChatBot;
    MainViewModel mainViewModel = new MainViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvHome = findViewById(R.id.tvHome);
        tvWallet = findViewById(R.id.tvWallet);
        tvGroups = findViewById(R.id.tvGroups);
        tvSetting = findViewById(R.id.tvSetting);

        // set default
        // set default textviews color
        setTextViewColor(tvHome, true);
        setTextViewColor(tvWallet, false);
        setTextViewColor(tvGroups, false);
        setTextViewColor(tvSetting, false);






        //set textView click listener
        //tvHome click listener
        tvHome.setOnClickListener(v -> {
            setTextViewColor(tvHome, true);
            setTextViewColor(tvWallet, false);
            setTextViewColor(tvGroups, false);
            setTextViewColor(tvSetting, false);
            mainViewModel.onNavItemClicked(Destination.HOME);
        });

        //tvWallet click listener
        tvWallet.setOnClickListener(v -> {
            setTextViewColor(tvHome, false);
            setTextViewColor(tvWallet, true);
            setTextViewColor(tvGroups, false);
            setTextViewColor(tvSetting, false);
        });

        //tvGroups click listener
        tvGroups.setOnClickListener(v -> {
            setTextViewColor(tvHome, false);
            setTextViewColor(tvWallet, false);
            setTextViewColor(tvGroups, true);
            setTextViewColor(tvSetting, false);
        });

        //tvSetting click listener
        tvSetting.setOnClickListener(v -> {
            setTextViewColor(tvHome, false);
            setTextViewColor(tvWallet, false);
            setTextViewColor(tvGroups, false);
            setTextViewColor(tvSetting, true);
        });


        // fragment navigation
        mainViewModel.navigate.observe(this, event -> {
            Destination destination = event.getContentIfNotHandled();
            if (destination != null) {
                Fragment fragment = null;
                switch (destination) {
                    case HOME:
                        fragment = new HomeFragment();
                        break;
                    case WALLET:

                        break;
                    case GROUPS:

                        break;
                    case SETTING:

                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
            }
        });

        //set textview contents
        //set fonts
        Typeface phosphorRegular = null;
        Typeface poppinsRegular = null;
        try {
            phosphorRegular = getResources().getFont(R.font.phosphor_regular);
            poppinsRegular = getResources().getFont(R.font.poppins_regular_400);
            Log.d("MainActivity", "Fonts loaded successfully from resources: phosphorRegular=" + (phosphorRegular != null) + "; poppinsRegular=" + (poppinsRegular != null));
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MainActivity", "Font loading error: " + e.getMessage());
            // to default typeface if loading fails
            phosphorRegular = Typeface.DEFAULT;
            poppinsRegular = Typeface.DEFAULT;
        }

        // set tvHome content
        SpannableString spannableHome = new SpannableString( "\uE2C2\nhome");
        //set tvWallet fonts
        spannableHome.setSpan(
                new CustomTypefaceSpan("Phosphor", phosphorRegular),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableHome.setSpan(
                new CustomTypefaceSpan("Poppins", poppinsRegular),
                1, spannableHome.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        //set tvWallet size
        spannableHome.setSpan(
                new AbsoluteSizeSpan(30, true),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableHome.setSpan(
                new AbsoluteSizeSpan(12, true),
                1, spannableHome.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvHome.setText(spannableHome);

        // set tvWallet content
        SpannableString spannableWallet = new SpannableString("\uE68A\nwallet");
        // set tvWallet fonts
        spannableWallet.setSpan(
                new CustomTypefaceSpan("Phosphor", phosphorRegular),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableWallet.setSpan(
                new CustomTypefaceSpan("Poppins", poppinsRegular),
                1, spannableWallet.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        // set tvWallet size
        spannableWallet.setSpan(
                new AbsoluteSizeSpan(30, true),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableWallet.setSpan(
                new AbsoluteSizeSpan(12, true),
                1, spannableWallet.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvWallet.setText(spannableWallet);

        // set tvGroups content
        SpannableString spannableGroups = new SpannableString("\uE68E\ngroups");
        // set tvGroups fonts
        spannableGroups.setSpan(
                new CustomTypefaceSpan("Phosphor", phosphorRegular),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableGroups.setSpan(
                new CustomTypefaceSpan("Poppins", poppinsRegular),
                1, spannableGroups.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        // set tvGroups size
        spannableGroups.setSpan(
                new AbsoluteSizeSpan(30, true),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableGroups.setSpan(
                new AbsoluteSizeSpan(12, true),
                1, spannableGroups.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvGroups.setText(spannableGroups);

        // set tvSetting content
        SpannableString spannableSetting = new SpannableString("\uE272\nsetting");
        // set tvSetting fonts
        spannableSetting.setSpan(
                new CustomTypefaceSpan("Phosphor", phosphorRegular),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableSetting.setSpan(
                new CustomTypefaceSpan("Poppins", poppinsRegular),
                1, spannableSetting.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        // set tvSetting size
        spannableSetting.setSpan(
                new AbsoluteSizeSpan(30, true),
                0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannableSetting.setSpan(
                new AbsoluteSizeSpan(12, true),
                1, spannableSetting.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvSetting.setText(spannableSetting);
    }

    // set tv color funcs
    void setTextViewColor(TextView textView, boolean isChoosen){
        if (isChoosen){
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_20_paolo_veronese_green));
            textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        }
        else {
            textView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_20_white));
            textView.setTextColor(ContextCompat.getColor(this, R.color.Nautical));

        }
    }
}