package com.example.happy_wallet_mobile.View.Adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.FundActivity; // Import FundActivity Model
import com.example.happy_wallet_mobile.R; // Import R

import java.util.List;

public class SelectedActivityAdapter extends RecyclerView.Adapter<SelectedActivityAdapter.SelectedActivityViewHolder> {

    private List<FundActivity> selectedActivities;
    private OnRemoveActivityListener onRemoveActivityListener;

    // Interface để xử lý sự kiện xóa một hoạt động
    public interface OnRemoveActivityListener {
        void onRemoveActivity(FundActivity activity);
    }

    public SelectedActivityAdapter(List<FundActivity> selectedActivities, OnRemoveActivityListener listener) {
        this.selectedActivities = selectedActivities;
        this.onRemoveActivityListener = listener;
    }

    @NonNull
    @Override
    public SelectedActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_activity, parent, false); // Inflate layout
        return new SelectedActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedActivityViewHolder holder, int position) {
        FundActivity activity = selectedActivities.get(position);

        // Gán mô tả hoạt động. Bạn sẽ cần một phương thức format tương tự trong PostAdapter
        holder.tvActivityDescription.setText(formatActivityText(activity)); //

        // Thiết lập sự kiện click cho nút xóa
        holder.ivRemoveActivity.setOnClickListener(v -> { //
            if (onRemoveActivityListener != null) {
                onRemoveActivityListener.onRemoveActivity(activity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedActivities.size();
    }

    public static class SelectedActivityViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityDescription;
        ImageView ivRemoveActivity;

        public SelectedActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityDescription = itemView.findViewById(R.id.tvActivityDescription); //
            ivRemoveActivity = itemView.findViewById(R.id.ivRemoveActivity); //
        }
    }

    // Helper method to format activity text (copy from PostAdapter for consistency)
    // Hoặc bạn có thể tạo một lớp Utils chung để chứa hàm này
    private String formatActivityText(FundActivity activity) {
        String baseText = "";
        String amountText = "";

        if (activity.getUser() != null) {
            baseText = activity.getUser().getUserName() + " ";
        } else {
            // Nếu không có thông tin user, chỉ hiển thị loại hoạt động hoặc description
            baseText = "Hoạt động: ";
        }

        switch (activity.getActivityType()) {
            case "contributed":
                baseText += "đã đóng góp ";
                if (activity.getAmount() != null) {
                    amountText = String.format("%,.0f₫", activity.getAmount()); // Định dạng tiền tệ
                }
                baseText += amountText + " ";
                break;
            case "used":
                baseText += "đã chi tiêu ";
                if (activity.getAmount() != null) {
                    amountText = String.format("%,.0f₫", activity.getAmount());
                }
                baseText += amountText + " ";
                break;
            case "created_fund":
                baseText += "đã tạo quỹ";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += ": '" + activity.getFundName() + "'";
                }
                break;
            case "joined_fund":
                baseText += "đã tham gia quỹ";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += " '" + activity.getFundName() + "'";
                }
                break;
            case "left_fund":
                baseText += "đã rời quỹ";
                if (!TextUtils.isEmpty(activity.getFundName())) {
                    baseText += " '" + activity.getFundName() + "'";
                }
                break;
            default:
                if (!TextUtils.isEmpty(activity.getDescription())) {
                    baseText = activity.getDescription();
                } else {
                    baseText = "một hoạt động.";
                }
                break;
        }

        // Nếu có description phụ mà chưa được bao gồm trong baseText
        if (!TextUtils.isEmpty(activity.getDescription())) {
            String lowerDescription = activity.getDescription().toLowerCase();
            String lowerBaseText = baseText.toLowerCase();
            // Chỉ thêm description nếu nó không phải là phần của baseText
            if (!lowerDescription.contains(lowerBaseText) && !lowerBaseText.contains(lowerDescription)) {
                baseText += " (" + activity.getDescription() + ")";
            }
        }
        return baseText.trim();
    }
}