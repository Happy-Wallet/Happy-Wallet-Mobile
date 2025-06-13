package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Category;
import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class CategoryListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Category> CategoryList;
    private LayoutInflater inflater;

    public CategoryListViewAdapter(Context _context, List<Category> _categoryList) {
        this.context = _context;
        this.CategoryList = _categoryList;
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
        ImageView ivCategoryIcon;
        TextView tvTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationListViewAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_category, parent, false);

            holder = new NotificationListViewAdapter.ViewHolder();
            // thieu icon
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);

            convertView.setTag(holder);
        } else {
            holder = (NotificationListViewAdapter.ViewHolder) convertView.getTag();
        }

        Category category = CategoryList.get(position);

        // thieu icon
        holder.tvTitle.setText(category.getTitle());

        return convertView;
    }

}
