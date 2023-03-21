package com.example.wmandroid.API.Auth;

import static com.example.wmandroid.Utils.SD_CLIENT.api_customerLoginUrl;
import static com.example.wmandroid.Utils.SD_CLIENT.api_customerRegisterUrl;
import static com.example.wmandroid.Utils.SD_CLIENT.api_processChangePassword;
import static com.example.wmandroid.Utils.SD_CLIENT.api_processForgotPassword;

import com.example.wmandroid.DTO.LoginDTO;
import com.example.wmandroid.DTO.OrganizeTeamDTO;
import com.example.wmandroid.DTO.RegisterCustomerDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthService  {

    @POST(api_customerLoginUrl)
    Call<List<LoginDTO>> customerLogin(LoginDTO loginDTO);

    @POST(api_customerRegisterUrl)
    Call<List<LoginDTO>> customerRegister(RegisterCustomerDTO registerDTO);

    @POST(api_processForgotPassword)
    Call<List<LoginDTO>> processForgotPassword(RegisterCustomerDTO registerDTO);

    @POST(api_processChangePassword)
    Call<List<LoginDTO>> processChangePassword(RegisterCustomerDTO registerDTO);

}
