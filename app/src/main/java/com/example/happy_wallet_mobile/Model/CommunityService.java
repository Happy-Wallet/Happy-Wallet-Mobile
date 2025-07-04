package com.example.happy_wallet_mobile.service; // Đảm bảo đúng package của bạn

import com.example.happy_wallet_mobile.Model.Comment;
import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.Model.User; // Import lớp User nếu cần trả về thông tin user trong response

import java.util.List;
import java.util.Map; // Để nhận phản hồi JSON chung
import okhttp3.MultipartBody; // Để gửi file
import okhttp3.RequestBody; // Để gửi các phần khác của multipart form

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface CommunityService {

    // --- Post Endpoints ---
    // POST /posts (for creating a post with image)
    // Cần gửi multipart/form-data cho ảnh và caption
    @Multipart
    @POST("posts")
    Call<Map<String, Object>> createPost(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Part MultipartBody.Part image, // Phần file ảnh
            @Part("caption") RequestBody caption // Phần text caption
    );

    // POST /posts (for creating a post without image)
    @POST("posts")
    Call<Map<String, Object>> createPostNoImage(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Body Map<String, String> body // Chỉ gửi caption
    );

    // GET /posts (Get all posts)
    @GET("posts")
    Call<List<Post>> getPosts(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
    );

    // GET /posts/{id} (Get a single post by ID)
    @GET("posts/{id}")
    Call<Post> getPostById(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("id") int postId
    );

    // PUT /posts/{id}
    @PUT("posts/{id}")
    Call<Map<String, String>> updatePost(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("id") int postId,
            @Body Map<String, String> body
    );

    // DELETE /posts/{id}
    @DELETE("posts/{id}")
    Call<Map<String, String>> deletePost(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("id") int postId
    );


    // --- Comment Endpoints ---
    // POST /comments/{postId}
    @POST("comments/{postId}")
    Call<Map<String, Object>> addComment(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("postId") int postId,
            @Body Map<String, String> body // Chứa "commentText"
    );

    // GET /comments/{postId}
    @GET("comments/{postId}")
    Call<List<Comment>> getCommentsByPost(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("postId") int postId
    );

    // PUT /comments/{commentId}
    @PUT("comments/{commentId}")
    Call<Map<String, String>> updateComment(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("commentId") int commentId,
            @Body Map<String, String> body
    );

    // DELETE /comments/{commentId}
    @DELETE("comments/{commentId}")
    Call<Map<String, String>> deleteComment(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("commentId") int commentId
    );


    // --- Fund Activity Endpoints ---
    // POST /fund-activities
    @POST("fund-activities")
    Call<Map<String, String>> addFundActivity(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Body Map<String, Object> body // body chứa fundId, activityType, amount, description, v.v.
    );

    // GET /fund-activities/fund/{fundId}
    @GET("fund-activities/fund/{fundId}")
    Call<List<FundActivity>> getFundActivitiesByFund(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
            @Path("fundId") int fundId
    );

    // GET /fund-activities/user
    @GET("fund-activities/user")
    Call<List<FundActivity>> getUserFundActivities(
            // Authorization header sẽ được thêm tự động bởi Interceptor trong APIClient
    );
}