package com.example.happy_wallet_mobile.View.Activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.Community.AddNewsFragment;

public class CommunityActivity extends AppCompatActivity {

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

        ivBack = findViewById(R.id.ivBack);
        flContentContainer = findViewById(R.id.flContentContainer);

        // back
        ivBack.setOnClickListener(v -> {
            finish();
        });

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flContentContainer, new AddNewsFragment())
                .commit();
    }
}