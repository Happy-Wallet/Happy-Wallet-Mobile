package com.example.happy_wallet_mobile.View.Fragment.Community;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.View.Adapter.ActivitySelectionAdapter;
import com.example.happy_wallet_mobile.ViewModel.Community.AddNewsViewModel;
import com.example.happy_wallet_mobile.ViewModel.Community.SelectActivityViewModel; // Cần tạo ViewModel này

import java.util.ArrayList;
import java.util.List;

public class SelectActivityDialogFragment extends DialogFragment implements ActivitySelectionAdapter.OnActivitySelectedListener {

    private SelectActivityViewModel selectActivityViewModel;
    private AddNewsViewModel addNewsViewModel; // Để cập nhật danh sách hoạt động đã chọn

    private RecyclerView recyclerViewActivitiesSelection;
    private ActivitySelectionAdapter activitySelectionAdapter;
    private List<FundActivity> availableActivities; // Danh sách tất cả các hoạt động có sẵn
    private List<FundActivity> initialSelectedActivities; // Danh sách các hoạt động đã chọn ban đầu (từ AddNewsFragment)

    private ProgressBar progressBarDialog;
    private TextView tvErrorMessageDialog;
    private Button btnSelectActivities;

    public SelectActivityDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Để DialogFragment chiếm toàn bộ chiều rộng màn hình (tùy chọn)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle); // Tạo style này nếu cần
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_activity_dialog, container, false); // Inflate layout

        selectActivityViewModel = new ViewModelProvider(this).get(SelectActivityViewModel.class); // ViewModel riêng cho Dialog
        addNewsViewModel = new ViewModelProvider(requireActivity()).get(AddNewsViewModel.class); // Lấy ViewModel của Fragment cha

        progressBarDialog = view.findViewById(R.id.progressBarDialog); //
        tvErrorMessageDialog = view.findViewById(R.id.tvErrorMessageDialog); //
        recyclerViewActivitiesSelection = view.findViewById(R.id.recyclerViewActivitiesSelection); //
        btnSelectActivities = view.findViewById(R.id.btnSelectActivities); //

        recyclerViewActivitiesSelection.setLayoutManager(new LinearLayoutManager(getContext()));
        availableActivities = new ArrayList<>();

        // Lấy danh sách đã chọn ban đầu từ AddNewsViewModel (để checkbox được check đúng)
        initialSelectedActivities = new ArrayList<>(addNewsViewModel.selectedActivities.getValue());

        activitySelectionAdapter = new ActivitySelectionAdapter(availableActivities, initialSelectedActivities, this); // 'this' là listener
        recyclerViewActivitiesSelection.setAdapter(activitySelectionAdapter);

        // Quan sát LiveData từ ViewModel
        selectActivityViewModel.availableActivities.observe(getViewLifecycleOwner(), new Observer<List<FundActivity>>() {
            @Override
            public void onChanged(List<FundActivity> activities) {
                availableActivities.clear();
                if (activities != null) {
                    availableActivities.addAll(activities);
                }
                activitySelectionAdapter.notifyDataSetChanged();
                tvErrorMessageDialog.setVisibility(View.GONE);
                recyclerViewActivitiesSelection.setVisibility(View.VISIBLE);
            }
        });

        selectActivityViewModel.isLoading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                progressBarDialog.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                btnSelectActivities.setEnabled(!isLoading);
                if (isLoading) {
                    tvErrorMessageDialog.setVisibility(View.GONE);
                }
            }
        });

        selectActivityViewModel.errorMessage.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null && !message.isEmpty()) {
                    tvErrorMessageDialog.setText("Lỗi: " + message);
                    tvErrorMessageDialog.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Lỗi tải hoạt động: " + message, Toast.LENGTH_LONG).show();
                    recyclerViewActivitiesSelection.setVisibility(View.GONE); // Ẩn danh sách nếu có lỗi
                } else {
                    tvErrorMessageDialog.setVisibility(View.GONE);
                }
            }
        });

        // Gọi API để tải danh sách hoạt động có sẵn
        selectActivityViewModel.fetchAvailableActivities();

        // Xử lý nút "Chọn"
        btnSelectActivities.setOnClickListener(v -> {
            // Khi nhấn nút "Chọn", cập nhật danh sách hoạt động đã chọn vào AddNewsViewModel
            List<FundActivity> finalSelectedActivities = activitySelectionAdapter.getSelectedActivities();
            // Xóa hết các hoạt động cũ và thêm lại các hoạt động đã chọn
            // addNewsViewModel.selectedActivities.getValue().clear(); // Không nên clear trực tiếp List từ LiveData
            // addNewsViewModel.selectedActivities.getValue().addAll(finalSelectedActivities); // Không nên add trực tiếp
            // addNewsViewModel.selectedActivities.setValue(new ArrayList<>(finalSelectedActivities)); // Tạo List mới và set

            // Tốt hơn: Sử dụng một phương thức riêng trong AddNewsViewModel để cập nhật toàn bộ danh sách
            addNewsViewModel.updateAllSelectedActivities(finalSelectedActivities);

            dismiss(); // Đóng DialogFragment
        });

        return view;
    }

    @Override
    public void onActivitySelected(FundActivity activity, boolean isSelected) {
        // Callback từ Adapter khi một hoạt động được chọn/bỏ chọn
        // ViewModel sẽ tự quản lý list chọn, nên không cần làm gì nhiều ở đây trừ khi có logic UI đặc biệt
        Log.d("SelectActivityDialog", "Activity " + activity.getDescription() + " is selected: " + isSelected);
    }
}