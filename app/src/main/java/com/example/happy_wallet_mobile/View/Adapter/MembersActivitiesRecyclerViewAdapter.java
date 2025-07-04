package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Model.GroupTransactionItem;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;

import java.util.ArrayList;
import java.util.List;

public class MembersActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<MembersActivitiesRecyclerViewAdapter.ViewHolder> {

    private List<GroupTransactionItem> transactionItemList;
    private Context context;

    public MembersActivitiesRecyclerViewAdapter(Context context, List<GroupTransactionItem> items) {
        this.context = context;
        this.transactionItemList = items != null ? items : new ArrayList<>();
    }

    public void updateActivities(List<GroupTransactionItem> items){
        this.transactionItemList = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MembersActivitiesRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_member_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MembersActivitiesRecyclerViewAdapter.ViewHolder holder, int position) {
        GroupTransactionItem item = transactionItemList.get(position);

        // User name
        String userName = item.getUsername() != null ? item.getUsername() : "Unknown";
        holder.tvUserName.setText(userName);

        // Category
        GroupTransactionItem.Category category = item.getCategory();
        String categoryName = (category != null && category.getName() != null) ? category.getName() : "Category";
        int categoryColorRes = (category != null) ? category.getColor_res() : R.color.black;

        holder.tvCategoryName.setText(categoryName);
        holder.tvCategoryName.setTextColor(holder.itemView.getContext().getColor(categoryColorRes));

        // Amount
        holder.tvAmount.setText(CurrencyUtility.format1(item.getAmount()));
        if ("EXPENSE".equalsIgnoreCase(item.getType())) {
            holder.tvAmount.setTextColor(holder.itemView.getContext().getColor(R.color.Radishical));
        } else {
            holder.tvAmount.setTextColor(holder.itemView.getContext().getColor(R.color.Paolo_Veronese_Green));
        }

        Glide.with(context)
                .load(item.getAvatar_url())
                .placeholder(R.drawable.ic_analysis)
                .error(R.drawable.ic_analysis)
                .circleCrop()
                .into(holder.ivAvatar);
    }

    @Override
    public int getItemCount() {
        return transactionItemList != null ? transactionItemList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvUserName, tvCategoryName, tvAmount;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvAmount = itemView.findViewById(R.id.tvAmount);
        }
    }
}
