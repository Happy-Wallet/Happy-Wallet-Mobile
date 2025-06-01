package com.example.happy_wallet_mobile.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignInViewModel extends ViewModel {

    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> signInSuccess = new MutableLiveData<>();

    public void setUserName(String name) {
        userName.setValue(name);
    }

    public void setPassword(String pwd) {
        password.setValue(pwd);
    }

    public LiveData<Boolean> getSignInResult() {
        return signInSuccess;
    }

    public void attemptSignIn() {
        String u = userName.getValue();
        String p = password.getValue();

        // Giả lập kiểm tra đơn giản
        if ("admin".equals(u) && "123456".equals(p)) {
            signInSuccess.setValue(true);
        } else {
            signInSuccess.setValue(false);
        }
    }
}
