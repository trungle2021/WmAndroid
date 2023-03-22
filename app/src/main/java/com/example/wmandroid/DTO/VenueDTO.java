package com.example.wmandroid.DTO;

import lombok.Data;

@Data

public class VenueDTO {

    private int id;
    private String venueName;
    private Integer minPeople;
    private Integer maxPeople;
    private Double price;
    private boolean active;
}
