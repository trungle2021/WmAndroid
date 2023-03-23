package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.databinding.ActivityCheckOtpBinding;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOtpActivity extends AppCompatActivity {
    ActivityCheckOtpBinding checkOtpBinding;
    EditText otp1;
    EditText otp2;
    EditText otp3;
    EditText otp4;
    EditText otp5;
    EditText otp6;
    TextView tvSent;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkOtpBinding = ActivityCheckOtpBinding.inflate(getLayoutInflater());
        setContentView(checkOtpBinding.getRoot());
        Intent intent = getIntent();
         email = intent.getExtras().getString("email");
         checkEmailExists(email);
        initControls();
        setUpTextOTP();
        checkOtpBinding.btnVerify.setOnClickListener(v->{

            if(TextUtils.isEmpty( otp1.getText().toString()) ||
                TextUtils.isEmpty(otp2.getText().toString()) ||
                TextUtils.isEmpty(otp3.getText().toString()) ||
                TextUtils.isEmpty(otp4.getText().toString()) ||
                TextUtils.isEmpty(otp5.getText().toString()) ||
                TextUtils.isEmpty(otp6.getText().toString()))
            {
                Toast.makeText(this, "Incorrect OTP! Please try again!", Toast.LENGTH_SHORT).show();
            }else{
                String _otp1 = otp1.getText().toString();
                String _otp2 = otp2.getText().toString();
                String _otp3 = otp3.getText().toString();
                String _otp4 = otp4.getText().toString();
                String _otp5 = otp5.getText().toString();
                String _otp6 = otp6.getText().toString();

                String otp_user = _otp1 +_otp2 +_otp3 +_otp4 + _otp5 + _otp6;

                AuthService authService = ApiClient.createService(AuthService.class);
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),otp_user);
                authService.validToken(requestBody).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(CheckOtpActivity.this,ChangePasswordActivity.class);
                            intent.putExtra("otp",response.body());
                            startActivity(intent);
                        }else{
                            String message;
                            try {
                                message = ApiClient.getError(response).getMessage();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Toast.makeText(CheckOtpActivity.this, message, Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        checkOtpBinding.resendCode.setOnClickListener(v->{
            AuthService authService = ApiClient.createService(AuthService.class);
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),email);
            authService.processForgotPassword(requestBody).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(CheckOtpActivity.this, "We have just resent the code. Please check your email", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });
    }

    private void checkEmailExists(String emailAddress) {

        AuthService authService = ApiClient.createService(AuthService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"),emailAddress);
        authService.processForgotPassword(requestBody).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){

                }else{
                    if(response.code() == 404){
                        Toast.makeText(CheckOtpActivity.this, "Email not exists! Try another email!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void setUpTextOTP() {
        otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!otp1.getText().toString().isEmpty()){
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!otp2.getText().toString().isEmpty()){
                    otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!otp3.getText().toString().isEmpty()){
                    otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!otp4.getText().toString().isEmpty()){
                    otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        otp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!otp5.getText().toString().isEmpty()){
                    otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initControls() {
        otp1 = checkOtpBinding.otp1;
        otp2 = checkOtpBinding.otp2;
        otp3 = checkOtpBinding.otp3;
        otp4 = checkOtpBinding.otp4;
        otp5 = checkOtpBinding.otp5;
        otp6 = checkOtpBinding.otp6;
        tvSent = checkOtpBinding.tvSent;
        tvSent.setText("We've sent a 6-digit confirmation code to" + email +". Make sure you enter correct code.");
    }
}