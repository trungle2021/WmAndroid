package com.example.wmandroid;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    StepsView stepsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
    }


}
//    TextView tvID;
//    TextView tvTeamName;
//    Button btnCallApi;


//    private void clickCallApi() {
//        ApiService.apiService.getTeams().enqueue(new Callback<List<OrganizeTeamDTO>>() {
//            @Override
//            public void onResponse(Call<List<OrganizeTeamDTO>> call, Response<List<OrganizeTeamDTO>> response) {
//                OrganizeTeamDTO teamDTOList = response.body().stream().findFirst().get();
//                if(teamDTOList != null && response.isSuccessful()){
//                    tvID.setText(String.valueOf(teamDTOList.getId()));
//                    tvTeamName.setText(teamDTOList.getTeamName());
//                }
//                Toast.makeText(MainActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<OrganizeTeamDTO>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }