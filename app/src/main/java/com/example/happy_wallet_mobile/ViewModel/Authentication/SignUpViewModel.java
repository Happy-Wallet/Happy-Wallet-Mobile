package com.example.happy_wallet_mobile.ViewModel.Authentication;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {
    private final MutableLiveData<String> name = new MutableLiveData<>();
    private final MutableLiveData<String> userName = new MutableLiveData<>();
    private final MutableLiveData<String> mail = new MutableLiveData<>();
    private final MutableLiveData<String> dateOfBirth = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<Boolean> signUpSuccess = new MutableLiveData<>();


    public void setName(String _name){name.setValue(_name);}
    public void setUserName(String _userName){userName.setValue(_userName);}
    public void setMail(String _mail){mail.setValue(_mail);}
    public void setDateOfBirth(String _dateOfBirth){dateOfBirth.setValue(_dateOfBirth);}
    public void setPassword(String _password){password.setValue(_password);}


    public LiveData<Boolean> getSignUpResult() {
        return signUpSuccess;
    }

    public void attempSignUp(){
        signUpSuccess.setValue(true);
    }
}
