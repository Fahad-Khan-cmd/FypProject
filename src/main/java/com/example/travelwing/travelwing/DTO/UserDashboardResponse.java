package com.example.travelwing.travelwing.DTO;

import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserDashboardResponse {

    private User user;
    private List<Booking> bookings;
}
