package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wmandroid.DTO.RegisterCustomerDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.FragmentEditProfileBinding;
import com.google.gson.Gson;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater,container,false);

        getParentFragmentManager().setFragmentResultListener("dataFromProfileFragment", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Gson gson = new Gson();
                String data = result.getString("userInfo");
                RegisterCustomerDTO customerRegisterDTO = gson.fromJson(data,RegisterCustomerDTO.class);

                binding.tvFirstname.setText(customerRegisterDTO.getFirst_name());
                binding.tvLastName.setText(customerRegisterDTO.getLast_name());
                binding.tvPhone.setText(customerRegisterDTO.getPhone());
                binding.tvEmail.setText(customerRegisterDTO.getEmail());
                binding.tvAddress.setText(customerRegisterDTO.getAddress());
                binding.tvUsername.setText(customerRegisterDTO.getUsername());

            }
        });

        return binding.getRoot();
    }
}