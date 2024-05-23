package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Repository.TransportationModeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransportationModeService {

    private final TransportationModeRepository transportationModeRepository;

    public TransportationModeService(TransportationModeRepository transportationModeRepository) {
        this.transportationModeRepository = transportationModeRepository;
    }


    public List<TransportationMode> getAllTransportationMode()
    {
           return transportationModeRepository.findAll();
    }
    public TransportationMode insertMode(TransportationMode transportationMode)
    {
        return transportationModeRepository.save(transportationMode);
    }


    public List<TransportationMode> findByRouteAndDate(String departureLocation, String destinationLocation, LocalDate bookingDate) {
          return transportationModeRepository.findByRouteAndDate(departureLocation, destinationLocation, bookingDate);
    }

}
