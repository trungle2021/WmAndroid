package com.example.wmandroid;

import android.content.Intent;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anton46.stepsview.StepsView;
import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.API.MenuService;
import com.example.wmandroid.API.VenueService;
import com.example.wmandroid.Adapter.MenuAdapter;
import com.example.wmandroid.Adapter.VenueAdapter;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;
    VenueAdapter venueAdapter;
    MenuAdapter menuAdapter;
    Button btnCalendar;
    List<VenueImgDTO> venueImgDTOList=new ArrayList<>();
    List<FoodImageDTO> foodImageDTOS=new ArrayList<>();
    ApiClient apiClient = new ApiClient(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        this.getSupportActionBar().hide();
        initControl();
        mainBinding.recycleViewVenue.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mainBinding.recycleViewVenue.setAdapter(venueAdapter);
        mainBinding.recycleViewMenu.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mainBinding.recycleViewMenu.setAdapter(menuAdapter);
        calendarView();
        callApiVenueImg();
        callAPiMenuImg();
    }

    private void callAPiMenuImg() {
        MenuService menuService=apiClient.createService(MenuService.class);
        menuService.foodImgAll().enqueue(new Callback<List<FoodImageDTO>>() {
            @Override
            public void onResponse(Call<List<FoodImageDTO>> call, Response<List<FoodImageDTO>> response) {
                foodImageDTOS.clear();
                foodImageDTOS.addAll(response.body());
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FoodImageDTO>> call, Throwable t) {
                Log.d("MainActivity","Get All Food Img");
            }
        });
    }

    private void initControl() {
        venueAdapter = new VenueAdapter(this);
        menuAdapter = new MenuAdapter(this);
        //tao nut calendar
        btnCalendar=findViewById(R.id.btnCalendar);
        venueAdapter.setVenueData(venueImgDTOList);
        menuAdapter.setMenuData(foodImageDTOS);


    }
    private void calendarView()
    {

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,OrderCalendarActivity.class);
                startActivity(intent);
            }
        });
    }

    private void callApiVenueImg() {
        VenueService venueService = apiClient.createService(VenueService.class);
        venueService.venueImgAll().enqueue(new Callback<List<VenueImgDTO>>() {
            @Override
            public void onResponse(Call<List<VenueImgDTO>> call, Response<List<VenueImgDTO>> response) {
                venueImgDTOList.clear();
                venueImgDTOList.addAll(response.body());
                venueAdapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<List<VenueImgDTO>> call, Throwable t) {
                Log.d("MainActivity","Get All Venue Img");
            }
        });
    }
}
