package com.example.wmandroid;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.wmandroid.API.ApiService;
import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.OrganizeTeamDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvID;
    TextView tvTeamName;
    Button btnCallApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvID = findViewById(R.id.tvTeam_ID);
        tvTeamName = findViewById(R.id.tvTeam_TeamName);
        btnCallApi = findViewById(R.id.btnCallApi);


        btnCallApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi();
            }
        });



    }

    private void clickCallApi() {
        ApiService.apiService.getTeams().enqueue(new Callback<List<OrganizeTeamDTO>>() {
            @Override
            public void onResponse(Call<List<OrganizeTeamDTO>> call, Response<List<OrganizeTeamDTO>> response) {
                OrganizeTeamDTO teamDTOList = response.body().stream().findFirst().get();
                if(teamDTOList != null && response.isSuccessful()){
                    tvID.setText(String.valueOf(teamDTOList.getId()));
                    tvTeamName.setText(teamDTOList.getTeamName());
                }
                Toast.makeText(MainActivity.this, "Call Api Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<OrganizeTeamDTO>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call Api Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}