package com.example.happy_wallet_mobile.ViewModel.Authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.happy_wallet_mobile.Data.Remote.ApiInterface.AuthService;
import com.example.happy_wallet_mobile.Data.Remote.APIClient;
import com.example.happy_wallet_mobile.Data.Remote.Request.Auth.RegisterRequest;
import com.example.happy_wallet_mobile.Data.Remote.Response.Auth.RegisterResponse;

import com.example.happy_wallet_mobile.Data.Repository.AuthRepository;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private final AuthRepository authRepository = new AuthRepository();

    private final MediatorLiveData <RegisterResponse> _registerResponse = new MediatorLiveData<>();

    public LiveData<RegisterResponse> getRegisterResponse() {
        return _registerResponse;
    }

    public void register(String email, String username, String password, String dateOfBirth) {
        RegisterRequest request = new RegisterRequest(email, username, password, dateOfBirth);

        AuthService authService = APIClient.getRetrofit().create(AuthService.class);
        Call<RegisterResponse> call = authService.register(request);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful()) {
                    _registerResponse.setValue(response.body());
                } else {
                    _registerResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                _registerResponse.setValue(null);
            }
        });
    }

}
