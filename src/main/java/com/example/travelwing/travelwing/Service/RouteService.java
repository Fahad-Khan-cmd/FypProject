package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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



    public List<Route> getAvailableRoutes(String departureLocation, String destinationLocation, LocalDate date) {
        return routeRepository.findByDepartureLocationAndDestinationLocation(departureLocation, destinationLocation);
    }



//    public List<TransportationMode> getAllModeByDepartAndArrivalTime(String departLocation, String destinationLocation, LocalDate arrivalTime, LocalDate departTime)
//    {
//       return   routeRepository.findAvailableTransportationModes(departLocation,destinationLocation,arrivalTime,departTime);
//
//    }
}
