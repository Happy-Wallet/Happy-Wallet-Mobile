package com.example.happy_wallet_mobile.Data.Remote.ApiInterface;

import com.example.happy_wallet_mobile.Model.Comment;
import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.Model.User;

import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface CommunityService {

    // --- Authentication Endpoints (giữ nguyên) ---
    @POST("auth/register")
    Call<Map<String, Object>> registerUser(@Body Map<String, String> body);

    @POST("auth/login")
    Call<Map<String, Object>> loginUser(@Body Map<String, Object> body); // body có thể là Map<String, Object> nếu có cả JWT token


    // --- Post Endpoints ---
    // POST /posts (for creating a post with image AND activities)
    @Multipart
    @POST("posts")
    Call<Map<String, Object>> createPost(
            @Part MultipartBody.Part image, // Phần file ảnh
            @Part("caption") RequestBody caption, // Phần text caption
            @Part("activity_ids") RequestBody activityIds // PHẦN MỚI: Danh sách ID hoạt động
    );

    // POST /posts (for creating a post without image BUT with activities)
    @POST("posts")
    Call<Map<String, Object>> createPostNoImage(
            @Body Map<String, Object> body // PHẦN MỚI: body bây giờ sẽ chứa caption VÀ activity_ids
    );

    // --- Các API khác giữ nguyên như bạn đã có ---
    @GET("posts")
    Call<List<Post>> getPosts();

    @GET("posts/{id}")
    Call<Post> getPostById(
            @Path("id") int postId
    );

    @PUT("posts/{id}")
    Call<Map<String, String>> updatePost(
            @Path("id") int postId,
            @Body Map<String, String> body
    );

    @DELETE("posts/{id}")
    Call<Map<String, String>> deletePost(
            @Path("id") int postId
    );

    @POST("comments/{postId}")
    Call<Map<String, Object>> addComment(
            @Path("postId") int postId,
            @Body Map<String, String> body
    );

    @GET("comments/{postId}")
    Call<List<Comment>> getCommentsByPost(
            @Path("postId") int postId
    );

    @PUT("comments/{commentId}")
    Call<Map<String, String>> updateComment(
            @Path("commentId") int commentId,
            @Body Map<String, String> body
    );

    @DELETE("comments/{commentId}")
    Call<Map<String, String>> deleteComment(
            @Path("commentId") int commentId
    );

    @POST("fund-activities")
    Call<Map<String, String>> addFundActivity(
            @Body Map<String, Object> body
    );

    @GET("fund-activities/fund/{fundId}")
    Call<List<FundActivity>> getFundActivitiesByFund(
            @Path("fundId") int fundId
    );

    @GET("fund-activities/user")
    Call<List<FundActivity>> getUserFundActivities();
}