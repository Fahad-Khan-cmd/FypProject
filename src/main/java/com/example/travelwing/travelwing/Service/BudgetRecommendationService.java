package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Domain.User;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import com.example.travelwing.travelwing.Repository.TransportationModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BudgetRecommendationService {
    private final RouteRepository routeRepository;
    private final TransportationModeRepository transportationModeRepository;




    public List<String> getBudgetRecommendations(Double budget) {
        List<String> recommendations = new ArrayList<>();

        // Rule: Recommend routes within budget
        List<Route> affordableRoutes = routeRepository.findAll().stream()
                .filter(route -> route.getTicketPrice() <= budget)
                .collect(Collectors.toList());

        recommendations.addAll(affordableRoutes.stream()
                .map(route -> "Destination: " + route.getDestinationLocation() + ", Price: $" + route.getTicketPrice())
                .collect(Collectors.toList()));

        // Rule: Recommend transportation modes within budget
        List<TransportationMode> affordableModes = transportationModeRepository.findAll().stream()
                .filter(mode -> mode.getCost() != null && mode.getCost() <= budget)
                .collect(Collectors.toList());

        recommendations.addAll(affordableModes.stream()
                .map(mode -> "Mode: " + mode.getModeName() + ", Price: $" + mode.getCost())
                .collect(Collectors.toList()));

        return recommendations;
    }

}
