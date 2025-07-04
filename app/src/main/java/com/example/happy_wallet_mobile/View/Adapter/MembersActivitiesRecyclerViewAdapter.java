package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.GroupTransaction;
import com.example.happy_wallet_mobile.Model.User;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;

import java.util.ArrayList;
import java.util.List;

public class MembersActivitiesRecyclerViewAdapter extends RecyclerView.Adapter<MembersActivitiesRecyclerViewAdapter.ViewHolder> {

    private List<GroupTransaction> transactionList;
    private List<User> memberList;
    private List<Category> categoryList;

    public MembersActivitiesRecyclerViewAdapter(
            List<GroupTransaction> transactions,
            List<User> members,
            List<Category> categories) {
        this.transactionList = transactions != null ? transactions : new ArrayList<>();
        this.memberList = members != null ? members : new ArrayList<>();
        this.categoryList = categories != null ? categories : new ArrayList<>();
    }

    // Update data
    public void updateActivities(List<GroupTransaction> transactions){
        this.transactionList = transactions != null ? transactions : new ArrayList<>();
    }

    public void updateGroupMembers(List<User> members){
        this.memberList = members != null ? members : new ArrayList<>();
    }

    public void updateCategories(List<Category> categories){
        this.categoryList = categories != null ? categories : new ArrayList<>();
    }

    public void refresh() {
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
        GroupTransaction transaction = transactionList.get(position);

        // Lấy tên user
        String userName = memberList.stream()
                .filter(u -> u.getId() == transaction.getUserId())
                .map(User::getUserName)
                .findFirst()
                .orElse("Unknown");

        // Lấy category
        Category category = categoryList.stream()
                .filter(c -> c.getCategoryId() == transaction.getCategoryId())
                .findFirst()
                .orElse(null);

        String categoryName = category != null ? category.getName() : "Category";
        int categoryColorRes = category != null ? category.getColorRes() : R.color.black;

        // Bind data
        holder.tvUserName.setText(userName);
        holder.tvCategoryName.setText(categoryName);
        holder.tvAmount.setText(CurrencyUtility.format1(transaction.getAmount()));

        // Đặt màu category
        holder.tvCategoryName.setTextColor(
                holder.itemView.getContext().getColor(categoryColorRes)
        );

        // Đặt màu amount theo loại
        if (transaction.getType() == eType.EXPENSE) {
            holder.tvAmount.setTextColor(holder.itemView.getResources().getColor(R.color.Radishical, null));
        } else {
            holder.tvAmount.setTextColor(holder.itemView.getResources().getColor(R.color.Paolo_Veronese_Green, null));
        }
    }


    @Override
    public int getItemCount() {
        return transactionList != null ? transactionList.size() : 0;
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
