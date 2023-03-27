package com.example.wmandroid.API;





import static com.example.wmandroid.Utils.SD_CLIENT.*;

import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.ServiceDTO;
import com.example.wmandroid.DTO.VenueDTO;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HomeService {
    @GET(api_venue_getAll_active)
    Call<List<VenueDTO>> getVenueActive();
    @GET(api_order_getAll)
    Call<List<OrderDTO>> getAllOrder();
    @POST(api_order_create)
    Call<OrderDTO>createOrder(@Body OrderDTO order);
    @GET(api_order_getOne)
    Call<OrderDTO> getOnebyId(@Path("id") String id);
    @GET(api_order_getAll_food)
    Call<List<FoodDTO>> getAllFood();
    @GET(api_order_getAll_service)
    Call<List<ServiceDTO>> getAllService();

}
