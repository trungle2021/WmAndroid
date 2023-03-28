package com.example.wmandroid.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.Auth.AuthService;
import com.example.wmandroid.DTO.CustomerDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.FragmentImagePreUploadBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImagePreUploadFragment extends Fragment {
    FragmentImagePreUploadBinding binding;
    Bitmap bitmap;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentImagePreUploadBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            Uri uri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                                binding.avatarImgView.setImageBitmap(bitmap);
                                // Set the corner radius of the CardView to 16dp
                                float cornerRadius = getResources().getDimension(R.dimen._100pxh);

                                // Set the width and height of the CardView to 200dp x 200dp
                                int width = (int) getResources().getDimension(R.dimen._200pxh);
                                int height = (int) getResources().getDimension(R.dimen._200pxh);
                                ViewGroup.LayoutParams _layoutParams = binding.chooseFile.getLayoutParams();
                                _layoutParams.width = width;
                                _layoutParams.height = height;
                                binding.chooseFile.setLayoutParams(_layoutParams);
                                binding.chooseFile.setRadius(cornerRadius);

                                // Set the height of the ImageView to wrap image
                                ViewGroup.LayoutParams avatarImgViewLayoutParams = binding.avatarImgView.getLayoutParams();
                                avatarImgViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                                avatarImgViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                                binding.avatarImgView.setLayoutParams(avatarImgViewLayoutParams);


                                // Set the height of the Button to wrap content
                                ViewGroup.LayoutParams layoutParams = binding.btnUpload.getLayoutParams();
                                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                binding.btnUpload.setLayoutParams(layoutParams);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                });

        binding.chooseFile.setOnClickListener(v->{
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            activityResultLauncher.launch(intent);
        });
        binding.btnUpload.setOnClickListener(v->{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if(bitmap != null){
                //getImage
                //Convert to base 64
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                final String base64Image = Base64.encodeToString(bytes,Base64.DEFAULT);
                //call api update
                AuthService authService = ApiClient.createService(AuthService.class);
                CustomerDTO customerDTO = new CustomerDTO();
                int customerID = Integer.parseInt(ApiClient.getValue("customerID"));
                customerDTO.setId(customerID);
                customerDTO.setAvatar(base64Image);
                authService.updateAvatar(customerDTO).enqueue(new Callback<CustomerDTO>() {
                    @Override
                    public void onResponse(Call<CustomerDTO> call, Response<CustomerDTO> response) {
                        if(response.isSuccessful()){
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout_navigate,new ProfileFragment());
                            transaction.commit();
                        }else{
                            try {
                                String message = ApiClient.getError(response).getMessage();
                                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerDTO> call, Throwable t) {

                    }
                });
                //back to profile fragment

            }else{
                Toast.makeText(getActivity(),"Select the image first", Toast.LENGTH_LONG).show();
            }

        });

        super.onViewCreated(view, savedInstanceState);
    }
}