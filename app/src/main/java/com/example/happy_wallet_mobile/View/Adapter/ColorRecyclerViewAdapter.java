package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.R;

import java.util.List;

public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<ColorRecyclerViewAdapter.ColorViewHolder> {

    private Context context;
    private List<String> colorList; // Mã màu hex, ví dụ: "#FF5733"
    private OnColorClickListener onColorClickListener;
    private int selectedPosition = -1;

    public interface OnColorClickListener {
        void onColorClick(String colorCode);
    }

    public void setOnColorClickListener(OnColorClickListener listener) {
        this.onColorClickListener = listener;
    }

    public ColorRecyclerViewAdapter(Context context, List<String> colorList) {
        this.context = context;
        this.colorList = colorList;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_icon, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        String colorCode = colorList.get(position);

        try {
            holder.ivIcon.setBackgroundTintList(
                    ColorStateList.valueOf(Color.parseColor(colorCode))
            );
        } catch (IllegalArgumentException e) {
            holder.ivIcon.setBackgroundTintList(
                    ColorStateList.valueOf(Color.GRAY)
            );
        }

        // Hiển thị viền nếu được chọn
        holder.clItemContainer.setBackgroundTintList(
                ColorStateList.valueOf(
                        selectedPosition == position
                                ? ContextCompat.getColor(context, R.color.Radishical)   // màu khi được chọn
                                : ContextCompat.getColor(context, R.color.white) // màu mặc định
                )
        );

        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition == RecyclerView.NO_POSITION) return;

            int old = selectedPosition;
            selectedPosition = adapterPosition;
            notifyItemChanged(old);
            notifyItemChanged(adapterPosition);

            if (onColorClickListener != null) {
                onColorClickListener.onColorClick(colorList.get(adapterPosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

    static class ColorViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        ConstraintLayout clItemContainer;
        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            clItemContainer = itemView.findViewById(R.id.clItemContainer);
        }
    }
}
