package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class IconRecyclerViewAdapter extends RecyclerView.Adapter<IconRecyclerViewAdapter.IconViewHolder> {

    public interface OnIconClickListener {
        void onIconClick(Icon icon);
    }

    private Context context;
    private List<Icon> iconList;
    private OnIconClickListener onIconClickListener;
    private int selectedPosition = -1;


    public IconRecyclerViewAdapter(Context context, List<Icon> iconList) {
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
        Icon icon = iconList.get(position);

        int resId = context.getResources().getIdentifier(icon.getIconPath(), "drawable", context.getPackageName());
        holder.ivIcon.setImageResource(resId);


        // Hiển thị viền nếu được chọn
        holder.clItemContainer.setBackgroundTintList(
                ColorStateList.valueOf(
                        selectedPosition == position
                                ? ContextCompat.getColor(context, R.color.Radishical)   // màu khi được chọn
                                : ContextCompat.getColor(context, R.color.Canadian_Tuxedo) // màu mặc định
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
                onIconClickListener.onIconClick(icon);
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

    public Icon getSelectedIcon() {
        return selectedPosition >= 0 ? iconList.get(selectedPosition) : null;
    }

}
