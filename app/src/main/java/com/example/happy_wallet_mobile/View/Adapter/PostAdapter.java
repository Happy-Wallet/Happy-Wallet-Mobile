package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.Model.Comment;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.utils.TimeFormatter; // Import lớp TimeFormatter

import java.util.Date; // Import Date
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;
    private OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int postId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Load User Info (Header)
        if (post.getUser() != null) {
            holder.tvPostUsername.setText(post.getUser().getUserName());
            holder.tvPostRole.setText("Community Member");
            if (!TextUtils.isEmpty(post.getUser().getAvatarUrl())) {
                Glide.with(context)
                        .load(post.getUser().getAvatarUrl())
                        .placeholder(R.drawable.avatar_place_holder)
                        .error(R.drawable.avatar_place_holder)
                        .into(holder.ivPostAvatar);
            } else {
                holder.ivPostAvatar.setImageResource(R.drawable.avatar_place_holder);
            }
        }
        // Gán thời gian đăng bài
        Date postDate = TimeFormatter.parseApiDate(post.getCreatedAt()); // Parse chuỗi thời gian
        holder.tvPostTime.setText(TimeFormatter.getShortRelativeTime(postDate)); // Định dạng và hiển thị


        // Post Image
        if (!TextUtils.isEmpty(post.getImageUrl())) {
            holder.ivPostImage.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(post.getImageUrl())
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .into(holder.ivPostImage);
        } else {
            holder.ivPostImage.setVisibility(View.GONE);
        }

        // Post Caption
        holder.tvPostCaption.setText(post.getCaption());

        // Activities (Dynamically add TextViews)
        holder.llActivitiesContainer.removeAllViews();
        if (post.getActivities() != null && !post.getActivities().isEmpty()) {
            holder.llActivitiesContainer.setVisibility(View.VISIBLE);
            for (FundActivity activity : post.getActivities()) {
                TextView activityTextView = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                if (holder.llActivitiesContainer.getChildCount() > 0) {
                    params.setMargins(0, (int) context.getResources().getDimension(R.dimen.activity_text_margin_top), 0, 0);
                }
                activityTextView.setLayoutParams(params);
                activityTextView.setTextSize(13);
                activityTextView.setTextColor(context.getResources().getColor(R.color.Nautical));
                activityTextView.setText(formatActivityText(activity));
                holder.llActivitiesContainer.addView(activityTextView);
            }
        } else {
            holder.llActivitiesContainer.setVisibility(View.GONE);
        }


        // Comments Section (Dynamically add item_comment layouts)
        holder.llCommentsSection.removeAllViews();
        if (post.getComments() != null && !post.getComments().isEmpty()) {
            holder.llCommentsSection.setVisibility(View.VISIBLE);
            for (int i = 0; i < post.getComments().size(); i++) {
                Comment comment = post.getComments().get(i);
                View commentView = LayoutInflater.from(context).inflate(R.layout.item_comment, holder.llCommentsSection, false);

                ImageView ivCommentUserAvatar = commentView.findViewById(R.id.ivCommentUserAvatar);
                TextView tvCommentUsername = commentView.findViewById(R.id.tvCommentUsername);
                TextView tvCommentText = commentView.findViewById(R.id.tvCommentText);

                if (comment.getUser() != null) {
                    tvCommentUsername.setText(comment.getUser().getUserName());
                    if (!TextUtils.isEmpty(comment.getUser().getAvatarUrl())) {
                        Glide.with(context)
                                .load(comment.getUser().getAvatarUrl())
                                .placeholder(R.drawable.avatar_place_holder)
                                .error(R.drawable.avatar_place_holder)
                                .into(ivCommentUserAvatar);
                    } else {
                        ivCommentUserAvatar.setImageResource(R.drawable.avatar_place_holder);
                    }
                }
                tvCommentText.setText(comment.getCommentText());

                // TODO: Hiển thị thời gian bình luận (nếu có trường createdAt trong Comment model)
                // Date commentDate = TimeFormatter.parseApiDate(comment.getCreatedAt());
                // TextView tvCommentTime = commentView.findViewById(R.id.tvCommentTime); // Bạn cần thêm ID này vào item_comment.xml
                // if (tvCommentTime != null) {
                //    tvCommentTime.setText(TimeFormatter.getShortRelativeTime(commentDate));
                // }


                if (i > 0) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) commentView.getLayoutParams();
                    if (params == null) {
                        params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                    }
                    params.topMargin = (int) context.getResources().getDimension(R.dimen.comment_item_margin_top);
                    commentView.setLayoutParams(params);
                }

                holder.llCommentsSection.addView(commentView);
            }
        } else {
            holder.llCommentsSection.setVisibility(View.GONE);
        }

        // Đặt OnClickListener cho toàn bộ item post
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(post.getPostId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPostAvatar;
        TextView tvPostUsername;
        TextView tvPostTime;
        TextView tvPostRole;
        ImageView ivPostImage;
        TextView tvPostCaption;
        LinearLayout llActivitiesContainer;
        LinearLayout llCommentsSection;
        EditText etWriteComment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPostAvatar = itemView.findViewById(R.id.ivPostAvatar);
            tvPostUsername = itemView.findViewById(R.id.tvPostUsername);
            tvPostTime = itemView.findViewById(R.id.tvPostTime);
            tvPostRole = itemView.findViewById(R.id.tvPostRole);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            tvPostCaption = itemView.findViewById(R.id.tvPostCaption);
            llActivitiesContainer = itemView.findViewById(R.id.llActivitiesContainer);
            llCommentsSection = itemView.findViewById(R.id.llCommentsSection);
            etWriteComment = itemView.findViewById(R.id.etWriteComment);
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