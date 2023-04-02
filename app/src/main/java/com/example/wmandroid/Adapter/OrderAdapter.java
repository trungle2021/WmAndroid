package com.example.wmandroid.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.Fragment.BookingDetailFragment;
import com.example.wmandroid.Fragment.OrderCalendarFragment;
import com.example.wmandroid.Fragment.ProfileFragment;
import com.example.wmandroid.Fragment.ServiceDetailFragment;
import com.example.wmandroid.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    List<OrderDTO> orderDTOList;
    private Context mContext;

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<OrderDTO> orderDTOList) {
        this.orderDTOList = orderDTOList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        OrderAdapter.OrderViewHolder viewHolder=new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        OrderDTO orderDTO = orderDTOList.get(position);
        if (orderDTO == null) {
            return;
        }

        String dateBooking = orderDTO.getTimeHappen(); // Example datetime string
        String dateHappen = orderDTO.getTimeHappen(); // Example datetime string
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            Date _dateBooking = inputFormat.parse(dateBooking);
            Date _dateHappen= inputFormat.parse(dateHappen);
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String output_dateBooking = outputFormat.format(_dateBooking);
            String output_dateHappen = outputFormat.format(_dateHappen);

            holder.bookingDate.setText("Booking Date: " + output_dateBooking);
            holder.eventDate.setText("Event Date: " + output_dateHappen);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.orderId.setText("Order ID: " + orderDTO.getId());
        holder.orderStatus.setText("Order Status: " +orderDTO.getOrderStatus());
        holder.btnDetail.setText("Detail");
        holder.btnDetail.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("orderId", String.valueOf(orderDTO.getId()));

            Fragment fragment = new BookingDetailFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_navigate, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });


    }

    @Override
    public int getItemCount() {
        if(orderDTOList != null){
            return orderDTOList.size();
        }
        return 0;
    }


    public  class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView bookingDate;
        TextView eventDate;
        TextView orderStatus;
        Button btnDetail;
        public OrderViewHolder(@NonNull View itemView)  {
            super(itemView);
            orderId=itemView.findViewById(R.id.orderId);
            bookingDate=itemView.findViewById(R.id.bookingDate);
            eventDate=itemView.findViewById(R.id.eventDate);
            orderStatus=itemView.findViewById(R.id.orderStatus);
            btnDetail=itemView.findViewById(R.id.btnDetail);
        }


    }



}