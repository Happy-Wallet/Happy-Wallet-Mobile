package com.example.happy_wallet_mobile.ViewModel.Setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.happy_wallet_mobile.Data.Local.UserPreferences;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.SettingService;
import com.example.happy_wallet_mobile.Data.Remote.Request.Setting.ChangePasswordRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Setting.ChangePasswordResponse;
import com.example.happy_wallet_mobile.Data.Repository.SettingRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changeSuccess = new MutableLiveData<>();

    private final SettingRepository repository = new SettingRepository();

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getMessage() { return message; }
    public LiveData<Boolean> getChangeSuccess() { return changeSuccess; }

    public void changePassword(String currentPassword, String newPassword) {
        isLoading.setValue(true);

        repository.changePassword(UserPreferences.getToken(), currentPassword, newPassword,
                new SettingRepository.ChangePasswordCallback() {
                    @Override
                    public void onSuccess(String msg) {
                        isLoading.postValue(false);
                        message.postValue(msg);
                        changeSuccess.postValue(true);
                    }

                    @Override
                    public void onFailure(String errMsg) {
                        isLoading.postValue(false);
                        message.postValue(errMsg);
                        changeSuccess.postValue(false);
                    }
                });
    }
}


