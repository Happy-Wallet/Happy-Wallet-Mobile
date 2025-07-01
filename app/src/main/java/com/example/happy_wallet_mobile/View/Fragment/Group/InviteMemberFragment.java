package com.example.happy_wallet_mobile.View.Fragment.Group;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Group.GroupsViewModel;
import com.example.happy_wallet_mobile.ViewModel.Group.InviteMemberViewModel;
import com.example.happy_wallet_mobile.ViewModel.MainViewModel;


public class InviteMemberFragment extends Fragment {

    InviteMemberViewModel inviteMemberViewModel;
    GroupsViewModel groupsViewModel;
    EditText etEmail;
    TextView tvInvite, tvCancel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite_member, container, false);

        inviteMemberViewModel = new ViewModelProvider(requireActivity()).get(InviteMemberViewModel.class);
        groupsViewModel = new ViewModelProvider(requireActivity()).get(GroupsViewModel.class);

        etEmail = view.findViewById(R.id.etEmail);
        tvInvite = view.findViewById(R.id.tvInvite);
        tvCancel = view.findViewById(R.id.tvCancel);

        //cancel
        tvCancel.setOnClickListener(v->{
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        //tvInvite clicked
        tvInvite.setOnClickListener(v -> {
            inviteMemberViewModel.inviteMember(etEmail.getText().toString(), groupsViewModel.getCurrentGroup().getValue());
        });

        return view;
    }
}