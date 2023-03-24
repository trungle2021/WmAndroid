package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.wmandroid.databinding.ActivityOrderCalendarBinding;

public class OrderCalendarActivity extends AppCompatActivity {

    ActivityOrderCalendarBinding calendarBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarBinding = ActivityOrderCalendarBinding.inflate(getLayoutInflater());
        setContentView(calendarBinding.getRoot());



    }
}