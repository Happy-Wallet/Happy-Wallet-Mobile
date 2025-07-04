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
import android.util.Log;
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

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.UserService;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.LoginResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.Model.User; // Đảm bảo import đúng User model
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
    EditText edPassword;
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
        edPassword = findViewById(R.id.etPassword);
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

                User loggedInUser = new User(
                        response.getUserId(),     // int Id
                        response.getEmail(),      // String Email
                        response.getUsername(),   // String UserName
                        null,                     // String HashedPassword (không có trong LoginResponse, truyền null)
                        null,                     // Date DateOfBirth (không có trong LoginResponse, truyền null)
                        null,                     // String AvatarUrl (truyền null vì LoginResponse không có getAvatarUrl())
                        null,                     // Date CreatedDate (không có trong LoginResponse, truyền null)
                        null,                     // Date UpdatedDate (không có trong LoginResponse, truyền null)
                        null                      // Date DeletedDate (không có trong LoginResponse, truyền null)
                );
                // Lưu User object và token vào SharedPreferences thông qua UserPreferences
                UserPreferences.saveUser(loggedInUser, response.getToken());

                // Cập nhật token cho APIClient ngay lập tức
                APIClient.setAuthToken(response.getToken());

                signInViewModel.fetchUserProfileAndSave();
                signInViewModel.getUserProfile().observe(this, profile->{
                    if(profile != null){
                        Log.d("SignInActivity", "Fetch profile done, user: " + profile.getUserName());

                        // Chuyển hướng sau khi chắc chắn đã lưu user
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });



            } else {
                // Đăng nhập thất bại
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                tvSignIn.setEnabled(true); // Kích hoạt lại nút đăng nhập
            }
        });




        // Sign in click listener
        tvSignIn.setOnClickListener(v -> {
            tvSignIn.setEnabled(false); // Disable button to prevent multiple clicks
            String username = etUserName.getText().toString().trim(); // Backend của bạn dùng email, nên đây có thể là email
            String password = edPassword.getText().toString().trim();

            // Call login method in ViewModel, passing the Activity's context
            // ViewModel sẽ gọi API và cập nhật LiveData getLoginResponse
            signInViewModel.login(username, password, this); // Đảm bảo username ở đây là email
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

}