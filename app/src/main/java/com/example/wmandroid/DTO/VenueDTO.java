package com.example.wmandroid.DTO;

import java.util.Set;

import lombok.Data;

@Data
public class VenueDTO {

    private int id;
    private String venueName;
    private Integer minPeople;
    private Integer maxPeople;
    private Double price;
    private boolean active;
    private Set<VenueImgDTO>venueImagesById;
}
