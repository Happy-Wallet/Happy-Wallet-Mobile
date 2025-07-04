package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton; // Import FloatingActionButton

import com.example.happy_wallet_mobile.Model.Post;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.PostAdapter;
import com.example.happy_wallet_mobile.View.Activity.CommunityActivity;
import com.example.happy_wallet_mobile.ViewModel.Community.CurrentNewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class CurrentNewsFragment extends Fragment {

    private CurrentNewsViewModel currentNewsViewModel;

    private RecyclerView recyclerViewPosts;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private ProgressBar progressBar;
    private TextView tvErrorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FloatingActionButton fabAddPost; // Khai báo FloatingActionButton

    public CurrentNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community_news, container, false);

        currentNewsViewModel = new ViewModelProvider(requireActivity()).get(CurrentNewsViewModel.class);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        recyclerViewPosts = view.findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerViewPosts.setAdapter(postAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessage);
        fabAddPost = view.findViewById(R.id.fabAddPost); // Khởi tạo FloatingActionButton

        // Thêm listener cho PostAdapter
        postAdapter.setOnItemClickListener(new PostAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int postId) {
                if (getActivity() instanceof CommunityActivity) {
                    ((CommunityActivity) getActivity()).navigateToNewsComment(postId);
                }
            }
        });

        // Thiết lập OnRefreshListener cho SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentNewsViewModel.fetchPosts();
            }
        });

        // Thiết lập OnClickListener cho FloatingActionButton
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof CommunityActivity) {
                    ((CommunityActivity) getActivity()).navigateToAddNews(); // Gọi phương thức điều hướng
                }
            }
        });

        // Quan sát LiveData từ ViewModel
        currentNewsViewModel.posts.observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postList.clear();
                if (posts != null) {
                    postList.addAll(posts);
                }
                postAdapter.notifyDataSetChanged();
                Log.d("CurrentNewsFragment", "Observed " + postList.size() + " posts update from ViewModel.");
                tvErrorMessage.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        currentNewsViewModel.isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                }
                if (isLoading) {
                    tvErrorMessage.setVisibility(View.GONE);
                }
            }
        });

        currentNewsViewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null && !message.isEmpty()) {
                    tvErrorMessage.setText("Lỗi: " + message);
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Lỗi tải dữ liệu: " + message, Toast.LENGTH_LONG).show();
                } else {
                    tvErrorMessage.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
}