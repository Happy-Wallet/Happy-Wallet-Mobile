package com.example.happy_wallet_mobile.View.Activity;

import static androidx.core.view.ViewCompat.setBackground;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import com.example.happy_wallet_mobile.View.Utilities.IconHelper;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ImageView ivHome, ivWallet, ivGroups, ivSetting, ivChatBot;
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

        ivHome = findViewById(R.id.ivHome);
        ivWallet = findViewById(R.id.ivWallet);
        ivGroups = findViewById(R.id.ivGroups);
        ivSetting = findViewById(R.id.ivSetting);
        ivChatBot = findViewById(R.id.ivChatBot);

        // set default
        // set default image views color
        setTexiviewColor(ivHome, true);
        setTexiviewColor(ivWallet, false);
        setTexiviewColor(ivGroups, false);
        setTexiviewColor(ivSetting, false);
        mainViewModel.onNavItemClicked(Destination.HOME);
        ivChatBot.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        //set texiview click listener
        //ivHome click listener
        ivHome.setOnClickListener(v -> {
            setTexiviewColor(ivHome, true);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
            mainViewModel.onNavItemClicked(Destination.HOME);
        });

        //ivWallet click listener
        ivWallet.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, true);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
        });

        //ivGroups click listener
        ivGroups.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, true);
            setTexiviewColor(ivSetting, false);
        });

        //ivSetting click listener
        ivSetting.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, true);
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

    }

    // set iv color funcs
    void setTexiviewColor(ImageView imageView, boolean isChoosen){
        if (isChoosen){
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_20_paolo_veronese_green));
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);
        }
        else {
            imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_rounded_20_white));
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.Nautical), PorterDuff.Mode.SRC_IN);

        }
    }
}