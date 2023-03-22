package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.wmandroid.DTO.RegisterCustomerDTO;

public class SignUp2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        Intent intent = getIntent();
        RegisterCustomerDTO registerDTO = (RegisterCustomerDTO) intent.getSerializableExtra("personalInfo");
    }
}