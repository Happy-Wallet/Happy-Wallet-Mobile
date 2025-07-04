package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar; // Thêm ProgressBar
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Model.User; // Cần thiết cho constructor User trong sendComment()
import com.example.happy_wallet_mobile.Model.Comment;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.utils.TimeFormatter;
import com.example.happy_wallet_mobile.ViewModel.Community.NewsCommentViewModel;

import java.util.Date;
import java.util.List;

public class NewsCommentFragment extends Fragment {

    NewsCommentViewModel newsCommentViewModel;

    private int postId;
    // Post currentPost; // Không cần giữ biến này ở đây nữa, sẽ lấy từ LiveData

    // UI elements from fragment_news_comment.xml
    private LinearLayout includedPostDetailContainer;
    private ImageView ivCurrentUserAvatar;
    private EditText etCommentInput;
    private ImageView ivSendComment;
    private ProgressBar progressBar; // ProgressBar cho fragment này
    private TextView tvErrorMessage; // TextView cho thông báo lỗi

    // UI elements from item_post.xml
    private ImageView ivPostAvatar;
    private TextView tvPostUsername;
    private TextView tvPostTime;
    private TextView tvPostRole;
    private ImageView ivPostImage;
    private TextView tvPostCaption;
    private LinearLayout llActivitiesContainer;
    private LinearLayout llCommentsSection;

    public NewsCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = getArguments().getInt("postId", -1);
            if (postId == -1) {
                Toast.makeText(getContext(), "Không tìm thấy ID bài đăng!", Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_comment, container, false);

        newsCommentViewModel = new ViewModelProvider(requireActivity()).get(NewsCommentViewModel.class);

        // Khởi tạo các phần tử UI
        progressBar = view.findViewById(R.id.progressBarNewsComment); //
        tvErrorMessage = view.findViewById(R.id.tvErrorMessageNewsComment); //

        includedPostDetailContainer = view.findViewById(R.id.included_post_detail); //
        ivCurrentUserAvatar = view.findViewById(R.id.ivCurrentUserAvatar); //
        etCommentInput = view.findViewById(R.id.etCommentInput); //
        ivSendComment = view.findViewById(R.id.ivSendComment); //


        if (includedPostDetailContainer != null) {
            ivPostAvatar = includedPostDetailContainer.findViewById(R.id.ivPostAvatar); //
            tvPostUsername = includedPostDetailContainer.findViewById(R.id.tvPostUsername); //
            tvPostTime = includedPostDetailContainer.findViewById(R.id.tvPostTime); //
            tvPostRole = includedPostDetailContainer.findViewById(R.id.tvPostRole); //
            ivPostImage = includedPostDetailContainer.findViewById(R.id.ivPostImage); //
            tvPostCaption = includedPostDetailContainer.findViewById(R.id.tvPostCaption); //
            llActivitiesContainer = includedPostDetailContainer.findViewById(R.id.llActivitiesContainer); //
            llCommentsSection = includedPostDetailContainer.findViewById(R.id.llCommentsSection); //
            EditText etWriteCommentInPost = includedPostDetailContainer.findViewById(R.id.etWriteComment); //
            if (etWriteCommentInPost != null) {
                etWriteCommentInPost.setVisibility(View.GONE);
            }
        }

        Glide.with(this)
                .load("https://res.cloudinary.com/dmutcpoey/image/upload/v1751546692/avatars/mucozqc2ayr6chjtb1fu.jpg") // Dummy avatar của người dùng hiện tại
                .placeholder(R.drawable.avatar_place_holder)
                .error(R.drawable.avatar_place_holder)
                .into(ivCurrentUserAvatar);


        // Quan sát LiveData từ ViewModel
        newsCommentViewModel.currentPost.observe(getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(Post post) {
                // Khi dữ liệu bài đăng thay đổi, cập nhật UI
                if (post != null) {
                    updatePostDetailUI(post);
                    tvErrorMessage.setVisibility(View.GONE);
                    includedPostDetailContainer.setVisibility(View.VISIBLE); // Hiển thị nội dung post
                } else {
                    includedPostDetailContainer.setVisibility(View.GONE); // Ẩn nội dung nếu null
                }
            }
        });

