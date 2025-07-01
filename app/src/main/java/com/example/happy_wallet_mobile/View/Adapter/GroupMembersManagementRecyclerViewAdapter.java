package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupMemberContribution;
import com.example.happy_wallet_mobile.View.Utilities.CurrencyUtility;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersManagementRecyclerViewAdapter extends RecyclerView.Adapter<GroupMembersManagementRecyclerViewAdapter.ViewHolder> {

    private List<GroupMemberContribution> memberContributionList = new ArrayList<>();

    public GroupMembersManagementRecyclerViewAdapter(List<GroupMemberContribution> memberContributionList){
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
                .inflate(R.layout.item_manage_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GroupMemberContribution item = memberContributionList.get(position);

        holder.tvMemberName.setText(item.getUser().getUserName());
        holder.tvMemberRole.setText(item.getGroupRole());


        holder.tvOptions.setOnClickListener(v -> {
            showPopupMenu(v, item, position);
        });
    }

    private void showPopupMenu(View anchor, GroupMemberContribution item, int position) {
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_group_member_options, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_delete) {
                // Xử lý xóa

                memberContributionList.remove(position);
                notifyItemRemoved(position);
                return true;
            }
            else if (menuItem.getItemId() == R.id.action_to_admin) {
                // Xử lý lên admin

                notifyItemChanged(position);
                return true;
            }
            else if (menuItem.getItemId() == R.id.action_to_member) {
                // Xử lý xuống member

                notifyItemChanged(position);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return memberContributionList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMemberName, tvMemberRole, tvOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvMemberRole = itemView.findViewById(R.id.tvMemberRole);
            tvOptions = itemView.findViewById(R.id.tvOptions);
        }
    }


}



