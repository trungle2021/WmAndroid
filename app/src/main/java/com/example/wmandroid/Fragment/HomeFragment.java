package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.MenuService;
import com.example.wmandroid.API.VenueService;
import com.example.wmandroid.Adapter.MenuAdapter;
import com.example.wmandroid.Adapter.VenueAdapter;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {
    VenueAdapter venueAdapter;
    MenuAdapter menuAdapter;
    List<VenueImgDTO> venueImgDTOList=new ArrayList<>();
    List<FoodImageDTO> foodImageDTOS=new ArrayList<>();
    View view;
    TextView seeMoreVenuesTV;
    TextView seeMoreMenusTV;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerViewVenue=view.findViewById(R.id.recycleViewVenue);
        RecyclerView recyclerViewMenu=view.findViewById(R.id.recycleViewMenu);
        seeMoreMenusTV=view.findViewById(R.id.seeMoreMenusBtn);
        seeMoreVenuesTV=view.findViewById(R.id.seeMoreVenuesBtn);
        recyclerViewVenue.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        venueAdapter = new VenueAdapter(getActivity());
        menuAdapter = new MenuAdapter(getActivity());
        recyclerViewMenu.setAdapter(menuAdapter);
        recyclerViewVenue.setAdapter(venueAdapter);
        callAPiMenuImg();
        callApiVenueImg();
        venueAdapter.setVenueData(venueImgDTOList);
        menuAdapter.setMenuData(foodImageDTOS);
        VenueDetail(seeMoreVenuesTV);
        MenuDetail(seeMoreMenusTV);
        return view;
    }

    private void MenuDetail( TextView menu) {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức chuyển sang Fragment khác
                // Truyền vào Activity gốc, FragmentManager và Fragment muốn chuyển sang

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_navigate, new MenuDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        menu.setOnClickListener(myOnClickListener);
    }

    private void VenueDetail(TextView venue) {
        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gọi phương thức chuyển sang Fragment khác
                // Truyền vào Activity gốc, FragmentManager và Fragment muốn chuyển sang

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout_navigate, new VenueDetailFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        venue.setOnClickListener(myOnClickListener);
    }

    private void callAPiMenuImg() {
        MenuService menuService= ApiClient.createService(MenuService.class);
        menuService.foodImgAll().enqueue(new Callback<List<FoodImageDTO>>() {
            @Override
            public void onResponse(Call<List<FoodImageDTO>> call, Response<List<FoodImageDTO>> response) {
                foodImageDTOS.clear();
                foodImageDTOS.addAll(response.body());
                menuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<FoodImageDTO>> call, Throwable t) {
                Log.d("Venue & Food fragment","Get All Food Img");
            }
        });
    }
    private void callApiVenueImg() {
        VenueService venueService = ApiClient.createService(VenueService.class);
        venueService.venueImgAll().enqueue(new Callback<List<VenueImgDTO>>() {
            @Override
            public void onResponse(Call<List<VenueImgDTO>> call, Response<List<VenueImgDTO>> response) {
                venueImgDTOList.clear();
                venueImgDTOList.addAll(response.body());
                venueAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<VenueImgDTO>> call, Throwable t) {
                Log.d("Venue & Food fragment","Get All Venue Img");
            }
        });
    }
}