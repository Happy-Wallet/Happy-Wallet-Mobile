package com.example.happy_wallet_mobile.ViewModel.Community;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Model.Comment;
import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Data.Remote.APIClient; // Import APIClient
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CommunityService;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsCommentViewModel extends ViewModel {

    private MutableLiveData<Post> _currentPost = new MutableLiveData<>();
    public LiveData<Post> currentPost = _currentPost;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;

    // LiveData để báo hiệu bình luận đã được gửi thành công
    private MutableLiveData<Boolean> _commentSentSuccess = new MutableLiveData<>();
    public LiveData<Boolean> commentSentSuccess = _commentSentSuccess;


    private CommunityService communityService;

    public NewsCommentViewModel() {
        // Khởi tạo CommunityService khi ViewModel được tạo
        communityService = APIClient.getRetrofit().create(CommunityService.class);
    }

    public void fetchPostDetails(int postId) {
        _isLoading.setValue(true); // Bắt đầu tải
        _errorMessage.setValue(null); // Xóa lỗi cũ

        communityService.getPostById(postId).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                _isLoading.setValue(false); // Kết thúc tải
                if (response.isSuccessful() && response.body() != null) {
                    _currentPost.setValue(response.body()); // Cập nhật LiveData với bài đăng
                    Log.d("NewsCommentViewModel", "Fetched post details for ID: " + postId);
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("NewsCommentViewModel", "Error parsing error body", e);
                    }
                    String msg = "Failed to fetch post details: " + response.code() + " - " + errorBody;
                    _errorMessage.setValue(msg);
                    Log.e("NewsCommentViewModel", msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
                _isLoading.setValue(false); // Kết thúc tải
                String msg = "API Call Failed for post details: " + t.getMessage();
                _errorMessage.setValue(msg);
                Log.e("NewsCommentViewModel", msg, t);
            }
        });
    }

    public void addComment(int postId, String commentText) {
        _isLoading.setValue(true); // Đặt trạng thái loading cho việc gửi comment
        _errorMessage.setValue(null); // Xóa lỗi cũ
        _commentSentSuccess.setValue(false); // Reset trạng thái gửi thành công

        Map<String, String> body = new HashMap<>();
        body.put("commentText", commentText);

        communityService.addComment(postId, body).enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, Object>> call, @NonNull Response<Map<String, Object>> response) {
                _isLoading.setValue(false); // Kết thúc tải
                if (response.isSuccessful()) {
                    _commentSentSuccess.setValue(true); // Báo hiệu gửi thành công
                    Log.d("NewsCommentViewModel", "Comment added successfully to post " + postId);
                    // Sau khi gửi thành công, tải lại chi tiết bài đăng để cập nhật bình luận mới
                    fetchPostDetails(postId);
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("NewsCommentViewModel", "Error parsing error body", e);
                    }
                    String msg = "Failed to add comment: " + response.code() + " - " + errorBody;
                    _errorMessage.setValue(msg);
                    Log.e("NewsCommentViewModel", msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, Object>> call, @NonNull Throwable t) {
                _isLoading.setValue(false); // Kết thúc tải
                String msg = "API Call Failed for add comment: " + t.getMessage();
                _errorMessage.setValue(msg);
                Log.e("NewsCommentViewModel", msg, t);
            }
        });
    }
}