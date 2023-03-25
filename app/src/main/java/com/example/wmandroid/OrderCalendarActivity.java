package com.example.wmandroid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.Adapter.RealVenueAdapter;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.Service.imp.OrderServiceImp;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderCalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    List<VenueDTO> venueList;
    List<OrderDTO> orderList;
    RealVenueAdapter adapter;
    ListView listView;
    Activity activity;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_calendar);

        // create instance of OrderServiceImp
//        init
        init();
        getVenue();


    }


    public void init()
    {
        activity=this;
        calendarView = findViewById(R.id.calendarView);
        listView=findViewById(R.id.listViewVenueKhang);

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
                try{
                    //call venue active
                    HomeService homeService = ApiClient.createService(HomeService.class);
                    homeService.getVenueActive().enqueue(new Callback<List<VenueDTO>>() {

                        @Override
                        public void onResponse(Call<List<VenueDTO>> call, Response<List<VenueDTO>> response) {
                            venueList= response.body();
//                     String id=  String.valueOf(activeVenueDTOActive.get(0).getId());
//                        Log.i("d",id);
                            Toast.makeText(OrderCalendarActivity.this, date, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<VenueDTO>> call, Throwable t) {

                            //chuyen ve6 login test
                            Intent intent = new Intent(OrderCalendarActivity.this, LoginActivity.class);
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
                            Intent intent = new Intent(OrderCalendarActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });

                    List<VenueBooked>bookeds= orderService.getVenuesBooked(date,venueList,orderList);

                    Gson gson = new Gson();
                    String jsonString = gson.toJson(bookeds);
                    Log.i("msg",jsonString);
                    //show venue


                    adapter=new RealVenueAdapter(bookeds,activity);
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

    public void createOrder()
        {}







}