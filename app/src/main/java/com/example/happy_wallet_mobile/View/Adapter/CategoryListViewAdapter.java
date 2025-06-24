package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Icon;
import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class CategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Category> CategoryList;
    private List<Icon> IconList;
    private LayoutInflater inflater;

    public CategoryListViewAdapter(Context _context, List<Category> categoryList, List<Icon> iconList) {
        this.context = _context;
        this.CategoryList = categoryList;
        this.IconList = iconList;
        this.inflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return CategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        FrameLayout flIconBackground;
        ImageView ivIcon;
        TextView tvTitle;
    }

    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }

    private OnCategoryClickListener onCategoryClickListener;

    public void setOnCategoryClickListener(OnCategoryClickListener listener) {
        this.onCategoryClickListener = listener;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, parent, false);

            holder = new ViewHolder();
            holder.flIconBackground = convertView.findViewById(R.id.flIconBackground);
            holder.ivIcon = convertView.findViewById(R.id.ivIcon);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = CategoryList.get(position);
        Icon icon = getIconById(category.getIconId());

        holder.tvTitle.setText(category.getName());

        // Gán icon
        if (icon != null) {
            int resId = context.getResources().getIdentifier(icon.getIconPath(), "drawable", context.getPackageName());
            if (resId != 0) {
                holder.ivIcon.setImageResource(resId);
            } else {
                holder.ivIcon.setImageResource(R.drawable.ic_house); // fallback nếu icon không tồn tại
            }
        }

        // Set background color theo category
        try {
            holder.flIconBackground.getBackground().setTint(android.graphics.Color.parseColor(category.getColorCode()));
        } catch (Exception e) {
            e.printStackTrace(); // fallback nếu color code sai
        }

        convertView.setOnClickListener(v -> {
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(category);
            }
        });

        return convertView;
    }

    private Icon getIconById(int iconId) {
        for (Icon icon : IconList) {
            if (icon.getIconId() == iconId) {
                return icon;
            }
        }
        return null;
    }


}
