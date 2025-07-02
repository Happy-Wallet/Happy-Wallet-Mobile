package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.eType;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class CategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private eType typeFilter;
    private List<Category> allCategories;
    private List<Category> filteredCategories;

    private LayoutInflater inflater;

    public CategoryListViewAdapter(Context context, List<Category> categoryList, eType typeFilter) {
        this.context = context;
        this.allCategories = categoryList;
        this.typeFilter = typeFilter;
        this.inflater = LayoutInflater.from(context);
        filterCategories();
    }

    private void filterCategories() {
        filteredCategories = new java.util.ArrayList<>();
        for (Category c : allCategories) {
            if (c.getType() == typeFilter) {
                filteredCategories.add(c);
            }
        }
    }

    public void updateCategories(List<Category> categoryList) {
        this.allCategories = categoryList;
        filterCategories();
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return filteredCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredCategories.get(position);
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

        Category category = filteredCategories.get(position);

        holder.tvTitle.setText(category.getName());

        // Gán icon trực tiếp từ iconRes
        holder.ivIcon.setImageResource(category.getIconRes());

        // Gán màu nền từ colorRes
        holder.flIconBackground.getBackground().setTint(
                ContextCompat.getColor(context, category.getColorRes())
        );

        convertView.setOnClickListener(v -> {
            if (onCategoryClickListener != null) {
                onCategoryClickListener.onCategoryClick(category);
            }
        });

        return convertView;
    }
}
