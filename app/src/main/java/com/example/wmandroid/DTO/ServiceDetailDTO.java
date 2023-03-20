package com.example.wmandroid.DTO;

import lombok.Data;


@Data
public class ServiceDetailDTO {
    private int id;

    private Integer orderId;

    private Integer serviceId;
    private ServiceDTO servicesByServiceId;
}
