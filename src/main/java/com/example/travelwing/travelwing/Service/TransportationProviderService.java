package com.example.travelwing.travelwing.Service;

import com.example.travelwing.travelwing.Domain.TransportationProvider;
import com.example.travelwing.travelwing.Repository.TransportationProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransportationProviderService {

    private final TransportationProviderRepository transportationProviderRepository;

    public TransportationProviderService(TransportationProviderRepository transportationProviderRepository) {
        this.transportationProviderRepository = transportationProviderRepository;
    }

    public List<TransportationProvider> getAllTransportationProviders()
    {
       return   transportationProviderRepository.findAll();
    }

    public Boolean insertProviders(TransportationProvider transportationProvider)
    {
        transportationProviderRepository.save(transportationProvider);
        return true;
    }

    public Optional<TransportationProvider> getById(Long id)
    {
        return transportationProviderRepository.findById(id);
    }
}
