package com.example.wmandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.R;

import java.util.List;

public class FoodSninperAdapter extends BaseAdapter {
    Context context;
    int myLayout;
    List<FoodDTO> list;

    public FoodSninperAdapter(Context context, int myLayout, List<FoodDTO> list) {
        this.context = context;
        this.myLayout = myLayout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public FoodDTO getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView=inflater.inflate(myLayout,null);
        TextView tvName= convertView.findViewById(R.id.tvFoodNameKhang);
        TextView tvFoodPrice=convertView.findViewById(R.id.tvFoodPriceKhang);
        TextView foodIdKhang=convertView.findViewById(R.id.foodIdKhang);

        //set data.
        tvName.setText(list.get(position).getFoodName());
        tvFoodPrice.setText("$"+list.get(position).getPrice().toString());
        foodIdKhang.setText(String.valueOf(list.get(position).getId()));

        return convertView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Định nghĩa cách hiển thị danh sách các item cho phép người dùng chọn
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_detail_snipper, parent, false);
        }

        TextView tvName= convertView.findViewById(R.id.tvFoodNameKhang);
        TextView tvFoodPrice=convertView.findViewById(R.id.tvFoodPriceKhang);
        TextView foodIdKhang=convertView.findViewById(R.id.foodIdKhang);
        tvName.setText(list.get(position).getFoodName());
        tvFoodPrice.setText("$"+list.get(position).getPrice().toString());
        foodIdKhang.setText(String.valueOf(list.get(position).getId()));


        return convertView;
    }



}
