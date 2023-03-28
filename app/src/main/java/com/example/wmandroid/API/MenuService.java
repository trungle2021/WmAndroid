package com.example.wmandroid.API;

import static com.example.wmandroid.Utils.SD_CLIENT.api_foodImg_getAll;
import static com.example.wmandroid.Utils.SD_CLIENT.api_food_getAll;
import static com.example.wmandroid.Utils.SD_CLIENT.api_venueImg_getAll;

import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueImgDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MenuService {
    @GET(api_foodImg_getAll)
    Call<List<FoodImageDTO>> foodImgAll();
    @GET(api_food_getAll)
    Call<List<FoodDTO>> foodAll();
}
