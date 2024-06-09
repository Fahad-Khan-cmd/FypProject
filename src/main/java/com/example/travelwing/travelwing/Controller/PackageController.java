package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.DTO.PackageDto;
import com.example.travelwing.travelwing.Domain.Packages;
import com.example.travelwing.travelwing.Service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.23.42.172:3000")
@RestController
@RequestMapping("/api/packages")
public class PackageController {


    @Autowired
    private PackageService packageService;

    @CrossOrigin(origins = "http://10.23.42.172:3000")
    @GetMapping
    public List<Packages> getAllPackages() {
        return packageService.getAllPackages();
    }

    @CrossOrigin(origins = "  http://10.23.42.172:3000")
    @GetMapping("/{id}")
    public ResponseEntity<Packages> getPackageById(@PathVariable Long id) {
        Packages travelPackage = packageService.getPackageById(id);
        if (travelPackage == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(travelPackage);
    }

    @CrossOrigin(origins = "http://10.23.42.172:3000")
    @PostMapping
    public Packages createPackage(@RequestBody PackageDto travelPackage) {
        return packageService.createPackage(travelPackage);
    }

    @CrossOrigin(origins = "  http://10.23.42.172:3000")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }
}
