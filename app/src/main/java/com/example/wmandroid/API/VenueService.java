package com.example.wmandroid.API;

import static com.example.wmandroid.Utils.SD_CLIENT.api_customerLoginUrl;
import static com.example.wmandroid.Utils.SD_CLIENT.api_venueImg_getAll;
import static com.example.wmandroid.Utils.SD_CLIENT.api_venue_getAll;

import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.DTO.VenueImgDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface VenueService {
    @GET(api_venueImg_getAll)
    Call<List<VenueImgDTO>> venueImgAll();
    @GET(api_venue_getAll)
    Call<List<VenueDTO>> venueAll();
}
