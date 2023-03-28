package com.example.wmandroid.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.Fragment.BookingDetailFragment;
import com.example.wmandroid.Fragment.ProfileFragment;
import com.example.wmandroid.R;
import com.example.wmandroid.Service.imp.OrderServiceImp;
import com.example.wmandroid.SignUp2Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RealVenueAdapter  extends BaseAdapter {
    List<VenueBooked> list;
    Activity activity;
    private FragmentManager mFragmentManager;


    public RealVenueAdapter(List<VenueBooked> list, Activity activity, FragmentManager mFragmentManager) {
        this.list = list;
        this.activity = activity;
        this.mFragmentManager = mFragmentManager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public VenueBooked getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OrderServiceImp orderService = new OrderServiceImp();
        View myView;
        if(view==null)
        {
            myView=View.inflate(viewGroup.getContext(),R.layout.venue_detail,null);
        }
        else{
            myView=view;
        }
        VenueBooked venueBooked= getItem(i);

        ((TextView)myView.findViewById(R.id.tvVenueNameKhang)).setText(venueBooked.getVenueName());
        ((TextView)myView.findViewById(R.id.tvPriceKhang)).setText(venueBooked.getPrice().toString());
        ((TextView)myView.findViewById(R.id.tvCapacityKhang)).setText("From "+venueBooked.getMinPeople()+" to "+venueBooked.getMaxPeople());
        ((TextView)myView.findViewById(R.id.tvtimeKhang)).setText(venueBooked.getBookedTime());
        ((TextView)myView.findViewById(R.id.venueIdKhang)).setText(venueBooked.getVenueId());

    int newPosition=i;

        myView.findViewById(R.id.btnCreateKhang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(view.getRootView().getContext());


                dialog.setTitle("Are you sure to Book this Venue");
                dialog.setMessage("Name : "+ getItem(newPosition).getVenueName());
                dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String venueId= (getItem(newPosition).getVenueId());
                        String date= getItem(newPosition).getBookedDay();
                        String time=getItem(newPosition).getBookedTime();
                        OrderDTO newOrder=orderService.getOrder(venueId,date,time);
// Begin a FragmentTransaction to replace the current Fragment with a new one
                        HomeService homeService = ApiClient.createService(HomeService.class);
                        homeService.createOrder(newOrder).enqueue(new Callback<OrderDTO>() {
                            @Override
                            public void onResponse(Call<OrderDTO> call, Response<OrderDTO> response) {

                                if(response!=null&& response.isSuccessful()){

                                    // Create a new Bundle to send date
                                   OrderDTO createdOrderd= response.body();
                                    Bundle bundle = new Bundle();
                                    String orderId=String.valueOf(createdOrderd.getId());
                                    bundle.putString("orderId", orderId);
                                    //tinh table

                                    //
                                    BookingDetailFragment bookingDetailFragment = new BookingDetailFragment();
                                    // Set the arguments for the new fragment
                                    bookingDetailFragment.setArguments(bundle);


                                mFragmentManager.popBackStack(ProfileFragment.class.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout_navigate, bookingDetailFragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();


                                  }else{
                                    Toast.makeText(view.getRootView().getContext(), "Something Wrong!book 30 days in advance", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            }

                            @Override
                            public void onFailure(Call<OrderDTO> call, Throwable t) {

                            }
                        });
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getRootView().getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();

            }
        });



        return myView;
    }


    //itemclick listener


}
