package com.example.happy_wallet_mobile.View.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.Model.MainDestination;
import com.example.happy_wallet_mobile.Model.SubDestination;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.AddCategoryFragment;
import com.example.happy_wallet_mobile.View.Fragment.AddIncomeFragment;
import com.example.happy_wallet_mobile.View.Fragment.CategoryListFragment;
import com.example.happy_wallet_mobile.View.Fragment.EditProfileFragment;
import com.example.happy_wallet_mobile.View.Fragment.GroupsFragment;
import com.example.happy_wallet_mobile.View.Fragment.HomeFragment;
import com.example.happy_wallet_mobile.View.Fragment.NotificationFragment;
import com.example.happy_wallet_mobile.View.Fragment.AddSavingGoalFragment;
import com.example.happy_wallet_mobile.View.Fragment.SettingFragment;
import com.example.happy_wallet_mobile.View.Fragment.WalletFragment;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    ImageView ivHome, ivWallet, ivGroups, ivSetting, ivChatBot;
    FrameLayout flNotification;
    MainViewModel mainViewModel;

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

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ivHome = findViewById(R.id.ivHome);
        ivWallet = findViewById(R.id.ivWallet);
        ivGroups = findViewById(R.id.ivGroups);
        ivSetting = findViewById(R.id.ivSetting);
        ivChatBot = findViewById(R.id.ivChatBot);
        flNotification = findViewById(R.id.flNotification);

        // set default
        // set default image views color
        setTexiviewColor(ivHome, true);
        setTexiviewColor(ivWallet, false);
        setTexiviewColor(ivGroups, false);
        setTexiviewColor(ivSetting, false);
        mainViewModel.onNavItemClickedMainBelow(MainDestination.HOME);
        ivChatBot.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        // flnotification click listener
        flNotification.setOnClickListener(v ->  {
            mainViewModel.onNavItemClickedSubAbove(SubDestination.NOTIFICATION);
        });

        //set image views click listener
        //ivHome click listener
        ivHome.setOnClickListener(v -> {
            setTexiviewColor(ivHome, true);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
            mainViewModel.onNavItemClickedMainBelow(MainDestination.HOME);
        });

        //ivWallet click listener
        ivWallet.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, true);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
            mainViewModel.onNavItemClickedMainBelow(MainDestination.WALLET);
        });

        //ivGroups click listener
        ivGroups.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, true);
            setTexiviewColor(ivSetting, false);
            mainViewModel.onNavItemClickedMainBelow(MainDestination.GROUPS);
        });

        //ivSetting click listener
        ivSetting.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, true);
            mainViewModel.onNavItemClickedMainBelow(MainDestination.SETTING);
        });

        //ivChat click listener
        ivChatBot.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CommunityActivity.class));
        });


        // main fragment under navigation
        mainViewModel.navigateMainBelow.observe(this, event -> {
            MainDestination destination = event.getContentIfNotHandled();
            if (destination != null) {
                Fragment fragment = null;
                switch (destination) {
                    case HOME:
                        fragment = new HomeFragment();
                        break;
                    case WALLET:
                        fragment = new WalletFragment();
                        break;
                    case GROUPS:
                        fragment = new GroupsFragment();
                        break;
                    case SETTING:
                        fragment = new SettingFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container_below, fragment)
                            .commit();
                }
            }
        });

        // main fragment under navigation
        mainViewModel.navigateSubBelow.observe(this, event ->{
            SubDestination subDestination = event.getContentIfNotHandled();
            if (subDestination != null){
                Fragment fragment = null;
                switch (subDestination){
                    case ADD_SAVING_GOAL:
                        fragment = new AddSavingGoalFragment();
                        break;
                    case CATEGORY_LIST:
                        fragment = new CategoryListFragment();
                        break;
                    case ADD_CATEGORY:
                        fragment = new AddCategoryFragment();
                        break;
                    case ADD_INCOME:
                        fragment = new AddIncomeFragment();
                        break;
                }

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container_below, fragment)
                            .addToBackStack("below")
                            .commit();
                }
            }
        });

        // sub fragment above navigation
        mainViewModel.navigateSubAbove.observe(this, event -> {
            SubDestination subDestination = event.getContentIfNotHandled();
            if (subDestination != null) {
                Fragment fragment = null;
                switch (subDestination) {
                    case NOTIFICATION:
                        fragment = new NotificationFragment();
                        break;
                    case EDIT_PROFILE:
                        fragment = new EditProfileFragment();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container_above, fragment)
                            .addToBackStack("above")
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