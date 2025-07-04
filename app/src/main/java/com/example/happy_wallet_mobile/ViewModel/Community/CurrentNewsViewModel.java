package com.example.happy_wallet_mobile.ViewModel.Community;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Data.Remote.APIClient; // Import APIClient
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CommunityService;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentNewsViewModel extends ViewModel {

    private MutableLiveData<List<Post>> _posts = new MutableLiveData<>();
    public LiveData<List<Post>> posts = _posts; // LiveData công khai để Fragment quan sát

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading; // LiveData để báo hiệu trạng thái loading

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage; // LiveData để báo hiệu lỗi

    private CommunityService communityService;

    public CurrentNewsViewModel() {
        // Khởi tạo CommunityService khi ViewModel được tạo
        communityService = APIClient.getRetrofit().create(CommunityService.class);
        fetchPosts(); // Tự động fetch bài đăng khi ViewModel được khởi tạo
    }

    public void fetchPosts() {
        _isLoading.setValue(true); // Bắt đầu tải, đặt trạng thái loading là true
        _errorMessage.setValue(null); // Xóa thông báo lỗi cũ

        communityService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                _isLoading.setValue(false); // Kết thúc tải, đặt trạng thái loading là false
                if (response.isSuccessful() && response.body() != null) {
                    _posts.setValue(response.body()); // Cập nhật LiveData với dữ liệu mới
                    Log.d("CurrentNewsViewModel", "Fetched " + response.body().size() + " posts successfully.");
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("CurrentNewsViewModel", "Error parsing error body", e);
                    }
                    String msg = "Failed to fetch posts: " + response.code() + " - " + errorBody;
                    _errorMessage.setValue(msg); // Cập nhật LiveData với thông báo lỗi
                    Log.e("CurrentNewsViewModel", msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                _isLoading.setValue(false); // Kết thúc tải, đặt trạng thái loading là false
                String msg = "API Call Failed: " + t.getMessage();
                _errorMessage.setValue(msg); // Cập nhật LiveData với thông báo lỗi
                Log.e("CurrentNewsViewModel", msg, t);
            }
        });
    }
}