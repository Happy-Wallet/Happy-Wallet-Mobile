package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CATEGORY = 0;
    private static final int TYPE_ADD_MORE = 1;

    private final Context context;
    private final List<Category> categories;
    private final List<Icon> icons;
    private final OnItemClickListener listener;
    private OnAddClickListener onAddClickListener;

    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    public CategoryRecyclerViewAdapter(Context context, List<Category> categories, List<Icon> icons, OnItemClickListener listener) {
        this.context = context;
        this.categories = categories;
        this.icons = icons;
        this.listener = listener;
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(Category category, Icon icon, OnItemClickListener listener, Context context) {
            tvName.setText(category.getName());

            int iconResId = context.getResources().getIdentifier(icon.getIconPath(), "drawable", context.getPackageName());
            if (iconResId != 0) {
                ivIcon.setImageResource(iconResId);
            }

            try {
                GradientDrawable bg = (GradientDrawable) flIconBackground.getBackground();
                bg.setColor(Color.parseColor(category.getColorCode()));
            } catch (Exception ignored) {}

            itemView.setOnClickListener(v -> listener.onItemClick(category));
        }
    }

    static class AddMoreViewHolder extends RecyclerView.ViewHolder {
        public AddMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < categories.size()) ? TYPE_CATEGORY : TYPE_ADD_MORE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_CATEGORY) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_category_square, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_add_more, parent, false);
            return new AddMoreViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CATEGORY) {
            Category category = categories.get(position);
            Icon icon = getIconById(category.getIconId());
            if (icon != null) {
                ((CategoryViewHolder) holder).bind(category, icon, listener, context);
            }
        } else {
            holder.itemView.setOnClickListener(v -> {
                if (onAddClickListener != null) {
                    onAddClickListener.onAddClick();
                }
            });
        }

        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int columnCount = 3;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = screenWidth / columnCount;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return categories.size() + 1;
    }

    private Icon getIconById(int iconId) {
        for (Icon icon : icons) {
            if (icon.getIconId() == iconId) {
                return icon;
            }
        }
        return null;
    }
}
