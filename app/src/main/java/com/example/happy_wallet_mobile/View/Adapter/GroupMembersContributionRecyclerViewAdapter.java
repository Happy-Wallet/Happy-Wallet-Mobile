package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // Import ImageView
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import Glide
import com.example.happy_wallet_mobile.Model.GroupMember; // Sử dụng GroupMember trực tiếp
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.UIModel.GroupMemberContribution; // Vẫn giữ nếu bạn dùng cho mục đích tính toán

import java.util.ArrayList;
import java.util.List;

public class GroupMembersContributionRecyclerViewAdapter extends RecyclerView.Adapter<GroupMembersContributionRecyclerViewAdapter.ViewHolder> {


    private List<GroupMember> memberList = new ArrayList<>();
    // private List<GroupMemberContribution> memberContributionList = new ArrayList<>(); // Loại bỏ hoặc dùng cho mục đích khác

    // Sửa constructor để nhận List<GroupMember>
    public GroupMembersContributionRecyclerViewAdapter(List<GroupMember> memberList){
        this.memberList = (memberList != null) ? memberList : new ArrayList<>();
    }

    // Sửa phương thức update để nhận List<GroupMember>
    public void updateMemberList(List<GroupMember> list) { // Đổi tên phương thức cho rõ ràng
        this.memberList = list;
        notifyDataSetChanged();
    }
    /*
    // Giữ lại nếu bạn vẫn cần hiển thị thông tin đóng góp dựa trên GroupMemberContribution
    public void updateMemberContributionList(List<GroupMemberContribution> list) {
        this.memberContributionList = list;
        notifyDataSetChanged();
    }
    */


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Sử dụng GroupMember trực tiếp
        GroupMember item = memberList.get(position); // Lấy GroupMember

        holder.tvMemberName.setText(item.getUsername()); // Lấy username từ GroupMember
        holder.tvMemberRole.setText(item.getRole());     // Lấy role từ GroupMember

        // Tải avatar bằng Glide
        if (item.getAvatarUrl() != null && !item.getAvatarUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getAvatarUrl())
                    .placeholder(R.drawable.default_avatar) // Avatar mặc định khi đang tải hoặc lỗi
                    .error(R.drawable.default_avatar)
                    .circleCrop()
                    .into(holder.ivAvatar);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_avatar)
                    .circleCrop()
                    .into(holder.ivAvatar);
        }

        holder.tvTotalContribution.setVisibility(View.GONE);
//        holder.itemView.findViewById(R.id.textView13).setVisibility(View.GONE); // Ẩn label "Contribute" nếu có
    }

    @Override
    public int getItemCount() {
        return memberList.size(); // Sử dụng memberList
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar; // Khai báo ImageView
        TextView tvMemberName, tvMemberRole, tvTotalContribution;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar); // Ánh xạ ImageView
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvMemberRole = itemView.findViewById(R.id.tvMemberRole);
            tvTotalContribution = itemView.findViewById(R.id.tvTotalContribution);
        }
    }
}