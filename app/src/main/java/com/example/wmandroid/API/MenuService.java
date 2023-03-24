package com.example.wmandroid.API;

import static com.example.wmandroid.Utils.SD_CLIENT.api_venueImg_getAll;

import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueImgDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MenuService {
    @GET(api_venueImg_getAll)
    Call<List<FoodImageDTO>> foodImgAll();
}
