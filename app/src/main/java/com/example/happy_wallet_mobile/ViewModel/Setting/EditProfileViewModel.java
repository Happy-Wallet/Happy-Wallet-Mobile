package com.example.happy_wallet_mobile.ViewModel.Setting;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.Response.User.UserResponse;
import com.example.happy_wallet_mobile.Data.Repository.UserRepository;
import com.example.happy_wallet_mobile.Model.User;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EditProfileViewModel extends ViewModel {
    UserRepository userRepository = new UserRepository();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();
    public void resetUpdateSuccess(){
        updateSuccess.postValue(false);
    }
    private MediatorLiveData<User> _userProfile = new MediatorLiveData<>();
    public LiveData<User> getUserProfile(){
        return _userProfile;
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<Boolean> getUpdateSuccess() { return updateSuccess; }

    public void updateProfile(String username, String dob, byte[] imageBytes) {
        String token = UserPreferences.getToken();
        if (token == null) {
            message.postValue("Authentication token not found.");
            return;
        }

        isLoading.postValue(true);

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("date_of_birth", dob);

        if (imageBytes != null) {
            builder.addFormDataPart("avatar", "avatar.jpg",
                    RequestBody.create(imageBytes, MediaType.parse("image/*")));
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
            public void onFailure(okhttp3.Call call, java.io.IOException e) {
                isLoading.postValue(false);
                message.postValue("Profile update failed: " + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws java.io.IOException {
                isLoading.postValue(false);
                if (response.isSuccessful()) {
                    updateSuccess.postValue(true);
                } else {
                    String errorBody = response.body() != null ? response.body().string() : "Unknown error";
                    message.postValue("Update failed: " + response.code() + " - " + errorBody);
                }
            }
        });
    }

    public void fetchUserProfileAndSave() {
        String token = UserPreferences.getToken();
        LiveData<UserResponse> source = userRepository.getUserProfile("Bearer " + token);



        _userProfile.addSource(source, userResponse -> {
            Log.d("fetch profile", "id: " + userResponse.getId() +
                    " name: " + userResponse.getUsername() +
                    " email: " + userResponse.getEmail() +
                    " avatar: " + userResponse.getAvatarUrl() +
                    " dob: " + userResponse.getDateOfBirth());
            if (userResponse != null) {
                // Convert UserResponse to User model
                User user = new User();
                user.setId(userResponse.getId());
                user.setEmail(userResponse.getEmail());
                user.setUserName(userResponse.getUsername());
                user.setAvatarUrl(userResponse.getAvatarUrl());
                user.setDateOfBirth(userResponse.getDateOfBirth());

                // Save to preferences
                UserPreferences.saveUser(user, token);

                Log.d("UserPreferences", "id: " + UserPreferences.getUser().getId() +
                        " name: " + UserPreferences.getUser().getUserName() +
                        " dob: " + UserPreferences.getUser().getDateOfBirth() +
                        " avatar: " + UserPreferences.getUser().getAvatarUrl());

                _userProfile.setValue(user);
            } else {
                _userProfile.setValue(null);
            }
            _userProfile.removeSource(source);
        });
    }
}
