package com.example.wmandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.R;
import com.example.wmandroid.databinding.MainImageViewBinding;

import java.util.List;

public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {
    List<VenueImgDTO> venueImgDTOS;
    Context context;
    MainImageViewBinding mainImageViewBinding;
    public VenueAdapter(Context context) {
        this.context = context;
    }

    public void setVenueData(List<VenueImgDTO> venueImgDTOS) {
        this.venueImgDTOS = venueImgDTOS;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mainImageViewBinding=MainImageViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(mainImageViewBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull VenueAdapter.ViewHolder holder, int position) {
        VenueImgDTO venueImgDTO=venueImgDTOS.get(position);
        Glide.with(context).load("data:image/png;base64," + venueImgDTO.getUrl()).into(mainImageViewBinding.mainImageView);
    }

    @Override
    public int getItemCount() {
        return venueImgDTOS.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
