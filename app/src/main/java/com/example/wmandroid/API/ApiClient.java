package com.example.wmandroid.API;

import com.example.wmandroid.Utils.SD_CLIENT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.wmandroid.Utils.SD_CLIENT.*;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {
    private static Retrofit retrofit;
    private static Activity activity1;
    private static final ArrayList<String> EXCLUDED_API = new ArrayList<String>(){
        {
            add(SD_CLIENT.DOMAIN_APP_API + api_customerLoginUrl);
            add(SD_CLIENT.DOMAIN_APP_API + api_customerRegisterUrl);
            add(SD_CLIENT.DOMAIN_APP_API + api_processForgotPassword);
            add(SD_CLIENT.DOMAIN_APP_API + api_processChangePassword);
        }
    };
    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    public static String getToken(Activity activity){
        activity1 = activity;
        SharedPreferences prefs = activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
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
                    .addHeader("Authorization", "Bearer " + getToken(activity1))
                    .build();
            return chain.proceed(newRequest);
        }
    }).build();

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(DOMAIN_APP_API)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }

}
