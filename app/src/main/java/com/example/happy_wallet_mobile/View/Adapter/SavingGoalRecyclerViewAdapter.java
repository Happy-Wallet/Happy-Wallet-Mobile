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

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SavingGoalRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_ADD = 1;
    private Context context;
    private List<SavingGoal> savingGoalList;
    private List<Category> categoryList;
    private List<Icon> iconList;

    public SavingGoalRecyclerViewAdapter(Context context, List<SavingGoal> savingGoalList, List<Category> categoryList, List<Icon> iconList) {
        this.context = context;
        this.savingGoalList = savingGoalList;
        this.categoryList = categoryList;
        this.iconList = iconList;
    }

    public interface OnItemClickListener {
        void onItemClick(SavingGoal goal, Category category, Icon icon);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
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
        if (position == savingGoalList.size()) {
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
        int progress = current.multiply(BigDecimal.valueOf(100))
                .divide(target, RoundingMode.HALF_UP)
                .intValue();
        itemHolder.pbProgress.setProgress(progress);

        Icon icon = null;
        Category category = getCategoryById(item.getCategoryId());
        if (category != null) {
            icon = getIconById(category.getIconId());

            if (icon != null) {
                int iconResId = context.getResources().getIdentifier(icon.getIconPath(), "drawable", context.getPackageName());
                if (iconResId != 0) {
                    itemHolder.ivIcon.setImageResource(iconResId);
                }
                itemHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
            }

            try {
                int color = android.graphics.Color.parseColor(category.getColorCode());
                itemHolder.flIconBackground.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_IN);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        } else {
            icon = null; // đảm bảo luôn gán giá trị cho final biến
        }

        final Icon finalIcon = icon; // biến icon phải là final hoặc effectively final
        itemHolder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item, category, finalIcon);
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

    private Category getCategoryById(int categoryId) {
        for (Category category : categoryList) {
            if (category.getCategoryId() == categoryId) {
                return category;
            }
        }
        return null;
    }

    private Icon getIconById(int iconId) {
        for (Icon icon : iconList) {
            if (icon.getIconId() == iconId) {
                return icon;
            }
        }
        return null;
    }

}
