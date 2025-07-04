package com.example.happy_wallet_mobile.ViewModel.Community;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Model.FundActivity;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.CommunityService;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectActivityViewModel extends ViewModel {

    private MutableLiveData<List<FundActivity>> _availableActivities = new MutableLiveData<>();
    public LiveData<List<FundActivity>> availableActivities = _availableActivities;

    private MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public LiveData<Boolean> isLoading = _isLoading;

    private MutableLiveData<String> _errorMessage = new MutableLiveData<>();
    public LiveData<String> errorMessage = _errorMessage;

    private CommunityService communityService; // Hoặc FundService

    public SelectActivityViewModel() {
        communityService = APIClient.getRetrofit().create(CommunityService.class);
        // Bạn có thể gọi fetchAvailableActivities() ngay tại đây nếu muốn tải ngay khi dialog mở
        // fetchAvailableActivities();
    }

    public void fetchAvailableActivities() {
        _isLoading.setValue(true);
        _errorMessage.setValue(null);

        // TODO: Thay thế bằng user_id của người dùng hiện tại
        // Hiện tại dùng API getUserFundActivities, bạn có thể cân nhắc getFundActivitiesByFund nếu có nhiều quỹ
        communityService.getUserFundActivities().enqueue(new Callback<List<FundActivity>>() {
            @Override
            public void onResponse(@NonNull Call<List<FundActivity>> call, @NonNull Response<List<FundActivity>> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    _availableActivities.setValue(response.body());
                    Log.d("SelectActivityVM", "Fetched " + response.body().size() + " activities.");
                } else {
                    String errorBody = "";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("SelectActivityVM", "Error parsing error body", e);
                    }
                    String msg = "Failed to fetch activities: " + response.code() + " - " + errorBody;
                    _errorMessage.setValue(msg);
                    Log.e("SelectActivityVM", msg);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FundActivity>> call, @NonNull Throwable t) {
                _isLoading.setValue(false);
                String msg = "API Call Failed for activities: " + t.getMessage();
                _errorMessage.setValue(msg);
                Log.e("SelectActivityVM", msg, t);
            }
        });
    }
}