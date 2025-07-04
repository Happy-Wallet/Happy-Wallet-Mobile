package com.example.happy_wallet_mobile.ViewModel.Community;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CommunityService;
import com.google.gson.Gson; // Import Gson

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewsViewModel extends ViewModel {

    private MutableLiveData<Boolean> _postCreatedSuccess = new MutableLiveData<>();
    public LiveData<Boolean> postCreatedSuccess = _postCreatedSuccess;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;

    private MutableLiveData<List<FundActivity>> _selectedActivities = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<FundActivity>> selectedActivities = _selectedActivities;

    private CommunityService communityService;

    public AddNewsViewModel() {
        communityService = APIClient.getRetrofit().create(CommunityService.class);
    }

    public void updateAllSelectedActivities(List<FundActivity> activities) {
        _selectedActivities.setValue(new ArrayList<>(activities));
    }

    public void addSelectedActivity(FundActivity activity) {
        List<FundActivity> currentList = _selectedActivities.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        if (!currentList.contains(activity)) {
            currentList.add(activity);
            _selectedActivities.setValue(currentList);
        }
    }

    public void removeSelectedActivity(FundActivity activity) {
        List<FundActivity> currentList = _selectedActivities.getValue();
        if (currentList != null) {
            currentList.remove(activity);
            _selectedActivities.setValue(currentList);
        }
    }

    // Phương thức chính để tạo bài đăng
    public void createPost(Uri imageUri, String caption, android.content.Context context) {
        _isLoading.setValue(true);
        _errorMessage.setValue(null);
        _postCreatedSuccess.setValue(false);

        // Lấy danh sách ID hoạt động đã chọn
        List<Integer> activityIds = new ArrayList<>();
        if (_selectedActivities.getValue() != null) {
            for (FundActivity activity : _selectedActivities.getValue()) {
                activityIds.add(activity.getActivityId());
            }
        }
        // Chuyển List<Integer> sang JSON String
        String activityIdsJson = new Gson().toJson(activityIds);

        if (imageUri != null) {
            uploadImageAndCreatePost(imageUri, caption, activityIdsJson, context);
        } else {
            createPostNoImage(caption, activityIdsJson);
        }
    }

    private void uploadImageAndCreatePost(Uri imageUri, String caption, String activityIdsJson, android.content.Context context) {
        File file = null;
        try {
            file = new File(context.getCacheDir(), "upload_image_" + System.currentTimeMillis() + ".jpg");
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
            inputStream.close();

            RequestBody requestFile = RequestBody.create(MediaType.parse(context.getContentResolver().getType(imageUri)), file);
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            RequestBody captionPart = RequestBody.create(MediaType.parse("text/plain"), caption);

            // Tạo RequestBody cho activity_ids
            RequestBody activityIdsPart = RequestBody.create(MediaType.parse("application/json"), activityIdsJson);


            final File finalFile = file;
            communityService.createPost(imagePart, captionPart, activityIdsPart).enqueue(new Callback<Map<String, Object>>() { // ĐÃ THAY ĐỔI
                @Override
                public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                    _isLoading.setValue(false);
                    if (response.isSuccessful()) {
                        _postCreatedSuccess.setValue(true);
                        _selectedActivities.setValue(new ArrayList<>()); // Reset selected activities
                        Log.d("AddNewsViewModel", "Post with image created successfully.");
                    } else {
                        String errorBody = "";
                        try {
                            if (response.errorBody() != null) {
                                errorBody = response.errorBody().string();
                            }
                        } catch (Exception e) {
                            Log.e("AddNewsViewModel", "Error parsing error body", e);
                        }
                        String msg = "Failed to create post with image: " + response.code() + " - " + errorBody;
                        _errorMessage.setValue(msg);
                        Log.e("AddNewsViewModel", msg);
                    }
                    if (finalFile.exists()) {
                        finalFile.delete();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                    _isLoading.setValue(false);
                    String msg = "API Call Failed for image post: " + t.getMessage();
                    _errorMessage.setValue(msg);
                    Log.e("AddNewsViewModel", msg, t);
                    if (finalFile.exists()) {
                        finalFile.delete();
                    }
                }
            });

        } catch (IOException e) {
            _isLoading.setValue(false);
            String msg = "Error creating temp file or uploading: " + e.getMessage();
            _errorMessage.setValue(msg);
            Log.e("AddNewsViewModel", msg, e);
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    private void createPostNoImage(String caption, String activityIdsJson) { // ĐÃ THAY ĐỔI
        Map<String, Object> body = new HashMap<>(); // Đã thay đổi thành Map<String, Object>
        body.put("caption", caption);
        body.put("activity_ids", new Gson().fromJson(activityIdsJson, List.class)); // Chuyển lại JSON String thành List nếu cần, hoặc gửi JSON String trực tiếp

        communityService.createPostNoImage(body).enqueue(new Callback<Map<String, Object>>() { // ĐÃ THAY ĐỔI
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful()) {
                    _postCreatedSuccess.setValue(true);
                    _selectedActivities.setValue(new ArrayList<>()); // Reset selected activities
                    Log.d("AddNewsViewModel", "Post without image created successfully.");
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("AddNewsViewModel", "Error parsing error body", e);
                    }
                    String msg = "Failed to create post no image: " + response.code() + " - " + errorBody;
                    _errorMessage.setValue(msg);
                    Log.e("AddNewsViewModel", msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                _isLoading.setValue(false);
                String msg = "API Call Failed for no image post: " + t.getMessage();
                _errorMessage.setValue(msg);
                Log.e("AddNewsViewModel", msg, t);
            }
        });
    }
}