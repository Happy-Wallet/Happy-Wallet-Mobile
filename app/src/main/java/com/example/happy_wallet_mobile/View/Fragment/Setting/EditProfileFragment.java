package com.example.happy_wallet_mobile.View.Fragment.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.UserService;
import com.google.android.material.datepicker.MaterialDatePicker; // Import MaterialDatePicker

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException; // Import ParseException
import java.text.SimpleDateFormat;
import java.util.Calendar; // Import Calendar
import java.util.Date; // Import Date
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;

public class EditProfileFragment extends Fragment {

    ImageView ivProfileImage;
    EditText etUserName, etDateOfBirth;
    TextView tvSave, tvCancel;
    ProgressBar progressBar;

    private static final int REQUEST_CODE_PICK_IMAGE = 1001;
    private Uri selectedImageUri;
    private SharedPreferences sharedPreferences;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Initialize View components
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        etUserName = view.findViewById(R.id.etUserName);
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth);
        tvSave = view.findViewById(R.id.tvSave);
        tvCancel = view.findViewById(R.id.tvCancel);
        progressBar = view.findViewById(R.id.progressBar);

        // Get token from SharedPreferences
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", null);

        // Call function to fetch user profile data when Fragment is created
        fetchUserProfile();

        // Set OnClickListener for ImageView to pick avatar image
        ivProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        // Set OnClickListener for Date of Birth EditText to show DatePicker
        etDateOfBirth.setOnClickListener(v -> showDatePicker());

        // Set OnClickListener for Save button
        tvSave.setOnClickListener(v -> updateProfile());

        // Set OnClickListener for Cancel button to go back to previous screen
        tvCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    // Method to fetch user profile information from API
    private void fetchUserProfile() {
        if (token == null) {
            Toast.makeText(getContext(), "Authentication token not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure UI updates are on the main thread
        requireActivity().runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE); // Show ProgressBar
        });


        UserService api = APIClient.getRetrofit().create(UserService.class);
        Call<UserResponse> call = api.getProfile("Bearer " + token);

        call.enqueue(new retrofit2.Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, retrofit2.Response<UserResponse> response) {
                // All UI updates must be on the main thread
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar

                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse user = response.body();
                        etUserName.setText(user.getUsername());

                        // Check and set date of birth if available
                        if (user.getDateOfBirth() != null && !user.getDateOfBirth().isEmpty()) {
                            try {
                                SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                                Date date = apiDateFormat.parse(user.getDateOfBirth());
                                SimpleDateFormat displayDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                                etDateOfBirth.setText(displayDateFormat.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                                etDateOfBirth.setText(user.getDateOfBirth());
                            }
                        }

                        // Load avatar image using Glide if URL is not empty
                        if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                            Glide.with(requireContext())
                                    .load(user.getAvatarUrl())
                                    .circleCrop()
                                    .placeholder(R.drawable.bg_rounded_50_white)
                                    .error(R.drawable.bg_rounded_50_white)
                                    .into(ivProfileImage);
                        } else {
                            ivProfileImage.setImageResource(R.drawable.bg_rounded_50_white);
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch profile: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // All UI updates must be on the main thread
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Error fetching profile: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Method to show Material DatePicker
    private void showDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Chọn ngày sinh");
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        final MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selection);
            Date selectedDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            String formattedDate = sdf.format(selectedDate);
            etDateOfBirth.setText(formattedDate);
        });
    }

    // Method to update user profile
    private void updateProfile() {
        String username = etUserName.getText().toString().trim();
        String dob = etDateOfBirth.getText().toString().trim();

        if (token == null) {
            Toast.makeText(getContext(), "Authentication token not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ensure UI updates are on the main thread
        requireActivity().runOnUiThread(() -> {
            progressBar.setVisibility(View.VISIBLE); // Show ProgressBar when updating
        });


        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("date_of_birth", dob);

        if (selectedImageUri != null) {
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedImageUri);
                byte[] imageBytes = IOUtils.toByteArray(inputStream);
                builder.addFormDataPart("avatar", "avatar.jpg",
                        RequestBody.create(imageBytes, MediaType.parse("image/*")));
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error preparing image for upload: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                // Ensure UI updates are on the main thread
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar if error occurs
                });
                return;
            }
        }

        RequestBody requestBody = builder.build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url("http://10.0.2.2:3000/settings/edit-profile")
                .header("Authorization", "Bearer " + token)
                .put(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // All UI updates must be on the main thread
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Profile update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                // All UI updates must be on the main thread
                requireActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE); // Hide ProgressBar
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        // Read error message from response body on background thread, then display on UI thread
                        final String errorMessage;
                        String errorMessage1;
                        try {
                            errorMessage1 = response.body() != null ? response.body().string() : "Unknown error";
                        } catch (IOException e) {
                            e.printStackTrace();
                            errorMessage1 = "Error reading response body";
                        }
                        errorMessage = errorMessage1;
                        Toast.makeText(getContext(), "Profile update failed: " + response.code() + " - " + errorMessage, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            // Load the selected image into ImageView with circular crop
            Glide.with(requireContext())
                    .load(selectedImageUri)
                    .circleCrop()
                    .into(ivProfileImage);
        }
    }
}
