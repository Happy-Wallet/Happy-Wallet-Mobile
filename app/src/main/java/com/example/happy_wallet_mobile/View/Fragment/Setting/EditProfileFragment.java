package com.example.happy_wallet_mobile.View.Fragment.Setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.UserService;
import com.example.happy_wallet_mobile.ViewModel.Setting.EditProfileViewModel;
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

    EditProfileViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        viewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), loading -> {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        });

        viewModel.getMessage().observe(getViewLifecycleOwner(), msg -> {
            if (msg != null) Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(getContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Initialize View components
        ivProfileImage = view.findViewById(R.id.ivProfileImage);
        etUserName = view.findViewById(R.id.etUserName);
        etDateOfBirth = view.findViewById(R.id.etDateOfBirth);
        tvSave = view.findViewById(R.id.tvSave);
        tvCancel = view.findViewById(R.id.tvCancel);
        progressBar = view.findViewById(R.id.progressBar);


        Log.d("EditProfie", UserPreferences.getUser().getUserName() + " : " + UserPreferences.getUser().getDateOfBirth());
// Set name
        etUserName.setText(UserPreferences.getUser().getUserName());
// Set date of birth formatted
        Date dob = UserPreferences.getUser().getDateOfBirth();
        if (dob != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            etDateOfBirth.setText(sdf.format(dob));
        }



        // Set OnClickListener for ImageView to pick avatar image
        ivProfileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        // Set OnClickListener for Date of Birth EditText to show DatePicker
        etDateOfBirth.setOnClickListener(v -> showDatePicker());

        // Set OnClickListener for Save button
        tvSave.setOnClickListener(v -> {
            String username = etUserName.getText().toString().trim();
            String dobStr  = etDateOfBirth.getText().toString().trim();

            byte[] imageBytes = null;
            if (selectedImageUri != null) {
                try (InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedImageUri)) {
                    imageBytes = IOUtils.toByteArray(inputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Image error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            viewModel.updateProfile(username, dobStr , imageBytes);
        });

        // Set OnClickListener for Cancel button to go back to previous screen
        tvCancel.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            etDateOfBirth.setText(sdf.format(calendar.getTime()));
        });
    }


}
