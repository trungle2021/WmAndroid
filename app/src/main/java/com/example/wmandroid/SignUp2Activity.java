package com.example.wmandroid;

import static com.example.wmandroid.Utils.Regex.isValidEmail;
import static com.example.wmandroid.Utils.Regex.isValidName;
import static com.example.wmandroid.Utils.Regex.isValidPassword;
import static com.example.wmandroid.Utils.Regex.isValidPhone;
import static com.example.wmandroid.Utils.Regex.isValidUsername;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.DTO.RegisterCustomerDTO;
import com.example.wmandroid.Utils.SD_CLIENT;
import com.example.wmandroid.databinding.ActivitySignUp2Binding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp2Activity extends AppCompatActivity {
    ActivitySignUp2Binding signUp2Binding;
    EditText username;
    EditText password;
    EditText cpassword;
    StepsView stepsView;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUp2Binding = ActivitySignUp2Binding.inflate(getLayoutInflater());
        setContentView(signUp2Binding.getRoot());
        init();
        Intent intent = getIntent();
        RegisterCustomerDTO registerDTO = (RegisterCustomerDTO) intent.getSerializableExtra("personalInfo");
        int current_state = intent.getExtras().getInt("current_state");
        int length = intent.getExtras().getInt("length");

        stepsView = signUp2Binding.stepsView;

        stepsView.setLabels(SD_CLIENT.stepSignUp)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.colorAccent))
                .setLabelColorIndicator(getResources().getColor(com.anton46.stepsview.R.color.orange))
                .setCompletedPosition(current_state)
                .drawView();

        stepsView.setCompletedPosition(current_state);

        signUp2Binding.btnSubmitPage2.setOnClickListener(v -> {


            if(validSignUp2(registerDTO)){
                AuthService authService = ApiClient.createService(AuthService.class);
                authService.customerRegister(registerDTO).enqueue(new Callback<RegisterCustomerDTO>() {
                    @Override
                    public void onResponse(Call<RegisterCustomerDTO> call, Response<RegisterCustomerDTO> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(SignUp2Activity.this, "Register Successfully", Toast.LENGTH_SHORT).show();
                            LoginDTO loginDTO = new LoginDTO();
                            loginDTO.setUsername(registerDTO.getUsername());
                            loginDTO.setPassword(registerDTO.getPassword());
                            authService.customerLogin(loginDTO).enqueue(new Callback<JWTAuthResponse>() {
                                @Override
                                public void onResponse(Call<JWTAuthResponse> call, Response<JWTAuthResponse> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getAccessToken() != null){
                                            storeToken(response); //store token in SharedPreferences
                                            //After Login Success, move to Home Activity;
                                            Intent intent = new Intent(SignUp2Activity.this, MainActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Toast.makeText(SignUp2Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }else{
                                        if(response.code() == 404){
                                            Toast.makeText(SignUp2Activity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JWTAuthResponse> call, Throwable t) {
                                    Toast.makeText(SignUp2Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            });

                        }else{

                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterCustomerDTO> call, Throwable t) {
                        Toast.makeText(SignUp2Activity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });



            }
        });

    }



    public boolean validSignUp2(RegisterCustomerDTO registerDTO){
        if (TextUtils.isEmpty(username.getText()) ||
                TextUtils.isEmpty(password.getText()) ||
                TextUtils.isEmpty(cpassword.getText()))
        {
            Toast.makeText(SignUp2Activity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!isValidUsername(username.getText().toString())) {
                Toast.makeText(SignUp2Activity.this, "Username length must be from 5 to 20 letters and no special letters", Toast.LENGTH_SHORT).show();
                return false;
            }
            if(!password.getText().toString().equals(cpassword.getText().toString())){
                Toast.makeText(SignUp2Activity.this, "Password and Password Confirm must be matches", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (!isValidPassword(password.getText().toString())) {
                Toast.makeText(SignUp2Activity.this, "Password length must be from 5 to 20 letters", Toast.LENGTH_SHORT).show();
                return false;
            }

            registerDTO.setUsername(username.getText().toString());
            registerDTO.setPassword(password.getText().toString());
            return true;
        }
    }

    public void init(){
        username = signUp2Binding.tvUsername;
        password = signUp2Binding.tvPassword;
        cpassword = signUp2Binding.tvConfirmPassword;
        stepsView = signUp2Binding.stepsView;
    }

    private void storeToken(Response<JWTAuthResponse> response){
        prefs=SignUp2Activity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();
        String saveToken=response.body().getAccessToken();
        edit.putString("token",saveToken);
        Log.i("Login",saveToken);
        edit.commit();
    }
}