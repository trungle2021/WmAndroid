package com.example.wmandroid.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.IOException;

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
                String gender =response.body().getGender() == null ? "" : response.body().getGender();
                String avatar =response.body().getAvatar() == null ? "" : response.body().getAvatar();

                binding.tvFullName.setText(firstName +" "+ lastName);
                binding.tvPhone.setText(phone);
                binding.tvEmail.setText(email);
                binding.tvAddress.setText(address);
                binding.tvGender.setText(gender);
                binding.tvEmailUser.setText(email);
                if(!avatar.equals("")){
                    byte[] bytes = Base64.decode(avatar,Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    binding.avatarImgView.setImageBitmap(bitmap);
                }

                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                Bundle result = new Bundle();
                result.putString("userInfo",json);
                getParentFragmentManager().setFragmentResult("dataFromProfileFragment",result);
            }

            @Override
            public void onFailure(Call<RegisterCustomerDTO> call, Throwable t) {
                Log.d(" Get customer info",t.getMessage());
            }
        });
        binding.btnLogout.setOnClickListener(v->{
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            ApiClient.removeToken();

        });
        binding.cardView.setOnClickListener(v->{
            replaceFragment(new ImagePreUploadFragment());
        });
        super.onViewCreated(view, savedInstanceState);

    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_navigate,fragment);
        transaction.commit();
    }
}