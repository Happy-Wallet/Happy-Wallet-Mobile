package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;

    private final Context context;
    private List<Group> GroupList;
    private List<Category> categoryList;

    public GroupRecyclerViewAdapter(Context context, List<Group> groupList, List<Category> categoryList) {
        this.context = context;
        this.GroupList = groupList;
        this.categoryList = categoryList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public static class AddViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlusIcon;
        public AddViewHolder(View itemView) {
            super(itemView);
            ivPlusIcon = itemView.findViewById(R.id.ivPlusIcon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == GroupList.size()) ? TYPE_ADD : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_more, parent, false);
            return new AddViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_saving_goal, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            AddViewHolder addHolder = (AddViewHolder) holder;
            addHolder.ivPlusIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            addHolder.itemView.setOnClickListener(v -> {
                if (onAddClickListener != null) onAddClickListener.onAddClick();
            });
            return;
        }

        ViewHolder itemHolder = (ViewHolder) holder;
        Group item = GroupList.get(position);
        itemHolder.tvTitle.setText(item.getName());

        // Tìm Category tương ứng
        Category category = getCategoryById(item.getCategoryId());
        if (category != null) {
            // Gán màu nền
            int colorRes = category.getColorRes();
            itemHolder.flIconBackground.getBackground()
                    .setColorFilter(ContextCompat.getColor(context, colorRes), PorterDuff.Mode.SRC_IN);

            // Gán icon
            itemHolder.ivIcon.setImageResource(category.getIconRes());
            itemHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        }

        itemHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return GroupList.size() + 1;
    }

    // Listeners
    public interface OnAddClickListener {
        void onAddClick();
    }
    private OnAddClickListener onAddClickListener;
    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Group group);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    // Helper
    private Category getCategoryById(int categoryId) {
        for (Category category : categoryList) {
            if (category.getCategoryId() == categoryId) return category;
        }
        return null;
    }
}
