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

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.Group.GroupsFragment;
import com.example.happy_wallet_mobile.View.Fragment.Home.HomeFragment;
import com.example.happy_wallet_mobile.View.Fragment.NotificationFragment;
import com.example.happy_wallet_mobile.View.Fragment.Setting.SettingFragment;
import com.example.happy_wallet_mobile.View.Fragment.Wallet.WalletFragment;
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
        setTexiviewColor(ivHome, true);
        setTexiviewColor(ivWallet, false);
        setTexiviewColor(ivGroups, false);
        setTexiviewColor(ivSetting, false);
        mainViewModel.navigateMainBelow(new HomeFragment());
        ivChatBot.setColorFilter(ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_IN);

        // flnotification click listener
        flNotification.setOnClickListener(v ->  {
            mainViewModel.navigateSubAbove(new NotificationFragment());
        });

        //set image views click listener
        //ivHome click listener
        ivHome.setOnClickListener(v -> {
            setTexiviewColor(ivHome, true);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
            mainViewModel.navigateMainBelow(new HomeFragment());
        });

        //ivWallet click listener
        ivWallet.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, true);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, false);
            mainViewModel.navigateMainBelow(new WalletFragment());
        });

        //ivGroups click listener
        ivGroups.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, true);
            setTexiviewColor(ivSetting, false);
            mainViewModel.navigateMainBelow(new GroupsFragment());
        });

        //ivSetting click listener
        ivSetting.setOnClickListener(v -> {
            setTexiviewColor(ivHome, false);
            setTexiviewColor(ivWallet, false);
            setTexiviewColor(ivGroups, false);
            setTexiviewColor(ivSetting, true);
            mainViewModel.navigateMainBelow(new SettingFragment());
        });

        //ivChat click listener
        ivChatBot.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CommunityActivity.class));
        });


        // main fragment under navigation
        mainViewModel.navigateMainBelow.observe(this, event -> {
            Fragment fragment = event.getContentIfNotHandled();
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container_below, fragment)
                        .commit();
            }
        });

        // main fragment under navigation
        mainViewModel.navigateSubBelow.observe(this, event -> {
            Fragment fragment = event.getContentIfNotHandled();
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_below, fragment)
                        .addToBackStack("below")
                        .commit();
            }
        });

        // sub fragment above navigation
        mainViewModel.navigateSubAbove.observe(this, event -> {
            Fragment fragment = event.getContentIfNotHandled();
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container_above, fragment)
                        .addToBackStack("above")
                        .commit();
            }
        });

        // Observe navigateBackEvent from MainViewModel
        mainViewModel.getNavigateBackEvent().observe(this, event -> {
            Boolean shouldNavigate = event.getContentIfNotHandled(); // Lấy nội dung nếu chưa được xử lý
            if (shouldNavigate != null && shouldNavigate) { // Kiểm tra null và giá trị true
                getSupportFragmentManager().popBackStack(); // Quay lại Fragment trước đó
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