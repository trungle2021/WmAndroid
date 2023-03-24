package com.example.wmandroid;

import static com.example.wmandroid.Utils.Regex.isValidEmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.databinding.ActivityForgotPasswordBinding;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding fpBinding;
    EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fpBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        email = fpBinding.tvEmail;
        setContentView(fpBinding.getRoot());
        getSupportActionBar().hide();

        fpBinding.btnSubmit.setOnClickListener(v->{
            String emailAddress = email.getText().toString();
            if(TextUtils.isEmpty(emailAddress)){
                email.setError("Email is required");
                return;
            }
            else if(!isValidEmail(emailAddress)){
                email.setError("Email Syntax Invalid");
                return;
            }else{
                Intent intent = new Intent(ForgotPasswordActivity.this, CheckOtpActivity.class);
                intent.putExtra("email",emailAddress);
                startActivity(intent);

            }
        });
    }
}