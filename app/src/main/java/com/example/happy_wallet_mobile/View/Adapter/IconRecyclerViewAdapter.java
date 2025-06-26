package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.R;

import java.util.List;

public class IconRecyclerViewAdapter extends RecyclerView.Adapter<IconRecyclerViewAdapter.IconViewHolder> {

    public interface OnIconClickListener {
        void onIconClick(int iconResId); // Trả về resource ID thay vì object
    }

    private final Context context;
    private final List<Integer> iconList; // Danh sách R.drawable.*
    private OnIconClickListener onIconClickListener;
    private int selectedPosition = -1;

    public IconRecyclerViewAdapter(Context context, List<Integer> iconList) {
        this.context = context;
        this.iconList = iconList;
    }

    public void setOnIconClickListener(OnIconClickListener listener) {
        this.onIconClickListener = listener;
    }

    @NonNull
    @Override
    public IconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false);
        return new IconViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IconViewHolder holder, int position) {
        int iconResId = iconList.get(position);

        holder.ivIcon.setImageResource(iconResId);

        // Viền nếu được chọn
        holder.clItemContainer.setBackgroundTintList(
                ColorStateList.valueOf(
                        selectedPosition == position
                                ? ContextCompat.getColor(context, R.color.Radishical)
                                : ContextCompat.getColor(context, R.color.Canadian_Tuxedo)
                )
        );

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) return;

            int old = selectedPosition;
            selectedPosition = adapterPosition;
            notifyItemChanged(old);
            notifyItemChanged(adapterPosition);

            if (onIconClickListener != null) {
                onIconClickListener.onIconClick(iconResId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return iconList.size();
    }

    static class IconViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        ConstraintLayout clItemContainer;

        public IconViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            clItemContainer = itemView.findViewById(R.id.clItemContainer);
        }
    }

    public Integer getSelectedIconResId() {
        return selectedPosition >= 0 ? iconList.get(selectedPosition) : null;
    }
}
