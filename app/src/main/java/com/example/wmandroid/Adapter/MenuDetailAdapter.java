package com.example.wmandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wmandroid.DTO.FoodDTO;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.R;

import java.util.Iterator;
import java.util.List;

public class MenuDetailAdapter extends RecyclerView.Adapter<MenuDetailAdapter.ViewHolder> {
    List<FoodDTO> foodDTOList;
    Context context;

    public MenuDetailAdapter(Context context) {
        this.context = context;
    }

    public void setDataMenuDetail(List<FoodDTO> foodDTOList) {
        this.foodDTOList = foodDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.menu_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuDetailAdapter.ViewHolder holder, int position) {
        FoodDTO foodDTO = foodDTOList.get(position);
        holder.name.setText("Name: "+foodDTO.getFoodName());
        holder.type.setText("Type: "+foodDTO.getFoodType());
        holder.price.setText("Price: "+String.valueOf(foodDTO.getPrice()));
        String firstPic="";
        Iterator<FoodImageDTO> iterator=foodDTO.getFoodImagesById().iterator();
        if(iterator.hasNext()){
            firstPic=iterator.next().getUrl();
        }
        Glide.with(context).load("data:image/png;base64," + firstPic).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return foodDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView type;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.menuDetailImg);
            name = itemView.findViewById(R.id.menuName);
            type = itemView.findViewById(R.id.foodType);
            price = itemView.findViewById(R.id.foodPrice);
        }
    }
}
