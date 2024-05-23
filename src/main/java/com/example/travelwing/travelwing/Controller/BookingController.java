package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.DTO.BookingDto;
import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Service.BookingService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@CrossOrigin
public class BookingController {

        private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }



    @CrossOrigin
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDto bookingDto, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            Booking booking = bookingService.createBooking(bookingDto);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (MessagingException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Optional<Booking>> getBookingById(@PathVariable Long id) {
        return    ResponseEntity.ok(bookingService.getBookingById(id));
    }


//    @CrossOrigin
//    @GetMapping
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<List<Booking>> getBookingsForUser(@AuthenticationPrincipal UserDetails userDetails) {
//        List<Booking> bookings = bookingService.getBookingsForUser(userDetails);
//        return ResponseEntity.ok(bookings);
//    }



}
