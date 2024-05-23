package com.example.travelwing.travelwing.Service;

import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.BookingRepository;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import com.example.travelwing.travelwing.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleEngine {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final RouteRepository routeRepository;

    public List<String> getRecommendations(User user) {
        List<Booking> userBookings = bookingRepository.findByUser(Optional.ofNullable(user));
        Set<String> recommendations = new HashSet<>();

        // Rule 1: Recommend destinations popular among all users
        recommendations.addAll(getPopularDestinations());

        // Rule 2: Recommend destinations similar to user's previous bookings
        recommendations.addAll(getSimilarDestinations(userBookings));

        // Rule 3: Recommend services for upcoming trips
        recommendations.addAll(getUpcomingTripServices(userBookings));

        return new ArrayList<>(recommendations);
    }

    private List<String> getPopularDestinations() {
        return bookingRepository.findAll().stream()
                .filter(booking -> booking.getRoute() != null)  // Add null check
                .collect(Collectors.groupingBy(booking -> booking.getRoute().getDestinationLocation(), Collectors.counting()))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<String> getSimilarDestinations(List<Booking> userBookings) {
        return userBookings.stream()
                .filter(booking -> booking.getRoute() != null)  // Add null check
                .flatMap(booking -> routeRepository.findByDepartureLocation(booking.getRoute().getDestinationLocation()).stream())
                .map(Route::getDestinationLocation)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<String> getUpcomingTripServices(List<Booking> userBookings) {
        return userBookings.stream()
                .filter(booking -> booking.getBookingDate().isAfter(LocalDate.now()))
                .map(booking -> "Services for " + (booking.getRoute() != null ? booking.getRoute().getDestinationLocation() : "unknown destination"))
                .collect(Collectors.toList());
    }

}
