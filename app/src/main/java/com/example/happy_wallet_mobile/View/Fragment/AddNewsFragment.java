package com.example.happy_wallet_mobile.View.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;

public class AddNewsFragment extends Fragment {

    TextView tvNews, tvPost;
    ImageView ivImage, ivChooseImage, ivAddActivity;
    EditText etDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_news, container, false);

        tvNews = view.findViewById(R.id.tvNews);
        tvPost = view.findViewById(R.id.tvPost);
        ivImage = view.findViewById(R.id.ivImage);
        ivChooseImage = view.findViewById(R.id.ivChooseImage);
        ivAddActivity = view.findViewById(R.id.ivAddActivity);
        etDescription = view.findViewById(R.id.etDescription);

        // default view
        ivImage.setVisibility(View.GONE);

        return view;
    }
}