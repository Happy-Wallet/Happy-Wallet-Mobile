package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Group;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class GroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private Context context;
    private List<Group> GroupList;

    public GroupRecyclerViewAdapter(Context context, List<Group> groupList) {
        this.context = context;
        this.GroupList = groupList;
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
        if (position == GroupList.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ADD) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_more, parent, false);
            return new GroupRecyclerViewAdapter.AddViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_saving_goal, parent, false);
            return new GroupRecyclerViewAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ADD) {
            GroupRecyclerViewAdapter.AddViewHolder addHolder = (GroupRecyclerViewAdapter.AddViewHolder) holder;
            addHolder.ivPlusIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            addHolder.itemView.setOnClickListener(v -> {
                if (onAddClickListener != null) {
                    onAddClickListener.onAddClick();
                }
            });
            return;
        }

        GroupRecyclerViewAdapter.ViewHolder itemHolder = (GroupRecyclerViewAdapter.ViewHolder) holder;
        Group item = GroupList.get(position);

        Drawable background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_50_paolo_veronese_green);
        if (background instanceof android.graphics.drawable.GradientDrawable) {
            ((android.graphics.drawable.GradientDrawable) background).setColor(android.graphics.Color.parseColor(item.getColor()));
        }
        itemHolder.flIconBackground.setBackground(background);


        int resId = context.getResources().getIdentifier(
                item.getIconPath(),
                "drawable",
                context.getPackageName()
        );
        if (resId != 0) {
            itemHolder.ivIcon.setImageResource(resId);
        } else {
            itemHolder.ivIcon.setImageResource(R.drawable.ic_wallet);
        }

        itemHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        itemHolder.tvTitle.setText(item.getTitle());
    }


    @Override
    public int getItemCount() {
        return GroupList.size() + 1;
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    private GroupRecyclerViewAdapter.OnAddClickListener onAddClickListener;

    public void setOnAddClickListener(GroupRecyclerViewAdapter.OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }


}
