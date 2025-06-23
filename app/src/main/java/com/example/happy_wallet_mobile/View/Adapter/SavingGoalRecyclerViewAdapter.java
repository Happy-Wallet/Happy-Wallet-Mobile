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

    public SavingGoalRecyclerViewAdapter(Context context, List<SavingGoal> savingGoalList) {
        this.context = context;
        this.savingGoalList = savingGoalList;
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

        itemHolder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);
        itemHolder.tvTitle.setText(item.getName());

        BigDecimal current = item.getCurrentAmount();
        BigDecimal target = item.getTargetAmount();
        int progress = current.multiply(BigDecimal.valueOf(100))
                .divide(target, RoundingMode.HALF_UP)
                .intValue();
        itemHolder.pbProgress.setMax(100);
        itemHolder.pbProgress.setProgress(progress);

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

}
