package com.example.wmandroid.API.Auth;

import static com.example.wmandroid.Utils.SD_CLIENT.api_customerLoginUrl;
import static com.example.wmandroid.Utils.SD_CLIENT.api_customerRegisterUrl;
import static com.example.wmandroid.Utils.SD_CLIENT.api_customers_getOne_RegisterCustomer;
import static com.example.wmandroid.Utils.SD_CLIENT.api_customervalidPhoneEmail;
import static com.example.wmandroid.Utils.SD_CLIENT.api_process_change_password;
import static com.example.wmandroid.Utils.SD_CLIENT.api_process_forgot_password;
import static com.example.wmandroid.Utils.SD_CLIENT.api_update_password_mobile;
import static com.example.wmandroid.Utils.SD_CLIENT.api_valid_otp;

import com.example.wmandroid.DTO.JWTAuthResponse;
import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.DTO.PasswordDTO;
import com.example.wmandroid.DTO.RegisterCustomerDTO;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService  {

    @POST(api_customerLoginUrl)
    Call<JWTAuthResponse> customerLogin(@Body LoginDTO loginDTO);

    @POST(api_customerRegisterUrl)
    Call<RegisterCustomerDTO> customerRegister(@Body RegisterCustomerDTO registerDTO);
    @POST(api_customervalidPhoneEmail)
    Call<RegisterCustomerDTO> customerValidPhoneEmail(@Body RegisterCustomerDTO registerDTO);
    @POST(api_process_forgot_password)
    Call<String> processForgotPassword(@Body RequestBody email);

    @POST(api_valid_otp)
    Call<String> validToken(@Body RequestBody otp);

    @POST(api_update_password_mobile)
    Call<String> updatePasswordMobile(@Body PasswordDTO passwordDTO);

    @GET(api_customers_getOne_RegisterCustomer)
    Call<RegisterCustomerDTO> getOneCustomer(@Path("id") int id);

}
