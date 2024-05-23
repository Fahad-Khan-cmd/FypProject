package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import com.example.travelwing.travelwing.Service.RouteService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteRepository routeRepository;


    public RouteController(RouteService routeService, RouteRepository routeRepository) {
        this.routeService = routeService;
        this.routeRepository = routeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Route>> getAllRoute() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @PostMapping("/createRoutes")
    public ResponseEntity<String> insertRoutes(@RequestBody Route route) {
        try {
            Boolean routes = routeService.createRoute(route);
            if (routes) {
                return ResponseEntity.ok("Route is Created Successfully");
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @CrossOrigin
    @GetMapping("/departures")
    public List<String> getAllDeparturesLocations()
    {
        return routeRepository.findAll().stream()
                .map(Route::getDepartureLocation).distinct()
                .collect(Collectors.toList());
    }


    @CrossOrigin
    @GetMapping("/destinations")
    public List<String> getAllDestinationsLocations()
    {
        return routeRepository.findAll().stream()
                .map(Route::getDestinationLocation).distinct()
                .collect(Collectors.toList());
    }



    @CrossOrigin
    @GetMapping("/available")
    public ResponseEntity<List<Route>> getAvailableRoutes(
            @RequestParam String departureLocation,
            @RequestParam String destinationLocation,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Route> routes = routeService.getAvailableRoutes(departureLocation, destinationLocation, date);
        return ResponseEntity.ok(routes);
    }


}
