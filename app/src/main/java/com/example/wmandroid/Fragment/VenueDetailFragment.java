package com.example.wmandroid.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.VenueService;
import com.example.wmandroid.Adapter.VenueDetailAdapter;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VenueDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VenueDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    RecyclerView recyclerView;
    VenueDetailAdapter adapter;
    List<VenueDTO> venueDTOList=new ArrayList<>();
    public VenueDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VenueDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VenueDetailFragment newInstance(String param1, String param2) {
        VenueDetailFragment fragment = new VenueDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view=inflater.inflate(R.layout.fragment_venue_detail, container, false);
        recyclerView= view.findViewById(R.id.rcv_venue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter=new VenueDetailAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        CallVenueApi();
        adapter.setDataVenueDetail(venueDTOList);
        return view;
    }

    private void CallVenueApi() {
        VenueService venueService= ApiClient.createService(VenueService.class);
        venueService.venueAll().enqueue(new Callback<List<VenueDTO>>() {
            @Override
            public void onResponse(Call<List<VenueDTO>> call, Response<List<VenueDTO>> response) {
                venueDTOList.clear();
                venueDTOList.addAll(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<VenueDTO>> call, Throwable t) {
                Log.d("Venue Detail fragment","Get All Venue ");
            }
        });
    }
}