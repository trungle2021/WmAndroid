package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.Adapter.OrderAdapter;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.FragmentMyOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyOrderFragment extends Fragment {

    FragmentMyOrderBinding binding;
    RecyclerView rcv;
    List<OrderDTO> orderDTOList = new ArrayList<>();
    OrderAdapter orderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyOrderBinding.inflate(inflater,container,false);
        rcv = binding.rcvOrder;
        if(getContext() != null){
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
            rcv.setLayoutManager(layoutManager);
            orderAdapter = new OrderAdapter(getContext());
            rcv.setAdapter(orderAdapter);
        }
        HomeService homeService = ApiClient.createService(HomeService.class);
        int customerId = Integer.parseInt(ApiClient.getValue("customerID"));
        homeService.getAllOrderByCustomer(customerId).enqueue(new Callback<List<OrderDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                if (response.isSuccessful()) {
                    orderDTOList.clear();
                    orderDTOList.addAll(response.body());
                    orderAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDTO>> call, Throwable t) {

            }
        });

        orderAdapter.setData(orderDTOList);

        return binding.getRoot();
    }


}