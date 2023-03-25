package com.example.wmandroid.API;

import static android.content.Intent.getIntent;

import com.example.wmandroid.DTO.ErrorDetails;
import com.example.wmandroid.LoginActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.wmandroid.Utils.SD_CLIENT.*;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;

public class ApiClient{
    private static Retrofit retrofit;
    private static Activity activity;
    public ApiClient(Activity activity) {
        this.activity = activity;
    }

    private static final ArrayList<String> EXCLUDED_API = new ArrayList<String>(){
        {
            add(api_customerLoginUrl);
            add(api_customerRegisterUrl);
            add(api_customervalidPhoneEmail);
            add(api_process_forgot_password);
            add(api_process_change_password);
            add(api_valid_otp);
            add(api_update_password_mobile);
        }
    };
    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public static String getToken(){
        SharedPreferences prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("auth_token","");
        return token;
    }

    public void removeToken(){
        SharedPreferences prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("auth_token"); // remove the token with the key "auth_token"
        editor.apply(); // save the changes
    }


    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            // Check if the URL matches the excluded URL
            for (String api: EXCLUDED_API) {
                if (request.url().toString().equals(api)) {
                    // Skip the interceptor and proceed with the request as-is
                    return chain.proceed(request);
                }
            }

            Request newRequest  = chain.request().newBuilder()
                    .addHeader("User-Agent","Android 11;Pixel 6")
                    .addHeader("Authorization", "Bearer " + getToken())
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(DOMAIN_APP_API)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }

    public static ErrorDetails getError(retrofit2.Response response) throws IOException {
        if (response.errorBody() != null) {
            String errorJson = response.errorBody().string();
            return new Gson().fromJson(errorJson, ErrorDetails.class);
        }
        return null;
    }



}
