package com.example.wmandroid.DTO;


import lombok.Data;

@Data
public class ServiceDTO {
    private int id;

    private String serviceName;

    private Double price;

    private String description;
    private boolean active;

//    private Set<ServiceDetailDTO> serviceDetailsById;
}
