package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Community.AddNewsViewModel;
import com.example.happy_wallet_mobile.ViewModel.Community.CurrentNewsViewModel;

public class CurrentNewsFragment extends Fragment {

    CurrentNewsViewModel currentNewsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_news, container, false);

        currentNewsViewModel = new ViewModelProvider(requireActivity()).get(CurrentNewsViewModel.class);

        return view;
    }
}