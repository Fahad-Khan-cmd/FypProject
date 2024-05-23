package com.example.travelwing.travelwing.Controller;

import com.example.travelwing.travelwing.Domain.TransportationProvider;
import com.example.travelwing.travelwing.Service.TransportationProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/Providers")
public class TransportationProviderController {

    private final TransportationProviderService transportationProviderService;


    public TransportationProviderController(TransportationProviderService transportationProviderService) {
        this.transportationProviderService = transportationProviderService;
    }

    @GetMapping
    public ResponseEntity<List<TransportationProvider>> getAllProviders()
    {
        return ResponseEntity.ok(transportationProviderService.getAllTransportationProviders());
    }

    @PostMapping("/createProviders")
    public ResponseEntity<String> createProvider(@RequestBody TransportationProvider transportationProvider)
    {
       Boolean providers = transportationProviderService.insertProviders(transportationProvider);
       if(providers)
       {
           return ResponseEntity.ok("Transportation Provider Insert SuccessFully");
       }else {
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Create ");
       }

    }

    @GetMapping("/Providers/{id}")
    public ResponseEntity<Optional<TransportationProvider>> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(transportationProviderService.getById(id));
    }



}
