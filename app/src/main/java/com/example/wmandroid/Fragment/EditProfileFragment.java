package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.RegisterCustomerDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.FragmentEditProfileBinding;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    int customerID;
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

                customerID = customerRegisterDTO.getCustomerId();
                binding.tvFirstname.setText(customerRegisterDTO.getFirst_name());
                binding.tvLastName.setText(customerRegisterDTO.getLast_name());
                binding.tvPhone.setText(customerRegisterDTO.getPhone());
                binding.tvEmail.setText(customerRegisterDTO.getEmail());
                binding.tvAddress.setText(customerRegisterDTO.getAddress());
                binding.tvUsername.setText(customerRegisterDTO.getUsername());

                if(customerRegisterDTO.getGender().equals("Male")){
                // Set the RadioButton as selected by default
                    binding.radioMale.setChecked(true);
                }else if(customerRegisterDTO.getGender().equals("Female")){
                    binding.radioFemale.setChecked(true);
                }else{
                    Log.d("Error","Cannot set value for radio gender");
                }
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.btnSaveChanges.setOnClickListener(v -> {
            String firstName = binding.tvFirstname.getText().toString();
            String lastName = binding.tvLastName.getText().toString();
            String phone = binding.tvPhone.getText().toString();
            String email = binding.tvEmail.getText().toString();
            String tvAddress = binding.tvAddress.getText().toString();
            String username = binding.tvUsername.getText().toString();
            String password = binding.tvPassword.getText().toString();
            String gender = getGender();

            RegisterCustomerDTO registerDTO = new RegisterCustomerDTO();
            registerDTO.setFirst_name(firstName);
            registerDTO.setLast_name(lastName);
            registerDTO.setPhone(phone);
            registerDTO.setEmail(email);
            registerDTO.setAddress(tvAddress);
            registerDTO.setUsername(username);
            registerDTO.setPassword(password);
            registerDTO.setGender(gender);
            registerDTO.setCustomerId(customerID);

            AuthService authService = ApiClient.createService(AuthService.class);
            authService.customerUpdate(registerDTO).enqueue(new Callback<RegisterCustomerDTO>() {
                @Override
                public void onResponse(Call<RegisterCustomerDTO> call, Response<RegisterCustomerDTO> response) {


                        if(response.isSuccessful()){
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout_navigate,new ProfileFragment());
                            transaction.commit();
                        } else{

                            String message = null;
                            try {
                                message = ApiClient.getError(response).getMessage();
                            } catch (IOException e) {
                                Log.d("Exception call API update customer profile",e.getMessage());
                            }
                            Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                        }


                }

                @Override
                public void onFailure(Call<RegisterCustomerDTO> call, Throwable t) {
                    Log.d("Call API Update Profile Failed",t.getMessage());
                }
            });
        });
        binding.arrowBack.setOnClickListener(v->{
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout_navigate, new ProfileFragment());
            transaction.commit();
        });



        super.onViewCreated(view, savedInstanceState);
    }

    public String getGender() {
        // get selected radio button from radioGroup
        int selectedId = binding.radioGenderGroup.getCheckedRadioButtonId();

        // find the radiobutton by returned id
       RadioButton radioButton = binding.radioGenderGroup.findViewById(selectedId);
        return radioButton.getText().toString();
    }

}