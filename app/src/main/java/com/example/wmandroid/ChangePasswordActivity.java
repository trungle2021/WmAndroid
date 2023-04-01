package com.example.wmandroid;

import static com.example.wmandroid.Utils.Regex.isValidPassword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.PasswordDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.ActivityChangePasswordBinding;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding changePasswordBinding;
    EditText password;
    EditText cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changePasswordBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(changePasswordBinding.getRoot());
        password = changePasswordBinding.tvPassword;
        cpassword = changePasswordBinding.tvConfirmPassword;
        getSupportActionBar().hide();

        changePasswordBinding.btnResetPassword.setOnClickListener(v->{
            if(TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(cpassword.getText().toString())){
                Toast.makeText(this, "Fields is required", Toast.LENGTH_SHORT).show();
            }
            if (!isValidPassword(password.getText().toString())) {
                Toast.makeText(ChangePasswordActivity.this, "Password length must be from 5 to 20 letters", Toast.LENGTH_SHORT).show();
                password.requestFocus();
                return;
            }

            if(!password.getText().toString().equals(cpassword.getText().toString())){
                Toast.makeText(ChangePasswordActivity.this, "Password and Password Confirm must be matches", Toast.LENGTH_SHORT).show();
                cpassword.requestFocus();
                return;
            }
            Intent intent = getIntent();
            String otp = intent.getExtras().getString("otp");
            AuthService authService = ApiClient.createService(AuthService.class);

            PasswordDTO passwordDTO = new PasswordDTO();
            passwordDTO.setNewPassword(password.getText().toString());
            passwordDTO.setConfirmPassword(cpassword.getText().toString());
            passwordDTO.setToken(otp);
            authService.updatePasswordMobile(passwordDTO).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Intent intent1 = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent1);
                    }else{
                        Toast.makeText(ChangePasswordActivity.this, "Something went wrong! Please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        });







    }
}