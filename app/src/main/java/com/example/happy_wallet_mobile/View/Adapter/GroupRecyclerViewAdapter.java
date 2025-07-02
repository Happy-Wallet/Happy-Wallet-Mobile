package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.Color; // Import Color
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

import com.example.happy_wallet_mobile.Model.Category; // Vẫn giữ để updateCategoryList
import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.R;

import java.util.List;
import java.util.Random; // Import Random

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;

    private final Context context;
    private List<Group> groupList;
    private List<Category> categoryList; // Vẫn giữ để updateCategoryList

    // Random object for generating colors
    private final Random random = new Random();

    public void updateCategoryList(List<Category> list) {
        this.categoryList = list;
        notifyDataSetChanged();
    }

    public void updateGroupList(List<Group> list) {
        this.groupList = list;
        notifyDataSetChanged();
    }

    public GroupRecyclerViewAdapter(Context context, List<Group> groupList, List<Category> categoryList) {
        this.context = context;
        this.groupList = groupList;
        this.categoryList = categoryList;
    }

    // ViewHolder cho item Group thông thường
    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        TextView tvInitialsIcon; // Changed from ImageView ivIcon to TextView tvInitialsIcon
        TextView tvName; // Changed from tvTitle to tvName to match item_group.xml

        public ViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            tvInitialsIcon = itemView.findViewById(R.id.tvInitialsIcon); // Initialize the new TextView
            tvName = itemView.findViewById(R.id.tvName); // Initialize tvName
        }
    }

    // ViewHolder cho item "Add More"
    public static class AddViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPlusIcon;
        public AddViewHolder(View itemView) {
            super(itemView);
            ivPlusIcon = itemView.findViewById(R.id.ivPlusIcon);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == groupList.size()) ? TYPE_ADD : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_more, parent, false);
            return new AddViewHolder(view);
        } else {
            // Inflate item_group.xml for group items
            View view = LayoutInflater.from(context).inflate(R.layout.item_group, parent, false);
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
        Group item = groupList.get(position);
        itemHolder.tvName.setText(item.getName()); // Set group name to tvName

        // Generate initials from group name
        String groupName = item.getName();
        String initials = "";
        if (groupName != null && !groupName.isEmpty()) {
            String[] words = groupName.split(" ");
            if (words.length >= 2) {
                initials = (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
            } else if (words.length == 1) {
                initials = groupName.substring(0, Math.min(groupName.length(), 2)).toUpperCase();
            }
        }
        itemHolder.tvInitialsIcon.setText(initials);

        // Generate a random background color for the icon FrameLayout
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        itemHolder.flIconBackground.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);


        itemHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupList.size() + 1;
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

    // Helper (no longer needed for icon/color, but kept if categoryList is used elsewhere)
    private Category getCategoryById(int categoryId) {
        if (categoryList == null) return null; // Add null check
        for (Category category : categoryList) {
            if (category.getCategoryId() == categoryId) return category;
        }
        return null;
    }
}