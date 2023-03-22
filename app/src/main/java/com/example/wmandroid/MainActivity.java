package com.example.wmandroid;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    StepsView stepsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        AuthService authService = ApiClient.createService(AuthService.class);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername("trungle");
        loginDTO.setPassword("trungle");
            authService.customerLogin(loginDTO).enqueue(new Callback<JWTAuthResponse>() {
                @Override
                public void onResponse(Call<JWTAuthResponse> call, Response<JWTAuthResponse> response) {
                    JWTAuthResponse jwtAuthResponse = response.body();
                }

                @Override
                public void onFailure(Call<JWTAuthResponse> call, Throwable t) {
                   Log.d("Error ","Error API",t);
                }
            });

    }

    private void callAPILogin(){

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