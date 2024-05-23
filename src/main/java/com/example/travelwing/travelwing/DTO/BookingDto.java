package com.example.travelwing.travelwing.DTO;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDto {



    private String tripType;
    private String departureLocation;
    private String destinationLocation;
    private LocalDate bookingStartDate;
    private LocalDate bookingEndDate; // Add this field for round trips
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private Long transportModeId;
    private Integer numTravelers;
    private Double totalCost;

}


