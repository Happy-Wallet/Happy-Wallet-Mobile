package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.SavingGoal;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class SavingGoalRecyclerViewAdapter extends RecyclerView.Adapter<SavingGoalRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<SavingGoal> savingGoalList;

    public SavingGoalRecyclerViewAdapter(Context context, List<SavingGoal> savingGoalList) {
        this.context = context;
        this.savingGoalList = savingGoalList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        ProgressBar pbProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            pbProgress = itemView.findViewById(R.id.pbProgress);
        }
    }

    @NonNull
    @Override
    public SavingGoalRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saving_goal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingGoalRecyclerViewAdapter.ViewHolder holder, int position) {
        SavingGoal item = savingGoalList.get(position);

        int resId = context.getResources().getIdentifier(
                item.getIconPath(),
                "drawable",
                context.getPackageName()
        );
        if (resId != 0) {
            holder.ivIcon.setImageResource(resId);
        } else {
            holder.ivIcon.setImageResource(R.drawable.ic_wallet);
        }

        holder.ivIcon.setColorFilter(ContextCompat.getColor(context, R.color.white), PorterDuff.Mode.SRC_IN);

        holder.tvTitle.setText(item.getTitle());
        holder.pbProgress.setMax(Integer.parseInt(item.getTarget()));
        holder.pbProgress.setProgress(Integer.parseInt(item.getCurrentMoney()));
    }

    @Override
    public int getItemCount() {
        return savingGoalList.size();
    }
}
