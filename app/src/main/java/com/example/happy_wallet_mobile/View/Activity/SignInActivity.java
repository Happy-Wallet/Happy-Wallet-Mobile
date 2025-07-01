package com.example.happy_wallet_mobile.View.Activity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.UserService;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Fragment.Authentication.ForgotPasswordFragment;
import com.example.happy_wallet_mobile.View.Fragment.Authentication.SignUpFragment;
import com.example.happy_wallet_mobile.ViewModel.Authentication.SignInViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;

public class SignInActivity extends AppCompatActivity {

    SignInViewModel signInViewModel;

    TextView tvProjectName;
    EditText etUserName;
    EditText edPassword; // Khai báo biến
    TextView tvSignIn;
    TextView tvSignUp;
    TextView tvForgotPassword;
    LinearLayout lnlSignInWithGoogle;
    FrameLayout flFragmentContainer;


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

        signInViewModel = new ViewModelProvider(this).get(SignInViewModel.class);

        tvProjectName = findViewById(R.id.tvProjectName);
        etUserName = findViewById(R.id.etUserName);
        edPassword = findViewById(R.id.etPassword); // Đã sửa từ R.id.edPassword thành R.id.etPassword
        tvSignIn = findViewById(R.id.tvSignIn);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        lnlSignInWithGoogle = findViewById(R.id.lnlSignInWithGoogle);


        // Set color for app name
        String ProjectName = "Happy Wallet";
        SpannableString spannableProjectName = new SpannableString(ProjectName);
        // Change color for "Happy" (from index 0 to 5)
        spannableProjectName.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.Paolo_Veronese_Green)),
                0,
                5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        // Change color for "Wallet" (from index 6 to end)
        spannableProjectName.setSpan(
                new ForegroundColorSpan(ContextCompat.getColor(this, R.color.Nautical)),
                6,
                ProjectName.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        tvProjectName.setText(spannableProjectName);

        // Get sign in result from viewmodel
        signInViewModel.getLoginResponse().observe(this, response -> {
            if (response != null) {
                Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();

                // change userPreference to current user
                // Gọi API lấy thông tin user và chuyển sang MainActivity nếu thành công
                fetchUserProfileAndSave(response.getToken());
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                tvSignIn.setEnabled(true); // Re-enable sign in button
            }
        });

        // Sign in click listener
        tvSignIn.setOnClickListener(v -> {
            tvSignIn.setEnabled(false); // Disable button to prevent multiple clicks
            String username = etUserName.getText().toString().trim();
            String password = edPassword.getText().toString().trim();

            // Call login method in ViewModel, passing the Activity's context
            signInViewModel.login(username, password, this);
        });

        // Forgot password click listener
        tvForgotPassword.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragmentContainer, new ForgotPasswordFragment())
                    .addToBackStack("auth")
                    .commit();
        });

        // Sign up click listener
        tvSignUp.setOnClickListener(v -> {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragmentContainer, new SignUpFragment())
                    .addToBackStack("auth")
                    .commit();
        });
    }

    // Method to fetch user profile information from API
    private void fetchUserProfileAndSave(String token) {
        UserService api = APIClient.getRetrofit().create(UserService.class);

        Call<UserResponse> call = api.getProfile("Bearer " + token);
        call.enqueue(new retrofit2.Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse userResponse = response.body();

                    // Convert từ UserResponse -> User
                    User user = new User();
                    user.setId(userResponse.getId());
                    user.setEmail(userResponse.getEmail());
                    user.setUserName(userResponse.getUsername());
                    user.setAvatarUrl(userResponse.getAvatarUrl());
                    user.setDateOfBirth(userResponse.getDateOfBirth());

                    // Convert date string sang Date nếu muốn

                    // Lưu vào UserPreferences static
                    UserPreferences.saveUser(user, token);

                    // Chuyển sang MainActivity
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignInActivity.this, "Lỗi khi lấy profile: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}