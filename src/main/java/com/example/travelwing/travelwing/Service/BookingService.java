package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.DTO.BookingDto;
import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.BookingRepository;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import com.example.travelwing.travelwing.Repository.TransportationModeRepository;
import com.example.travelwing.travelwing.Repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RouteRepository routeRepository;
    private final TransportationModeRepository transportationModeRepository;
    private final JavaMailSender javaMailSender;

    public Booking createBooking(BookingDto bookingDto) throws MessagingException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        List<Route> route = routeRepository.findByDepartureLocationAndDestinationLocation(
                bookingDto.getDepartureLocation(), bookingDto.getDestinationLocation());

        if (route.isEmpty()) {
            throw new RuntimeException("Route doesn't exist");
        }

        Route routes = route.get(0);

        if (bookingDto.getTransportModeId() == null) {
            throw new IllegalArgumentException("Transport Mode ID must not be null");
        }

        Optional<TransportationMode> optionalTransportationMode = transportationModeRepository.findById(bookingDto.getTransportModeId());
        if (!optionalTransportationMode.isPresent()) {
            throw new RuntimeException("Transportation mode not found");
        }

        TransportationMode transportationMode = optionalTransportationMode.get();

        Double totalCost = calculateTotalCost(bookingDto, transportationMode, routes);

        Booking booking = Booking.builder()
                .user(user)
                .route(routes)
                .transportationMode(transportationMode)
                .bookingDate(bookingDto.getBookingStartDate()) // Use bookingStartDate
                .bookingStatus("CONFIRMED")
                .numTravelers(bookingDto.getNumTravelers())
                .totalCost(totalCost) // Set the calculated total cost
                .build();

        if ("round-trip".equals(bookingDto.getTripType())) {
            booking.setBookingEndDate(bookingDto.getBookingEndDate()); // Set end date for round trips
        }

        bookingRepository.save(booking);
        sendConfirmationEmail(user.getEmail(), booking);
        return booking;
    }

    private Double calculateTotalCost(BookingDto bookingDto, TransportationMode transportationMode, Route route) {
        // Example calculation: ticket price per traveler + additional cost based on transportation mode
        double ticketPrice = route.getTicketPrice();
        int numTravelers = bookingDto.getNumTravelers();
        double baseCost = ticketPrice * numTravelers;

        // Add additional cost based on transportation mode (e.g., fixed cost or percentage)
        double additionalCost = 0.0;
        switch (transportationMode.getModeName().toLowerCase()) {
            case "train":
                additionalCost = 50.0; // Example fixed cost for train
                break;
            case "bus":
                additionalCost = 30.0; // Example fixed cost for bus
                break;
            case "plane":
                additionalCost = 100.0; // Example fixed cost for plane
                break;
            default:
                additionalCost = 20.0; // Default cost for other modes
        }

        return baseCost + additionalCost;
    }

    private void sendConfirmationEmail(String to, Booking booking) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(to);
        helper.setSubject("Booking Confirmation");
        helper.setText("Your booking has been confirmed. Booking details: " + booking.toString(), true);

        javaMailSender.send(mimeMessage);
    }

    public Optional<Booking> getBookingById(Long id)
    {
        return bookingRepository.findById(id);
    }


    public List<Booking> findByUser(Optional<User> user)
    {
            return bookingRepository.findByUser(user);
    }

}
