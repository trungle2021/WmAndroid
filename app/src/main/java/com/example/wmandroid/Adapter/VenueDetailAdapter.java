package com.example.wmandroid.Adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.R;

import java.util.Iterator;
import java.util.List;

public class VenueDetailAdapter extends RecyclerView.Adapter<VenueDetailAdapter.ViewHolder> {
    List<VenueDTO> venueDTOList;
    Context context;

    public VenueDetailAdapter(Context context) {
        this.context = context;
    }
    public void setDataVenueDetail(List<VenueDTO> venueDTOList){
        this.venueDTOList=venueDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.venue_detail, parent, false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VenueDetailAdapter.ViewHolder holder, int position) {
        VenueDTO venueDTO=venueDTOList.get(position);
        holder.name.setText("Name: "+venueDTO.getVenueName());
        holder.min.setText("Min: "+String.valueOf(venueDTO.getMinPeople()));
        holder.max.setText("Max: "+String.valueOf(venueDTO.getMaxPeople()));
        holder.price.setText("Price: "+String.valueOf(venueDTO.getPrice()));
        String firstPic="";
        Iterator<VenueImgDTO> iterator=venueDTO.getVenueImagesById().iterator();
        if(iterator.hasNext()){
            firstPic=iterator.next().getUrl();
        }
        Glide.with(context).load("data:image/png;base64," + firstPic).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return venueDTOList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView min;
        private TextView max;
        private TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.venueDetailImg);
            name=itemView.findViewById(R.id.venueName);
            min=itemView.findViewById(R.id.minPPL);
            max=itemView.findViewById(R.id.maxPPL);
            price=itemView.findViewById(R.id.price);
        }
    }
}
