package com.example.wmandroid.Service.imp;

import static com.example.wmandroid.Utils.orderStatus.orderStatusCancel;
import static com.example.wmandroid.Utils.orderStatus.orderStatusCanceled;
import static com.example.wmandroid.Utils.orderStatus.orderStatusCompleted;
import static com.example.wmandroid.Utils.orderStatus.orderStatusConfirm;
import static com.example.wmandroid.Utils.orderStatus.orderStatusDeposited;
import static com.example.wmandroid.Utils.orderStatus.orderStatusOrdered;
import static com.example.wmandroid.Utils.orderStatus.orderStatusRefund;
import static com.example.wmandroid.Utils.orderStatus.orderStatusWarning;

import android.util.Log;

import com.example.wmandroid.API.ApiClient;
import com.example.wmandroid.API.HomeService;
import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.DTO.VenueDTO;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderServiceImp {

    public List<VenueBooked> getVenuesBooked(String date,List<VenueDTO> venueList,List<OrderDTO> orderList) {


        List<OrderDTO> bookedList=new ArrayList<>();
        if(orderList!=null){
            for (OrderDTO order: orderList) {
                if (order.getTimeHappen().contains(date) && !order.getOrderStatus().equals(orderStatusCancel)&& !order.getOrderStatus().equals(orderStatusCanceled)&& !order.getOrderStatus().equals(orderStatusRefund)&& !order.getOrderStatus().equals(orderStatusCompleted)) {
                    bookedList.add(order);
                }
            }
        }

        //fiter venue
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<VenueBooked> bookeds=new ArrayList<>();
        if(bookedList!=null){

            for (OrderDTO order:bookedList)
            {
                LocalTime timeHappen = LocalDateTime.parse(order.getTimeHappen(), formatter).toLocalTime();
                LocalTime compareTime1 = LocalTime.parse("13:00:00");
                LocalTime compareTime2 = LocalTime.parse("18:00:00");
                if(timeHappen.isBefore(compareTime1)){
                    VenueBooked newbooked=new VenueBooked();
                    newbooked.setVenueId(String.valueOf(order.getVenueId()));
                    newbooked.setBookedTime("Afternoon");
                    bookeds.add(newbooked);

                }
                else if(timeHappen.isAfter(compareTime1) && timeHappen.isBefore(compareTime2)){
                    VenueBooked newbooked=new VenueBooked();
                    newbooked.setVenueId(String.valueOf(order.getVenueId()));
                    newbooked.setBookedTime("Evening");
                    bookeds.add(newbooked);
                }
            }
        }
        List<VenueBooked> responseList= getVenueNoBooked(venueList,bookeds,date);
        Gson gson = new Gson();
        String responseLists = gson.toJson(bookeds);

        Log.i("booked",responseLists);
        return responseList;
    }

public List<VenueBooked>getVenueNoBooked(List<VenueDTO> venues,List<VenueBooked> venueBookeds,String date)
{
    List<VenueBooked> newList=new ArrayList<>();
    for (VenueDTO venue: venues) {
        VenueBooked venue1=new VenueBooked();
        venue1.setVenueId(String.valueOf(venue.getId()));
        venue1.setVenueName(venue.getVenueName());
        venue1.setMinPeople(venue.getMinPeople());
        venue1.setMaxPeople(venue.getMaxPeople());
        venue1.setPrice(venue.getPrice());
        venue1.setActive(venue.isActive());
        venue1.setBookedDay(date);
        venue1.setBookedTime("Afternoon");
        newList.add(venue1);
        VenueBooked venue2=new VenueBooked();
        venue2.setVenueId(String.valueOf(venue.getId()));
        venue2.setVenueName(venue.getVenueName());
        venue2.setMinPeople(venue.getMinPeople());
        venue2.setMaxPeople(venue.getMaxPeople());
        venue2.setPrice(venue.getPrice());
        venue2.setActive(venue.isActive());
        venue2.setBookedDay(date);
        venue2.setBookedTime("Evening");
        newList.add(venue2);
    }

    List<VenueBooked> availableList=new ArrayList<>();

    for (VenueBooked noBooked:newList)
    {
        boolean isBooked=false;
        for (VenueBooked booked: venueBookeds) {
            if(noBooked.getVenueId().equalsIgnoreCase(booked.getVenueId()) && noBooked.getBookedTime().equalsIgnoreCase(booked.getBookedTime()))
            {
                isBooked=true;
                break;
            }

        }
        if(!isBooked){availableList.add(noBooked);}
    }
    Gson gson = new Gson();
    String availableLists = gson.toJson(availableList);

    Log.i("available",availableLists);
    return availableList;
}


public OrderDTO getOrder(String strVenueId,String date,String time)
{

    DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Integer venueId=Integer.parseInt(strVenueId);
    String dateTime=new String();
    if(time.equalsIgnoreCase("Afternoon"))
    {
        dateTime=date+" 12:00:00";
    }
    else if(time.equalsIgnoreCase("Evening"))
    {
        dateTime=date+" 17:00:00";
    }
    else{ return null;}
    LocalDateTime orderDateTime = LocalDateTime.now();
    String formattedNow = orderDateTime.format(formatter);
    LocalDateTime happenDateTime = LocalDateTime.parse(dateTime, formatter);

//        LocalTime timeHappen = happenDateTime.toLocalTime();
//check status
    Duration duration=Duration.between(orderDateTime,happenDateTime);
    if(duration.toDays() >=30 && happenDateTime.isAfter(orderDateTime)) {
        OrderDTO newOrder = new OrderDTO();
        newOrder.setVenueId(venueId);
        newOrder.setTimeHappen(dateTime);
        //set cung test
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        CustomUserDetails customerDetail =(CustomUserDetails) authentication.getPrincipal();

        newOrder.setCustomerId(1);
        //
        newOrder.setOrderStatus(orderStatusOrdered);
        newOrder.setOrderDate(formattedNow);

        return newOrder;
    }
    else{

        return null;

    }

}


}
