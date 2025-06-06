package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.happy_wallet_mobile.Model.Notification;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.NotificationListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Fragment {

    Button btnBack;
    ListView lvNotification;
    NotificationListViewAdapter adapter;
    List<Notification> notificationList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);


        btnBack = view.findViewById(R.id.btnBack);
        lvNotification = view.findViewById(R.id.lvNotification);


        // back
        btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });


        notificationList = new ArrayList<>();
        notificationList.add(new Notification("Thông báo 1 ====================================" +
                "================================================================",
                "Mô tả thông báo 1========================================================" +
                        "===========================================================================" +
                        "==============="));
        notificationList.add(new Notification("Thông báo 2", "Mô tả thông báo 2"));
        notificationList.add(new Notification("Thông báo 3 ====================================",
                "Mô tả thông báo 3========================================================" +
                        "===========================================================================" +
                        "==============="));
        notificationList.add(new Notification("Thông báo 1 ====================================" +
                "================================================================",
                "Mô tả thông báo 1========================================================" +
                        "===========================================================================" +
                        "==============="));
        notificationList.add(new Notification("Thông báo 1 ====================================" +
                "================================================================",
                "Mô tả thông báo 1========================================================" +
                        "===========================================================================" +
                        "==============="));
        notificationList.add(new Notification("Thông báo 1 ====================================" +
                "================================================================",
                "Mô tả thông báo 1========================================================" +
                        "===========================================================================" +
                        "==============="));


        adapter = new NotificationListViewAdapter(requireContext(), notificationList);
        lvNotification.setAdapter(adapter);

        return view;
    }
}
