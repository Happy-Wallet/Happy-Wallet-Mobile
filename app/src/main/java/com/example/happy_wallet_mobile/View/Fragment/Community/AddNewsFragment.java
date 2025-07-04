package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.flexbox.FlexboxLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.View.Activity.CommunityActivity;
import com.example.happy_wallet_mobile.View.Adapter.SelectedActivityAdapter;
import com.example.happy_wallet_mobile.ViewModel.Community.AddNewsViewModel;
import com.example.happy_wallet_mobile.View.Fragment.Community.SelectActivityDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddNewsFragment extends Fragment implements SelectedActivityAdapter.OnRemoveActivityListener {

    ConstraintLayout clContentLayout;
    FrameLayout flButtonLayout;
    TextView tvNews, tvPost, tvErrorMessage;
    ImageView ivImage, ivChooseImage, ivAddActivity;
    EditText etDescription;
    ProgressBar progressBar;

    RecyclerView rcvActivities;
    SelectedActivityAdapter selectedActivityAdapter;
    List<FundActivity> currentSelectedActivities;

    AddNewsViewModel addNewsViewModel;

    private Uri selectedImageUri;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        Glide.with(this)
                                .load(selectedImageUri)
                                .into(ivImage);
                        ivImage.setVisibility(View.VISIBLE);
                        adjustLayoutForImage(true);
                    }
                }
            });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_news, container, false);

        addNewsViewModel = new ViewModelProvider(requireActivity()).get(AddNewsViewModel.class);

        clContentLayout = view.findViewById(R.id.clContentLayout);
        flButtonLayout = view.findViewById(R.id.flButtonLayout);
        tvNews = view.findViewById(R.id.tvNews);
        tvPost = view.findViewById(R.id.tvPost);
        ivImage = view.findViewById(R.id.ivImage);
        ivChooseImage = view.findViewById(R.id.ivChooseImage);
        ivAddActivity = view.findViewById(R.id.ivAddActivity);
        etDescription = view.findViewById(R.id.etDescription);
        progressBar = view.findViewById(R.id.progressBarAddNews);
        tvErrorMessage = view.findViewById(R.id.tvErrorMessageAddNews);

        rcvActivities = view.findViewById(R.id.rcvActivities);
        currentSelectedActivities = new ArrayList<>();
        selectedActivityAdapter = new SelectedActivityAdapter(currentSelectedActivities, this);

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext());
        rcvActivities.setLayoutManager(flexboxLayoutManager);
        rcvActivities.setAdapter(selectedActivityAdapter);
        rcvActivities.setVisibility(View.GONE); // Ẩn ban đầu nếu chưa có hoạt động nào được chọn

        ivImage.setVisibility(View.GONE);
        adjustLayoutForImage(false);

        ivChooseImage.setOnClickListener(v -> openImageChooser());
        tvPost.setOnClickListener(v -> postNews());
        ivAddActivity.setOnClickListener(v -> addActivity()); // Nút thêm hoạt động

        // Quan sát LiveData từ ViewModel
        addNewsViewModel.isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                tvPost.setEnabled(!isLoading);
                ivChooseImage.setEnabled(!isLoading);
                ivAddActivity.setEnabled(!isLoading);
                etDescription.setEnabled(!isLoading);
                if (isLoading) {
                    tvErrorMessage.setVisibility(View.GONE);
                }
            }
        });

        addNewsViewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null && !message.isEmpty()) {
                    tvErrorMessage.setText("Lỗi: " + message);
                    tvErrorMessage.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Lỗi: " + message, Toast.LENGTH_LONG).show();
                } else {
                    tvErrorMessage.setVisibility(View.GONE);
                }
            }
        });

        addNewsViewModel.postCreatedSuccess.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isSuccess) {
                if (isSuccess) {
                    Toast.makeText(getContext(), "Đăng bài thành công!", Toast.LENGTH_SHORT).show();
                    resetFormAndNavigateBack();
                }
            }
        });

        addNewsViewModel.selectedActivities.observe(getViewLifecycleOwner(), new Observer<List<FundActivity>>() {
            @Override
            public void onChanged(List<FundActivity> activities) {
                currentSelectedActivities.clear();
                if (activities != null) {
                    currentSelectedActivities.addAll(activities);
                }
                selectedActivityAdapter.notifyDataSetChanged();
                rcvActivities.setVisibility(currentSelectedActivities.isEmpty() ? View.GONE : View.VISIBLE);
            }
        });

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        pickImageLauncher.launch(intent);
    }

    private void adjustLayoutForImage(boolean hasImage) {
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(clContentLayout);

        if (hasImage) {
            constraintSet.clear(R.id.etDescription, ConstraintSet.TOP);
            constraintSet.connect(R.id.etDescription, ConstraintSet.TOP, R.id.ivImage, ConstraintSet.BOTTOM, (int) getResources().getDimension(R.dimen.margin_top_image_desc));
        } else {
            constraintSet.clear(R.id.etDescription, ConstraintSet.TOP);
            constraintSet.connect(R.id.etDescription, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, (int) getResources().getDimension(R.dimen.margin_top_no_image_desc));
        }
        constraintSet.applyTo(clContentLayout);
    }

    private void postNews() {
        String caption = etDescription.getText().toString().trim();

        if (caption.isEmpty() && selectedImageUri == null && (currentSelectedActivities == null || currentSelectedActivities.isEmpty())) {
            Toast.makeText(getContext(), "Vui lòng nhập nội dung, chọn ảnh hoặc chọn hoạt động!", Toast.LENGTH_SHORT).show();
            return;
        }

        addNewsViewModel.createPost(selectedImageUri, caption, getContext());
    }

    private void resetFormAndNavigateBack() {
        etDescription.setText("");
        ivImage.setVisibility(View.GONE);
        selectedImageUri = null;
        adjustLayoutForImage(false);
        // ViewModel đã reset selectedActivities, adapter sẽ tự cập nhật nhờ observer

        if (getActivity() instanceof CommunityActivity) {
            ((CommunityActivity) getActivity()).loadFragment(new CurrentNewsFragment(), false);
        }
    }

    @Override
    public void onRemoveActivity(FundActivity activity) {
        // Xử lý khi người dùng nhấn nút 'X' để xóa một hoạt động đã chọn
        addNewsViewModel.removeSelectedActivity(activity);
        Toast.makeText(getContext(), "Đã xóa hoạt động khỏi danh sách.", Toast.LENGTH_SHORT).show();
    }

    private void addActivity() {
        // Mở SelectActivityDialogFragment khi nhấn nút "Add Activity"
        SelectActivityDialogFragment dialogFragment = new SelectActivityDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "SelectActivityDialog");
    }
}