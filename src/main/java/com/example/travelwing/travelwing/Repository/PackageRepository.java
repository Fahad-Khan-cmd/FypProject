package com.example.travelwing.travelwing.Repository;


import com.example.travelwing.travelwing.Domain.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PackageRepository extends JpaRepository<Packages, Long > {


}
