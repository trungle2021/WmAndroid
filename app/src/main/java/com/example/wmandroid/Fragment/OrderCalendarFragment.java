package com.example.wmandroid.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.Adapter.RealVenueAdapter;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.LoginActivity;
import com.example.wmandroid.R;
import com.example.wmandroid.Service.imp.OrderServiceImp;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderCalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderCalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderCalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderCalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderCalendarFragment newInstance(String param1, String param2) {
        OrderCalendarFragment fragment = new OrderCalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    CalendarView calendarView;
    List<VenueDTO> venueList;
    List<OrderDTO> orderList;
    RealVenueAdapter adapter;
    ListView listView;
    Activity activity;

    public OrderCalendarFragment(View view, CalendarView calendarView, List<VenueDTO> venueList, List<OrderDTO> orderList, RealVenueAdapter adapter, ListView listView, Activity activity) {
        this.view = view;
        this.calendarView = calendarView;
        this.venueList = venueList;
        this.orderList = orderList;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
    }

    public OrderCalendarFragment(int contentLayoutId, View view, CalendarView calendarView, List<VenueDTO> venueList, List<OrderDTO> orderList, RealVenueAdapter adapter, ListView listView, Activity activity) {
        super(contentLayoutId);
        this.view = view;
        this.calendarView = calendarView;
        this.venueList = venueList;
        this.orderList = orderList;
        this.adapter = adapter;
        this.listView = listView;
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_order_calendar, container, false);
            init();
            Toast.makeText(getContext(), "View inflated for the first time", Toast.LENGTH_SHORT).show();
            getVenue();

        return view;
    }




    public void init()
    {

        calendarView=view.findViewById(R.id.calendarView);
        listView=view.findViewById(R.id.listViewVenueKhang);

    }

    //get venue
    public  void getVenue()
    {
        OrderServiceImp orderService = new OrderServiceImp();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String strDay;
                String strMonth;
                String strYear= String.valueOf(year);
                month+=1;
                if(dayOfMonth<10)
                {strDay="0"+dayOfMonth;}
                else{strDay=String.valueOf(dayOfMonth);}
                if(month<10)
                {strMonth="0"+month;}
                else{strMonth=String.valueOf(month);}
                String date = strYear + "-" + strMonth + "-" + strDay;
                Toast.makeText(getContext(), date, Toast.LENGTH_SHORT).show();
                try{
                    //call venue active
                    HomeService homeService = ApiClient.createService(HomeService.class);
                    homeService.getVenueActive().enqueue(new Callback<List<VenueDTO>>() {

                        @Override
                        public void onResponse(Call<List<VenueDTO>> call, Response<List<VenueDTO>> response) {
                            venueList= response.body();
//                     String id=  String.valueOf(activeVenueDTOActive.get(0).getId());
//                        Log.i("d",id);
                           }

                        @Override
                        public void onFailure(Call<List<VenueDTO>> call, Throwable t) {

//                            chuyen ve6 login test
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });
//get orderlist
                    homeService.getAllOrder().enqueue(new Callback<List<OrderDTO>>() {
                        @Override
                        public void onResponse(Call<List<OrderDTO>> call, Response<List<OrderDTO>> response) {
                            orderList=response.body();
                        }
                        @Override
                        public void onFailure(Call<List<OrderDTO>> call, Throwable t) {
                          Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                    List<VenueBooked>bookeds= orderService.getVenuesBooked(date,venueList,orderList);

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(bookeds);
                    Log.i("msg",jsonString);
                    //show venue


                    adapter=new RealVenueAdapter(bookeds,activity,requireActivity().getSupportFragmentManager());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
//
                }
                catch(Exception e){
                    Log.i("error",e.getMessage());

                }
//
            }
        });

    }




}