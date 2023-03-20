package com.example.wmandroid.API;

import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.OrganizeTeamDTO;
import com.example.wmandroid.Utils.SD_CLIENT;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import java.util.List;

import static com.example.wmandroid.Utils.SD_CLIENT.*;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN_APP_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/api/teams/all")
    Call<List<OrganizeTeamDTO>> getTeams();

}
