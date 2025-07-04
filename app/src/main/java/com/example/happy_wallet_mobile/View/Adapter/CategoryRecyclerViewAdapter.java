package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_CATEGORY = 0;
    private static final int TYPE_ADD_MORE = 1;

    private final Context context;
    private List<Category> allCategories;
    private eType typeFilter;
    private List<Category> filteredCategories;
    private final OnItemClickListener listener;
    private OnAddClickListener onAddClickListener;
    private int selectedPosition = -1;

    public void updateCategories(List<Category> list) {
        this.allCategories = list;
        filterCategories();
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    public CategoryRecyclerViewAdapter(Context context, List<Category> categories, eType typeFilter, OnItemClickListener listener) {
        this.context = context;
        this.allCategories = categories;
        this.typeFilter = typeFilter;
        this.listener = listener;
        filterCategories(); // lọc ngay ban đầu
    }

    private void filterCategories() {
        filteredCategories = new ArrayList<>();
        for (Category c : allCategories) {
            if (c.getType() == typeFilter) {
                filteredCategories.add(c);
            }
        }
    }

    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvName;
        LinearLayout lnlItemContainer;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvName = itemView.findViewById(R.id.tvName);
            lnlItemContainer = itemView.findViewById(R.id.lnlItemContainer);
        }

        public void bind(Category category, OnItemClickListener listener, Context context, int position, CategoryRecyclerViewAdapter adapter) {
            tvName.setText(category.getName());

            // Gán icon từ res
            ivIcon.setImageResource(category.getIconRes());

            // Gán màu từ res
            GradientDrawable bg = (GradientDrawable) flIconBackground.getBackground();
            bg.setColor(ContextCompat.getColor(context, category.getColorRes()));

            itemView.setOnClickListener(v -> {
                adapter.setSelectedPosition(position);
                listener.onItemClick(category);
            });
        }
    }

    static class AddMoreViewHolder extends RecyclerView.ViewHolder {
        public AddMoreViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position < filteredCategories.size()) ? TYPE_CATEGORY : TYPE_ADD_MORE;
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
            Category category = filteredCategories.get(position);
            CategoryViewHolder categoryHolder = (CategoryViewHolder) holder;
            categoryHolder.bind(category, listener, context, position, this);

            // Viền khi được chọn
            categoryHolder.lnlItemContainer.setBackgroundTintList(
                    ColorStateList.valueOf(
                            ContextCompat.getColor(context,
                                    selectedPosition == position
                                            ? R.color.Silver_Phoenix
                                            : R.color.white
                            )
                    )
            );
        } else {
            holder.itemView.setOnClickListener(v -> {
                if (onAddClickListener != null) {
                    onAddClickListener.onAddClick();
                }
            });
        }

        // Chia 3 cột ngang bằng nhau
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int columnCount = 3;
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = screenWidth / columnCount;
        holder.itemView.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return filteredCategories.size() + 1;
    }

    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(oldPosition);
        notifyItemChanged(position);
    }

    public void setSelectedCategory(int categoryId) {
        for (int i = 0; i < filteredCategories.size(); i++) {
            if (filteredCategories.get(i).getCategoryId() == categoryId) {
                setSelectedPosition(i);
                break;
            }
        }
    }

}
