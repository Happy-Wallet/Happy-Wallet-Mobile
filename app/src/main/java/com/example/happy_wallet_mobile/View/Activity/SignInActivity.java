package com.example.happy_wallet_mobile.View.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.SignInViewModel;

public class SignInActivity extends AppCompatActivity {

    SignInViewModel signInViewModel;

    TextView tvProjectName;
    EditText etUserName;
    EditText edPassword;
    TextView tvSignIn;
    TextView tvSignUp;
    LinearLayout lnlSignInWithGoogle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.SignIn), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        tvProjectName = findViewById(R.id.tvProjectName);
        etUserName = findViewById(R.id.etUserName);
        edPassword = findViewById(R.id.etPassword);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        lnlSignInWithGoogle = findViewById(R.id.lnlSignInWithGoogle);


        String ProjectName = "Happy Wallet";
        SpannableString spannableProjectName = new SpannableString(ProjectName);
        // Đổi màu cho "Happy" (từ index 0 đến 5)
        spannableProjectName.setSpan(
                new ForegroundColorSpan(Color.parseColor("#4DC9A9")),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        // Đổi màu cho "Wallet" (từ index 6 đến hết)
        spannableProjectName.setSpan(
                new ForegroundColorSpan(Color.parseColor("#30437A")),
                6,
                ProjectName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvProjectName.setText(spannableProjectName);


        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        signInViewModel.getSignInResult().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                finish();

            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        tvSignIn.setOnClickListener(v -> {
            String username = etUserName.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            signInViewModel.setUserName(username);
            signInViewModel.setPassword(password);
            signInViewModel.attemptSignIn();
        });
    }
}