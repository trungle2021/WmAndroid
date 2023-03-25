package com.example.wmandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wmandroid.CreateOrder;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.OrderCalendarActivity;
import com.example.wmandroid.R;

import java.util.List;

public class RealVenueAdapter  extends BaseAdapter {
    List<VenueBooked> list;
    Activity activity;

    public RealVenueAdapter(List<VenueBooked> list, Activity activity) {
        this.list = list;
        this.activity = activity;
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
                Log.i("order",view.getRootView().toString());
                Intent intent = new Intent(view.getRootView().getContext(), CreateOrder.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", String.valueOf(getItem(i).getVenueId()));
                view.getRootView().getContext().startActivity(intent);

            }
        });


        return myView;
    }


    //itemclick listener


}
