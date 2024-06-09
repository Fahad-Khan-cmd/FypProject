package com.example.travelwing.travelwing.Service;


import com.example.travelwing.travelwing.DTO.PackageDto;
import com.example.travelwing.travelwing.Domain.Packages;
import com.example.travelwing.travelwing.Repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageService {


    @Autowired
    private PackageRepository packageRepository;

    public List<Packages> getAllPackages() {
        return packageRepository.findAll();
    }

    public Packages getPackageById(Long id) {
        return packageRepository.findById(id).orElse(null);
    }

    public Packages createPackage(PackageDto packageDto) {
        Packages travelPackage = Packages.builder()
                .name(packageDto.getName())
                .description(packageDto.getDescription())
                .price(packageDto.getPrice())
                .duration(packageDto.getDuration())
                .includedServices(packageDto.getIncludedServices())
                .imageUrl(packageDto.getImageUrl())
                .startDate(packageDto.getStartDate())
                .endDate(packageDto.getEndDate())
                .build();
        return packageRepository.save(travelPackage);
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}
