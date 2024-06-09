package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.DTO.TransportationModeDto;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import com.example.travelwing.travelwing.Domain.TransportationProvider;
import com.example.travelwing.travelwing.Repository.RouteRepository;
import com.example.travelwing.travelwing.Repository.TransportationProviderRepository;
import com.example.travelwing.travelwing.Service.TransportationModeService;
import com.example.travelwing.travelwing.Service.TransportationProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@RestController
@RequestMapping("/modes")
@CrossOrigin(origins = "http://10.23.42.172:3000")
public class TransportationModeController {


    private final TransportationModeService transportationModeService;

    private final TransportationProviderService transportationProviderService;

    private final TransportationProviderRepository transportationProviderRepository;

    private static final Logger LOGGER = Logger.getLogger(TransportationModeController.class.getName());

    private final RouteRepository routeRepository;
    public TransportationModeController(TransportationModeService transportationModeService, TransportationProviderService transportationProviderService, TransportationProviderRepository transportationProviderRepository, RouteRepository routeRepository) {
        this.transportationModeService = transportationModeService;
        this.transportationProviderService = transportationProviderService;
        this.transportationProviderRepository = transportationProviderRepository;
        this.routeRepository = routeRepository;
    }


    @GetMapping("/available")
    @CrossOrigin(origins = "http://10.23.42.172:3000")
    public List<TransportationMode> getAvailableTransportationModes(
            @RequestParam("departureLocation") String departureLocation,
            @RequestParam("destinationLocation") String destinationLocation,
            @RequestParam("departTime") String departTime,
            @RequestParam("arrivalTime") String arrivalTime) {

        LOGGER.info("Fetching available transportation modes for: " +
                "departureLocation=" + departureLocation +
                ", destinationLocation=" + destinationLocation +
                ", departTime=" + departTime +
                ", arrivalTime=" + arrivalTime);

        LocalTime departure = parseTime(departTime);
        LocalTime arrival = parseTime(arrivalTime);

        List<TransportationMode> modes = routeRepository.findAvailableTransportationModes(
                departureLocation, destinationLocation, departure, arrival);

        LOGGER.info("Found " + modes.size() + " transportation modes");

        return modes;
    }

    private LocalTime parseTime(String time) {
        DateTimeFormatter formatterWithFraction = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        DateTimeFormatter formatterWithoutFraction = DateTimeFormatter.ofPattern("HH:mm:ss");
        try {
            return LocalTime.parse(time, formatterWithFraction);
        } catch (DateTimeParseException e) {
            try {
                return LocalTime.parse(time, formatterWithoutFraction);
            } catch (DateTimeParseException ex) {
                throw new RuntimeException("Failed to parse time: " + time, ex);
            }
        }
    }

    @GetMapping
    @CrossOrigin(origins = "http://10.23.42.172:3000")
    public List<TransportationMode> findAllModes()
    {
        return transportationModeService.getAllTransportationMode();
    }

    @PostMapping("/createModes")
    @CrossOrigin(origins = "  http://10.23.42.172:3000")
    public ResponseEntity<TransportationMode> insertModes(@RequestBody TransportationModeDto transportationModeDto)
    {

        Long providerId = transportationModeDto.getProviderId();
        if(providerId == null)
        {
            throw new IllegalArgumentException("Transportation Id Cannot be Null");
        }
        Optional<TransportationProvider> providerOptional =  transportationProviderRepository.findById(providerId);


        if(providerOptional.isPresent())
        {
            TransportationProvider provider = providerOptional.get();


            TransportationMode mode = new TransportationMode();
            mode.setModeName(transportationModeDto.getModeName());
            mode.setDescription(transportationModeDto.getDescription());
            mode.setCost(transportationModeDto.getCost());
            mode.setTransportationProvider(provider);

            return ResponseEntity.ok(transportationModeService.insertMode(mode));

        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/modesByRoutesAndDates")
    @CrossOrigin(origins = "http://10.23.42.172:3000")
    public ResponseEntity<List<TransportationMode>> getTransportationModes(
            @RequestParam String departureLocation,
            @RequestParam String destinationLocation,
            @RequestParam LocalDate bookingDate) {
        List<TransportationMode> transportationModes = transportationModeService.findByRouteAndDate(departureLocation,destinationLocation, bookingDate);
        return ResponseEntity.ok(transportationModes);
    }

}
