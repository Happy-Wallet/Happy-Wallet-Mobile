package com.example.happy_wallet_mobile.Data.Remote;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException; // Import IOException

public class APIClient {
    private static Retrofit retrofit;

    private static final String BASE_URL = "http://10.0.2.2:3000/";

    private static String authToken = null;

    public static void setAuthToken(String token) {
        authToken = token;
        retrofit = null;
    }

    public static Retrofit getRetrofit(){
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging) // Thêm logging interceptor
                    .addInterceptor(new Interceptor() { // Thêm interceptor cho Authorization header
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder();

                            if (authToken != null && !authToken.isEmpty()) {
                                requestBuilder.header("Authorization", "Bearer " + authToken);
                            }

                            requestBuilder.method(original.method(), original.body());
                            return chain.proceed(requestBuilder.build());
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}