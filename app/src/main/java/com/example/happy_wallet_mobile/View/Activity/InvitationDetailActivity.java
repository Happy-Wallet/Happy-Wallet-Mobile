package com.example.happy_wallet_mobile.View.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.happy_wallet_mobile.R;
import com.example.happy_wallet_mobile.ViewModel.Group.InvitationViewModel;
import com.example.happy_wallet_mobile.Data.Remote.Response.Invitation.MessageResponse;
import com.example.happy_wallet_mobile.Data.Remote.Response.Group.FundDetailsResponse;

public class InvitationDetailActivity extends AppCompatActivity {

    private InvitationViewModel invitationViewModel;
    private TextView fundNameTextView;
    private TextView fundDescriptionTextView;
    private Button acceptButton;
    private Button rejectButton;
    private int fundId; // fundId của lời mời hiện tại

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_detail);

        fundNameTextView = findViewById(R.id.tvInvitationTitle); // Dùng tvInvitationTitle để hiển thị tên quỹ/tiêu đề lời mời
        fundDescriptionTextView = findViewById(R.id.tvInvitationDescription); // Dùng tvInvitationDescription để hiển thị mô tả/mục tiêu
        acceptButton = findViewById(R.id.btnAcceptInvitation);
        rejectButton = findViewById(R.id.btnRejectInvitation);

        // Lấy fundId từ Intent
        if (getIntent().getExtras() != null) {
            fundId = getIntent().getIntExtra("fundId", -1);
            if (fundId == -1) {
                Toast.makeText(this, "Không tìm thấy ID quỹ.", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "Không có dữ liệu lời mời.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        invitationViewModel = new ViewModelProvider(this).get(InvitationViewModel.class);

        // --- Cập nhật logic hiển thị chi tiết quỹ ---
        // 1. Gọi API để lấy chi tiết quỹ ngay lập tức
        invitationViewModel.fetchFundDetails(fundId);

        // 2. Quan sát LiveData _fundDetails để cập nhật UI
        invitationViewModel.fundDetails.observe(this, fundDetails -> {
            if (fundDetails != null) {
                fundNameTextView.setText("Tên quỹ: " + fundDetails.getName());
                fundDescriptionTextView.setText("Mô tả: " + fundDetails.getDescription());
                // Bạn có thể hiển thị thêm thông tin như mục tiêu, ngày kết thúc nếu muốn
                // if (fundDetails.getHasTarget()) {
                //     // Hiển thị targetAmount và targetEndDate
                // }
            } else {
                Toast.makeText(this, "Không thể tải chi tiết quỹ.", Toast.LENGTH_SHORT).show();
                fundNameTextView.setText("Tên quỹ: Không khả dụng");
                fundDescriptionTextView.setText("Mô tả: Không khả dụng");
            }
        });

        // 3. Quan sát trạng thái tải và lỗi (tùy chọn nhưng nên có)
        invitationViewModel.isLoading.observe(this, isLoading -> {
            // Hiển thị/ẩn ProgressBar nếu có
            // if (isLoading) { showProgressBar(); } else { hideProgressBar(); }
        });

        invitationViewModel.errorMessage.observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // --- Logic cho nút Chấp nhận/Từ chối vẫn giữ nguyên ---
        acceptButton.setOnClickListener(v -> {
            invitationViewModel.acceptInvitation(fundId).observe(this, messageResponse -> {
                if (messageResponse != null) {
                    Toast.makeText(InvitationDetailActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(InvitationDetailActivity.this, "Không thể chấp nhận lời mời.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        rejectButton.setOnClickListener(v -> {
            invitationViewModel.rejectInvitation(fundId).observe(this, messageResponse -> {
                if (messageResponse != null) {
                    Toast.makeText(InvitationDetailActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(InvitationDetailActivity.this, "Không thể từ chối lời mời.", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}