package com.example.wmandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding signUpBinding;
    StepsView stepsView;

    int current_state = 0;

    String[] descriptionsData = {"Personal Information", "User Account"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(signUpBinding.getRoot());
        stepsView = signUpBinding.stepsView;

        stepsView = signUpBinding.stepsView;

        stepsView.setLabels(descriptionsData)
                .setBarColorIndicator(Color.BLACK)
                .setProgressColorIndicator(getResources().getColor(R.color.colorAccent))
                .setLabelColorIndicator(getResources().getColor(com.anton46.stepsview.R.color.orange))
                .setCompletedPosition(0)
                .drawView();

        stepsView.setCompletedPosition(current_state);
        int length = descriptionsData.length;

        signUpBinding.btnPInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current_state < (length -1 )){
                    current_state +=1;
                    signUpBinding.stepsView.setCompletedPosition(current_state).drawView();
                }
                Log.d("current_state = ",current_state + " ");
            }
        });

//        signUpBinding.btnDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(current_state > 0 ){
//                    current_state -=1;
//                    signUpBinding.stepsView.setCompletedPosition(current_state).drawView();
//                }
//                Log.d("current_state = ",current_state + " ");
//
//            }
//        });


    }
}