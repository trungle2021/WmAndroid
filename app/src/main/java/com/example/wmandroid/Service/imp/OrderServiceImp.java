package com.example.wmandroid.Service.imp;

import static com.example.wmandroid.Utils.orderStatus.orderStatusCancel;
import static com.example.wmandroid.Utils.orderStatus.orderStatusCanceled;
import static com.example.wmandroid.Utils.orderStatus.orderStatusCompleted;
import static com.example.wmandroid.Utils.orderStatus.orderStatusConfirm;
import static com.example.wmandroid.Utils.orderStatus.orderStatusDeposited;
import static com.example.wmandroid.Utils.orderStatus.orderStatusOrdered;
import static com.example.wmandroid.Utils.orderStatus.orderStatusRefund;
import static com.example.wmandroid.Utils.orderStatus.orderStatusWarning;

import com.example.wmandroid.DTO.OrderDTO;
import com.example.wmandroid.DTO.VenueBooked;
import com.example.wmandroid.DTO.VenueDTO;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

                    newbooked.setBookedTime("Evening");
                    bookeds.add(newbooked);
                }
            }
        }
        List<VenueBooked> responseList= getVenueNoBooked(venueList,bookeds);

        return responseList;
    }

public List<VenueBooked>getVenueNoBooked(List<VenueDTO> venues,List<VenueBooked> venueBookeds)
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
        venue1.setBookedTime("Afternoon");
        newList.add(venue1);
        VenueBooked venue2=new VenueBooked();
        venue2.setVenueId(String.valueOf(venue.getId()));
        venue2.setVenueName(venue.getVenueName());
        venue2.setMinPeople(venue.getMinPeople());
        venue2.setMaxPeople(venue.getMaxPeople());
        venue2.setPrice(venue.getPrice());
        venue2.setActive(venue.isActive());
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
    return availableList;
}

}
