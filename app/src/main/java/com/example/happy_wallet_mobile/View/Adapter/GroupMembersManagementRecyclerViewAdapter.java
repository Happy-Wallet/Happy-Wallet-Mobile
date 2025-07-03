package com.example.happy_wallet_mobile.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView; // Import ImageView
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast; // Dùng để hiển thị toast tạm thời

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Import Glide
import com.example.happy_wallet_mobile.Model.GroupMember; // Import GroupMember
import com.example.happy_wallet_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersManagementRecyclerViewAdapter extends RecyclerView.Adapter<GroupMembersManagementRecyclerViewAdapter.ViewHolder> {

    private List<GroupMember> memberList; // Đổi từ GroupMemberContribution sang GroupMember
    private OnMemberActionListener listener; // Interface mới cho các hành động

    // Constructor mới nhận List<GroupMember>
    public GroupMembersManagementRecyclerViewAdapter(List<GroupMember> memberList){
        this.memberList = (memberList != null) ? memberList : new ArrayList<>();
    }

    // Phương thức cập nhật danh sách
    public void updateMemberList(List<GroupMember> list) { // Đổi tên phương thức
        this.memberList = list;
        notifyDataSetChanged();
    }

    public void setOnMemberActionListener(OnMemberActionListener listener) {
        this.listener = listener;
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
        GroupMember item = memberList.get(position);

        holder.tvMemberName.setText(item.getUsername()); // Lấy username từ GroupMember
        holder.tvMemberRole.setText(item.getRole());     // Lấy role từ GroupMember

        // Tải avatar bằng Glide
        if (item.getAvatarUrl() != null && !item.getAvatarUrl().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(item.getAvatarUrl())
                    .placeholder(R.drawable.default_avatar) // Avatar mặc định khi đang tải hoặc lỗi
                    .error(R.drawable.default_avatar)      // Avatar mặc định khi có lỗi
                    .circleCrop()                          // Cắt ảnh thành hình tròn
                    .into(holder.ivAvatar);
        } else {
            // Nếu không có URL avatar, hiển thị avatar mặc định
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_avatar)
                    .circleCrop()
                    .into(holder.ivAvatar);
        }

        holder.tvOptions.setOnClickListener(v -> {
            showPopupMenu(v, item, position);
        });
    }

    private void showPopupMenu(View anchor, GroupMember member, int position) {
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.menu_group_member_options, popupMenu.getMenu());

         if (member.getRole().equals("Admin")) {
             popupMenu.getMenu().findItem(R.id.action_to_admin).setVisible(false);
             popupMenu.getMenu().findItem(R.id.action_to_member).setVisible(true);
         } else {
             popupMenu.getMenu().findItem(R.id.action_to_admin).setVisible(true);
             popupMenu.getMenu().findItem(R.id.action_to_member).setVisible(false);
         }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (listener != null) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.action_delete) {
                    listener.onDeleteClick(member);
                    return true;
                } else if (itemId == R.id.action_to_admin) {
                    listener.onChangeRoleClick(member, "Admin");
                    return true;
                } else if (itemId == R.id.action_to_member) {
                    listener.onChangeRoleClick(member, "Member");
                    return true;
                }
            }
            return false;
        });
        popupMenu.show();
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar; // Khai báo ImageView
        TextView tvMemberName, tvMemberRole, tvOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar); // Ánh xạ ImageView
            tvMemberName = itemView.findViewById(R.id.tvMemberName);
            tvMemberRole = itemView.findViewById(R.id.tvMemberRole);
            tvOptions = itemView.findViewById(R.id.tvOptions);
        }
    }

    // Interface để gửi sự kiện về Fragment
    public interface OnMemberActionListener {
        void onDeleteClick(GroupMember member);
        void onChangeRoleClick(GroupMember member, String newRole);
    }
}