        newsCommentViewModel.isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                // Vô hiệu hóa/kích hoạt các nút/trường nhập liệu khi đang tải
                etCommentInput.setEnabled(!isLoading);
                ivSendComment.setEnabled(!isLoading);
                if (isLoading) {
                    tvErrorMessage.setVisibility(View.GONE);
                }
            }
        });

        newsCommentViewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null && !message.isEmpty()) {
                    tvErrorMessage.setText("Lỗi: " + message);
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                    includedPostDetailContainer.setVisibility(View.GONE); // Ẩn nội dung nếu có lỗi
                } else {
                    tvErrorMessage.setVisibility(View.GONE);
                }
            }
        });

        newsCommentViewModel.commentSentSuccess.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(getContext(), "Bình luận đã được gửi!", Toast.LENGTH_SHORT).show();
                    etCommentInput.setText(""); // Xóa nội dung input
                    // ViewModel đã tự động gọi fetchPostDetails() sau khi gửi thành công
                    // không cần làm gì thêm ở đây ngoài thông báo
                }
            }
        });


        // Gọi API để lấy chi tiết bài đăng (ViewModel sẽ thực hiện)
        newsCommentViewModel.fetchPostDetails(postId);

        // Thiết lập sự kiện click cho nút gửi bình luận
        ivSendComment.setOnClickListener(v -> sendComment());

        return view;
    }

    private void sendComment() {
        String commentText = etCommentInput.getText().toString().trim();
        if (commentText.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập bình luận!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Gọi phương thức addComment của ViewModel
        newsCommentViewModel.addComment(postId, commentText);
    }

    // Hàm này sẽ được gọi khi có dữ liệu post từ LiveData
    private void updatePostDetailUI(Post post) {
        if (post == null) {
            return;
        }

        // Load User Info (Header)
        if (post.getUser() != null) {
            tvPostUsername.setText(post.getUser().getUserName());
            tvPostRole.setText("Community Member");
            if (!TextUtils.isEmpty(post.getUser().getAvatarUrl())) {
                Glide.with(this)
                        .load(post.getUser().getAvatarUrl())
                        .placeholder(R.drawable.avatar_place_holder)
                        .error(R.drawable.avatar_place_holder)
                        .into(ivPostAvatar);
            } else {
                ivPostAvatar.setImageResource(R.drawable.avatar_place_holder);
            }
        }
        Date postDate = TimeFormatter.parseApiDate(post.getCreatedAt());
        tvPostTime.setText(TimeFormatter.getShortRelativeTime(postDate));

        // Post Image
        if (!TextUtils.isEmpty(post.getImageUrl())) {
            ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(post.getImageUrl())
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .into(ivPostImage);
        } else {
            ivPostImage.setVisibility(View.GONE);
        }

        // Post Caption
        tvPostCaption.setText(post.getCaption());

        // Activities (Dynamically add TextViews)
        llActivitiesContainer.removeAllViews();
        if (post.getActivities() != null && !post.getActivities().isEmpty()) {
            llActivitiesContainer.setVisibility(View.VISIBLE);
            for (FundActivity activity : post.getActivities()) {
                TextView activityTextView = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                if (llActivitiesContainer.getChildCount() > 0) {
                    params.setMargins(0, (int) getResources().getDimension(R.dimen.activity_text_margin_top), 0, 0);
                }
                activityTextView.setLayoutParams(params);
                activityTextView.setTextSize(13);
                activityTextView.setTextColor(getResources().getColor(R.color.Nautical));
                activityTextView.setText(formatActivityText(activity));
                llActivitiesContainer.addView(activityTextView);
            }
        } else {
            llActivitiesContainer.setVisibility(View.GONE);
        }

        // Comments Section (Dynamically add item_comment layouts for ALL comments)
        llCommentsSection.removeAllViews();
        if (post.getComments() != null && !post.getComments().isEmpty()) {
            llCommentsSection.setVisibility(View.VISIBLE);
            for (int i = 0; i < post.getComments().size(); i++) {
                Comment comment = post.getComments().get(i);
                View commentView = LayoutInflater.from(getContext()).inflate(R.layout.item_comment, llCommentsSection, false);

                ImageView ivCommentUserAvatar = commentView.findViewById(R.id.ivCommentUserAvatar);
                TextView tvCommentUsername = commentView.findViewById(R.id.tvCommentUsername);
                TextView tvCommentText = commentView.findViewById(R.id.tvCommentText);
                TextView tvCommentReply = commentView.findViewById(R.id.tvCommentReply); // Lấy tham chiếu đến nút Reply

                if (comment.getUser() != null) {
                    tvCommentUsername.setText(comment.getUser().getUserName());
                    if (!TextUtils.isEmpty(comment.getUser().getAvatarUrl())) {
                        Glide.with(this)
                                .load(comment.getUser().getAvatarUrl())
                                .placeholder(R.drawable.avatar_place_holder)
                                .error(R.drawable.avatar_place_holder)
                                .into(ivCommentUserAvatar);
                    } else {
                        ivCommentUserAvatar.setImageResource(R.drawable.avatar_place_holder);
                    }
                }
                tvCommentText.setText(comment.getCommentText());

                // Hiển thị thời gian bình luận (nếu có trường createdAt trong Comment model)
                // Bạn cần thêm một TextView với ID thích hợp (ví dụ: tvCommentTime) vào item_comment.xml
                // và uncomment đoạn code này
                // if (comment.getCreatedAt() != null) {
                //    Date commentDate = TimeFormatter.parseApiDate(comment.getCreatedAt());
                //    TextView tvCommentTime = commentView.findViewById(R.id.tvCommentTime);
                //    if (tvCommentTime != null) {
                //       tvCommentTime.setText(TimeFormatter.getShortRelativeTime(commentDate));
                //    }
                // }


                if (i > 0) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) commentView.getLayoutParams();
                    if (params == null) {
                        params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                    }
                    params.topMargin = (int) getResources().getDimension(R.dimen.comment_item_margin_top);
                    commentView.setLayoutParams(params);
                }

                if (tvCommentReply != null) {
                    tvCommentReply.setOnClickListener(v -> {
                        Toast.makeText(getContext(), "Reply to " + comment.getUser().getUserName(), Toast.LENGTH_SHORT).show();
                        // TODO: Logic để trả lời bình luận
                    });
                }

                llCommentsSection.addView(commentView);
            }
        } else {
            llCommentsSection.setVisibility(View.GONE);
        }
    }


    private String formatActivityText(FundActivity activity) {
        String baseText = "";
        String amountText = "";

        if (activity.getUser() != null) {
            baseText = activity.getUser().getUserName() + " ";
        }

        switch (activity.getActivityType()) {
            case "contributed":
                baseText += "contributed ";
                if (activity.getAmount() != null) {
                    amountText = String.format("%.0f$", activity.getAmount() / 1000);
                }
                baseText += amountText + " ";
                break;
            case "used":
                baseText += "used ";
                if (activity.getAmount() != null) {
                    amountText = String.format("%.0f$", activity.getAmount() / 1000);
                }
                baseText += amountText + " ";
                break;
            case "created_fund":
                baseText += "created a new fund";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += ": '" + activity.getFundName() + "'";
                }
                break;
            case "joined_fund":
                baseText += "joined fund";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += " '" + activity.getFundName() + "'";
                }
                break;
            case "left_fund":
                baseText += "left fund";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += " '" + activity.getFundName() + "'";
                }
                break;
            default:
                if (!TextUtils.isEmpty(activity.getDescription())) {
                    baseText = activity.getDescription();
                } else {
                    baseText = "performed an activity.";
                }
                break;
        }

        if (!TextUtils.isEmpty(activity.getDescription())) {
            String lowerDescription = activity.getDescription().toLowerCase();
            String lowerBaseText = baseText.toLowerCase();
            if (!lowerDescription.contains(lowerBaseText) && !lowerBaseText.contains(lowerDescription)) {
                baseText += " (" + activity.getDescription() + ")";
            }
        }
        return baseText.trim();
    }
}