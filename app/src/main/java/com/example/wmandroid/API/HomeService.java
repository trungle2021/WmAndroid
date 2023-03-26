package com.example.wmandroid.API;





import static com.example.wmandroid.Utils.SD_CLIENT.*;

import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HomeService {
    @GET(api_venue_getAll_active)
    Call<List<VenueDTO>> getVenueActive();
    @GET(api_order_getAll)
    Call<List<OrderDTO>> getAllOrder();
    @POST(api_order_create)
    Call<OrderDTO>createOrder(@Body OrderDTO order);
}
