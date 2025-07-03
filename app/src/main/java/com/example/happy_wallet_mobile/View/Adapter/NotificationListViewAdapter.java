package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.content.ContextCompat; // Import này để lấy màu an toàn

import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class NotificationListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Notification> notificationList;
    private LayoutInflater inflater;
    private OnNotificationClickListener listener; // Thêm listener

    // Interface để xử lý sự kiện click trên Fragment
    public interface OnNotificationClickListener {
        void onNotificationClick(Notification notification);
        void onMarkAsReadClick(int notificationId); // Thêm để đánh dấu đã đọc

        void onAcceptInvitationClick(int fundId, int notificationId);

        void onRejectInvitationClick(int fundId, int notificationId);
    }

    public NotificationListViewAdapter(Context context, List<Notification> notificationList, OnNotificationClickListener listener) {
        this.context = context;
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener; // Khởi tạo listener
    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public Object getItem(int position) {
        return notificationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notificationList.get(position).getId(); // Trả về ID thông báo
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        // Thêm các View khác nếu cần, ví dụ: TextView cho trạng thái đã đọc
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_notification, parent, false);

            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvDescription = convertView.findViewById(R.id.tvDescription);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Notification notification = notificationList.get(position);

        holder.tvTitle.setText(notification.getTitle());
        holder.tvDescription.setText(notification.getDescription());

        // Cập nhật UI dựa trên trạng thái đã đọc và loại thông báo
        if (notification.isRead()) {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color)); // Màu xám cho đã đọc
            holder.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            holder.tvTitle.setTypeface(null, Typeface.NORMAL);
            holder.tvDescription.setTypeface(null, Typeface.NORMAL);
        } else {
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.Nautical)); // Màu mặc định
            holder.tvDescription.setTextColor(ContextCompat.getColor(context, R.color.Nautical));
            holder.tvTitle.setTypeface(null, Typeface.BOLD); // In đậm cho chưa đọc
            holder.tvDescription.setTypeface(null, Typeface.NORMAL);
        }

        // Tùy chỉnh hiển thị nếu là lời mời
        if (notification.isInvitation()) {
            // Ví dụ: thay đổi màu sắc, thêm icon, hoặc thêm text "Lời mời"
            // Có thể thêm một ImageView cho icon hoặc một TextView nhỏ "INVITATION"
            holder.tvTitle.append(" (Lời mời)"); // Thêm chữ "(Lời mời)" vào tiêu đề
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.red_color_for_invitation)); // Ví dụ màu đỏ
        } else {
            // Đảm bảo loại bỏ các tùy chỉnh nếu không phải lời mời
            // Ví dụ: holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.Nautical));
        }

        // Xử lý sự kiện click cho từng item
        convertView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onNotificationClick(notification);
                // Sau khi click, bạn có thể tự động đánh dấu đã đọc
                if (!notification.isRead()) {
                    notification.setRead(true);
                    notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    // Phương thức để cập nhật danh sách notifications
    public void setNotifications(List<Notification> newNotificationList) {
        this.notificationList.clear();
        this.notificationList.addAll(newNotificationList);
        notifyDataSetChanged();
    }
}