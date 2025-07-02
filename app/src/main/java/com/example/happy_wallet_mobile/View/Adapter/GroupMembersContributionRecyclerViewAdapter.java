package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupMemberContribution;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersContributionRecyclerViewAdapter extends RecyclerView.Adapter<GroupMembersContributionRecyclerViewAdapter.ViewHolder> {

    private List<GroupMemberContribution> memberContributionList = new ArrayList<>();

    public GroupMembersContributionRecyclerViewAdapter(List<GroupMemberContribution> memberContributionList){
        this.memberContributionList = (memberContributionList != null) ? memberContributionList : new ArrayList<>();
    }

    public void updateMemberContributionList(List<GroupMemberContribution> list) {
        this.memberContributionList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupMemberContribution item = memberContributionList.get(position);

        holder.tvMemberName.setText(item.getUser().getUserName());
        holder.tvMemberRole.setText(item.getGroupRole());
        holder.tvTotalContribution.setText(CurrencyUtility.format(item.getTotalIncomeContribution()));
    }

    @Override
    public int getItemCount() {
        return memberContributionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMemberName, tvMemberRole, tvTotalContribution;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvMemberRole = itemView.findViewById(R.id.tvMemberRole);
            tvTotalContribution = itemView.findViewById(R.id.tvTotalContribution);
        }
    }
}

