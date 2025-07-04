package com.example.happy_wallet_mobile.View.Adapter.UIModel;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class ActivitySelectionAdapter extends RecyclerView.Adapter<ActivitySelectionAdapter.ActivitySelectionViewHolder> {

    private List<FundActivity> availableActivities;
    private List<FundActivity> selectedActivities; // Danh sách các hoạt động đã chọn
    private OnActivitySelectedListener listener;

    // Interface để báo về khi một hoạt động được chọn/bỏ chọn
    public interface OnActivitySelectedListener {
        void onActivitySelected(FundActivity activity, boolean isSelected);
    }

    public ActivitySelectionAdapter(List<FundActivity> availableActivities, List<FundActivity> initialSelected, OnActivitySelectedListener listener) {
        this.availableActivities = availableActivities;
        this.selectedActivities = new ArrayList<>(initialSelected); // Tạo bản sao để không ảnh hưởng list bên ngoài
        this.listener = listener;
    }

    @NonNull
    @Override
    public ActivitySelectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_selection, parent, false); // Inflate layout
        return new ActivitySelectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivitySelectionViewHolder holder, int position) {
        FundActivity activity = availableActivities.get(position);

        holder.tvActivityText.setText(formatActivityText(activity)); //

        // Thiết lập trạng thái của checkbox dựa trên danh sách đã chọn
        holder.checkboxActivity.setChecked(selectedActivities.contains(activity)); //

        // Xử lý sự kiện khi checkbox thay đổi trạng thái
        holder.checkboxActivity.setOnCheckedChangeListener(null); // Clear previous listener
        holder.checkboxActivity.setChecked(selectedActivities.contains(activity)); // Set check state
        holder.checkboxActivity.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!selectedActivities.contains(activity)) {
                    selectedActivities.add(activity);
                }
            } else {
                selectedActivities.remove(activity);
            }
            if (listener != null) {
                listener.onActivitySelected(activity, isChecked);
            }
        });

        // Xử lý sự kiện click vào toàn bộ item (cũng thay đổi checkbox)
        holder.itemView.setOnClickListener(v -> holder.checkboxActivity.performClick());
    }

    @Override
    public int getItemCount() {
        return availableActivities.size();
    }

    public List<FundActivity> getSelectedActivities() {
        return selectedActivities;
    }

    // Helper method to format activity text (copy from PostAdapter for consistency)
    private String formatActivityText(FundActivity activity) {
        String baseText = "";
        String amountText = "";

        if (activity.getUser() != null) {
            baseText = activity.getUser().getUserName() + " ";
        } else {
            baseText = "Hoạt động: "; // Default if user info not available
        }

        switch (activity.getActivityType()) {
            case "contributed":
                baseText += "đã đóng góp ";
                if (activity.getAmount() != null) {
                    baseText += String.format("%,.0f₫", activity.getAmount()) + " ";
                }
                break;
            case "used":
                baseText += "đã chi tiêu ";
                if (activity.getAmount() != null) {
                    baseText += String.format("%,.0f₫", activity.getAmount()) + " ";
                }
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

        if (!TextUtils.isEmpty(activity.getDescription())) {
            String lowerDescription = activity.getDescription().toLowerCase();
            String lowerBaseText = baseText.toLowerCase();
            if (!lowerDescription.contains(lowerBaseText) && !lowerBaseText.contains(lowerDescription)) {
                baseText += " (" + activity.getDescription() + ")";
            }
        }
        return baseText.trim();
    }

    public static class ActivitySelectionViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityText;
        CheckBox checkboxActivity;

        public ActivitySelectionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvActivityText = itemView.findViewById(R.id.tvActivityText); //
            checkboxActivity = itemView.findViewById(R.id.checkboxActivity); //
        }
    }
}