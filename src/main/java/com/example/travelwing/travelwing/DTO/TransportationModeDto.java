package com.example.travelwing.travelwing.DTO;


import lombok.Data;

@Data
public class TransportationModeDto {

    private String modeName;
    private String description;
    private Double cost;
    private Long providerId;
}
