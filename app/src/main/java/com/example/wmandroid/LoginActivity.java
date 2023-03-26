package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.databinding.ActivityLoginBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    ActivityLoginBinding loginBinding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();
        setContentView(loginBinding.getRoot());

        loginBinding.btnLogin.setOnClickListener(v -> {
            String username = loginBinding.tvUsername.getText().toString();
            String password = loginBinding.tvPassword.getText().toString();
            LoginDTO loginDTO = new LoginDTO();
                loginDTO.setUsername(username);
                loginDTO.setPassword(password);
                ApiClient apiClient = new ApiClient(this);
            AuthService authService = apiClient
                    .createService(AuthService.class);
            authService.customerLogin(loginDTO).enqueue(new Callback<JWTAuthResponse>() {
                @Override
                public void onResponse(Call<JWTAuthResponse> call, Response<JWTAuthResponse> response) {
                   if(response.isSuccessful()){
                       if(response.body().getAccessToken() != null){
                           storeToken(response); //store token in SharedPreferences
                           Intent intent = new Intent(LoginActivity.this, NavigateActivity.class);
                           startActivity(intent);
                       }else{
                           Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                           return;
                       }
                   }else{
                       if(response.code() == 404){
                           Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                            return;
                       }
                   }


                }

                @Override
                public void onFailure(Call<JWTAuthResponse> call, Throwable t) {

                }
            });
        });

        loginBinding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        loginBinding.textSignUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });




    }

    private void storeToken(Response<JWTAuthResponse> response){
        prefs=LoginActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();
        String saveToken=response.body().getAccessToken();
        edit.putString("auth_token",saveToken);
        edit.putString("customerID",parseJWT(saveToken,"userID"));
        Log.i("Login",saveToken);
        Log.i("customerID",saveToken);
        edit.commit();
    }

    private String parseJWT(String token,String name){
        JWT jwt = new JWT(token);
        Claim subscriptionMetaData = jwt.getClaim(name);
        String parsedValue = subscriptionMetaData.asString();

        return parsedValue;
    }

}