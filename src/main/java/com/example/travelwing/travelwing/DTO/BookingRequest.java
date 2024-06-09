package com.example.travelwing.travelwing.DTO;

import lombok.Data;

import java.time.LocalDate;


@Data
public class BookingRequest {

    private Long packageId;
    private LocalDate bookingDate;
}
