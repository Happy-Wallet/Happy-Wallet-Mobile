package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Community.CurrentNewsViewModel;
import com.example.happy_wallet_mobile.ViewModel.Community.NewsCommentViewModel;

public class NewsCommentFragment extends Fragment {

    NewsCommentViewModel newsCommentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_comment, container, false);

        newsCommentViewModel = new ViewModelProvider(requireActivity()).get(NewsCommentViewModel.class);

        return view;
    }
}