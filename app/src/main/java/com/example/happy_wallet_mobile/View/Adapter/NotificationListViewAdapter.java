package com.example.happy_wallet_mobile.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;

import java.util.List;

public class NotificationListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Notification> notificationList;
    private LayoutInflater inflater;

    public NotificationListViewAdapter(Context context, List<Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
        this.inflater = LayoutInflater.from(context);
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
        return position;
    }

    static class ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
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

        return convertView;
    }
}
