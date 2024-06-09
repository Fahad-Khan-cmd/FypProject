package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class RouteService {


    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> getAllRoutes()
    {
        return routeRepository.findAll();
    }

    public Boolean createRoute(Route route)
    {
        try {
            routeRepository.save(route);
            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;

    }


    }



//    public List<Route> getAvailableRoutes(String departureLocation, String destinationLocation, LocalDate date) {
//        return routeRepository.findByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation);
//    }

    public List<Route> getAvailableRoutes(String departureLocation, String destinationLocation, LocalDate startDate, LocalDate endDate) {
        validateDates(startDate, endDate);

        List<Route> routes = routeRepository.findByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation);

        // Assuming routes operate daily, filter based on booking date range
        return routes.stream()
                .filter(route -> isRouteAvailable(route, startDate, endDate))
                .collect(Collectors.toList());
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Booking start date cannot be in the past.");
        }

        if (endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Booking end date must be after the start date.");
        }
    }

    private boolean isRouteAvailable(Route route, LocalDate startDate, LocalDate endDate) {
        // You can add more complex logic here if routes are available on specific days
        return !startDate.isAfter(endDate);
    }



//    public List<TransportationMode> getAllModeByDepartAndArrivalTime(String departLocation, String destinationLocation, LocalDate arrivalTime, LocalDate departTime)
//    {
//       return   routeRepository.findAvailableTransportationModes(departLocation,destinationLocation,arrivalTime,departTime);
//
//    }
}
