package com.example.wmandroid.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.RegisterCustomerDTO;
import com.example.wmandroid.LoginActivity;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.FragmentProfileBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;

    public ProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater,container,false);
        binding.btnEditProfile.setOnClickListener(v->{
            replaceFragment(new EditProfileFragment());
        });


        binding.bookingImgView
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewGroup parentView = (ViewGroup) view.getParent();
//                parentView.removeView(view);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(ProfileFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_navigate, new OrderCalendarFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return binding.getRoot();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AuthService authService = ApiClient.createService(AuthService.class);
        ApiClient apiClient = new ApiClient();
        int customerID = Integer.parseInt(apiClient.getValue("customerID"));
        authService.getOneCustomer(customerID).enqueue(new Callback<RegisterCustomerDTO>() {
            @Override
            public void onResponse(Call<RegisterCustomerDTO> call, Response<RegisterCustomerDTO> response) {
                String firstName = response.body().getFirst_name() == null ? "" : response.body().getFirst_name();
                String lastName =response.body().getLast_name() == null? "" : response.body().getLast_name();
                String phone =response.body().getPhone() == null ? "" : response.body().getPhone();
                String email =response.body().getEmail() == null ? "" : response.body().getEmail();
                String address =response.body().getAddress() == null ? "" : response.body().getAddress();

                binding.tvFullName.setText(firstName +" "+ lastName);
                binding.tvPhone.setText(phone);
                binding.tvEmail.setText(email);
                binding.tvAddress.setText(address);


                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                Bundle result = new Bundle();
                result.putString("userInfo",json);
                getParentFragmentManager().setFragmentResult("dataFromProfileFragment",result);
            }

            @Override
            public void onFailure(Call<RegisterCustomerDTO> call, Throwable t) {

            }
        });
        binding.btnLogout.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            ApiClient.removeToken();

        });
        super.onViewCreated(view, savedInstanceState);
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_navigate,fragment);
        transaction.commit();
    }
}