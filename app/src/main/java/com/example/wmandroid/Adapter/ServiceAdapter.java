package com.example.wmandroid.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.ServiceDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.R;

import java.util.List;

public class ServiceAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    List<ServiceDTO> list;


    public ServiceAdapter(Context context, int myLayout, List<ServiceDTO> list) {
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ServiceDTO getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView=inflater.inflate(myLayout,null);

         //khai báo

        TextView tvServiceName= convertView.findViewById(R.id.tvServiceNameKhang);
        TextView tvServicePrice=convertView.findViewById(R.id.tvServicePriceKhang);
//        TextView foodIdKhang=convertView.findViewById(R.id.foodIdKhang);


        //set data.
        tvServiceName.setText(list.get(position).getServiceName());
        tvServicePrice.setText("$"+list.get(position).getPrice().toString());
//        foodIdKhang.setText(String.valueOf(list.get(position).getId()));

        return convertView;
    }

}
