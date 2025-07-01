package com.example.happy_wallet_mobile.Data.Local;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.happy_wallet_mobile.Model.User;
import com.google.gson.Gson;

public class UserPreferences {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USER_JSON = "user_json";
    private static final String KEY_TOKEN = "auth_token";

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static Gson gson;

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public static void saveUser(User user, String token) {
        if (editor == null) return;
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER_JSON, userJson);
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public static User getUser() {
        if (sharedPreferences == null) return null;
        String userJson = sharedPreferences.getString(KEY_USER_JSON, null);
        return (userJson != null) ? gson.fromJson(userJson, User.class) : null;
    }

    public static String getToken() {
        if (sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public static boolean isLoggedIn() {
        return getToken() != null;
    }

    public static void clear() {
        if (editor != null) {
            editor.clear();
            editor.apply();
        }
    }
}
