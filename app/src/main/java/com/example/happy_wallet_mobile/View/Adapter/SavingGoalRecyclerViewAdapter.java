package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
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

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class SavingGoalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private final Context context;
    private List<Category> categoryList = new ArrayList<>();
    private List<SavingGoal> savingGoalList = new ArrayList<>();


    public SavingGoalRecyclerViewAdapter(Context context, List<SavingGoal> savingGoalList, List<Category> categoryList) {
        this.context = context;
        this.savingGoalList = savingGoalList;
        this.categoryList = categoryList;
    }

    public void updateSavingGoals(List<SavingGoal> list) {
        this.savingGoalList = (list != null) ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void updateCategories(List<Category> list) {
        this.categoryList = list != null ? list : new ArrayList<>();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvTitle;
        ProgressBar pbProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            flIconBackground = itemView.findViewById(R.id.flIconBackground);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            pbProgress = itemView.findViewById(R.id.pbProgress);
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
        return (position == savingGoalList.size()) ? TYPE_ADD : TYPE_ITEM;
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
                if (onAddClickListener != null) {
                    onAddClickListener.onAddClick();
                }
            });
            return;
        }

        ViewHolder itemHolder = (ViewHolder) holder;
        SavingGoal item = savingGoalList.get(position);
        itemHolder.tvTitle.setText(item.getName());

        BigDecimal current = item.getCurrentAmount();
        BigDecimal target = item.getTargetAmount();

        int progress = 0;
        if (target != null && target.compareTo(BigDecimal.ZERO) > 0) {
            progress = current.multiply(BigDecimal.valueOf(100))
                    .divide(target, 2, RoundingMode.HALF_UP)
                    .intValue();
        }
        itemHolder.pbProgress.setProgress(progress);

        // Lấy category và icon resource ID
        Category category = getCategoryById(item.getCategoryId());
        if (category != null) {
            int iconResId = category.getIconRes();
            if (iconResId != 0) {
                itemHolder.ivIcon.setImageResource(iconResId);
                itemHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            }

            try {
                int color = ContextCompat.getColor(context, category.getColorRes());
                itemHolder.flIconBackground.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        itemHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item, category);
            }
        });
    }


    @Override
    public int getItemCount() {
        return savingGoalList.size() + 1;
    }

    public interface OnAddClickListener {
        void onAddClick();
    }

    private OnAddClickListener onAddClickListener;
    public void setOnAddClickListener(OnAddClickListener listener) {
        this.onAddClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(SavingGoal goal, Category category);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private Category getCategoryById(int categoryId) {
        if (categoryList == null) return null;
        for (Category category : categoryList) {
            if (category.getCategoryId() == categoryId) {
                return category;
            }
        }
        return null;
    }

}
