package com.example.travelwing.travelwing.Repository;

import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {

    List<Booking> findByUser(Optional<User> user);

}
