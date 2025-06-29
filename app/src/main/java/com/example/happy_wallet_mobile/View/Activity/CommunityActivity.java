package com.example.happy_wallet_mobile.View.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.Community.AddNewsFragment;
import com.example.happy_wallet_mobile.ViewModel.Community.CommunityViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;

public class CommunityActivity extends AppCompatActivity {

    CommunityViewModel communityViewModel;
    ImageView ivBack;
    FrameLayout flContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_community);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        communityViewModel = new ViewModelProvider(this).get(CommunityViewModel.class);

        ivBack = findViewById(R.id.ivBack);
        flContentContainer = findViewById(R.id.flContentContainer);

        // back
        ivBack.setOnClickListener(v -> {
            Log.d("CommunityActivity", "ivBack clicked");
            finish();
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContentContainer, new AddNewsFragment())
                .commit();
    }
}