package com.example.wmandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.databinding.MainImageViewBinding;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder>{
    List<FoodImageDTO> foodImageDTOS;
    Context context;
    MainImageViewBinding mainImageViewBinding;
    public MenuAdapter(Context context) {
        this.context = context;
    }

    public void setMenuData(List<FoodImageDTO> foodImageDTOS) {
        this.foodImageDTOS = foodImageDTOS;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mainImageViewBinding=MainImageViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MenuAdapter.ViewHolder(mainImageViewBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        FoodImageDTO foodImageDTO=foodImageDTOS.get(position);
        Glide.with(context).load("data:image/png;base64," + foodImageDTO.getUrl()).into(mainImageViewBinding.mainImageView);
    }

    @Override
    public int getItemCount() {
        return foodImageDTOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
