package com.example.wmandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wmandroid.DTO.FoodImageDTO;
import com.example.wmandroid.DTO.VenueImgDTO;
import com.example.wmandroid.R;
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
        View v=LayoutInflater.from(context).inflate(R.layout.main_image_view,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        FoodImageDTO foodImageDTO=foodImageDTOS.get(position);
        Glide.with(context).load("data:image/png;base64," + foodImageDTO.getUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return foodImageDTOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.mainImageView);
        }
    }
}
