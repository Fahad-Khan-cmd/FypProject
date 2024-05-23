package com.example.travelwing.travelwing.Repository;

import com.example.travelwing.travelwing.Domain.Route;
import com.example.travelwing.travelwing.Domain.TransportationMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface RouteRepository extends JpaRepository<Route,Long> {
    public  List<Route> findByDepartureLocationAndDestinationLocation(String departureLocation,String destinationLocation);


//    @Query("SELECT r FROM Route r WHERE r.departureLocation = :departureLocation AND r.destinationLocation = :destinationLocation")
//    List<Route> findByDepartureLocationAndDestinationLocation(@Param("departureLocation") String departureLocation, @Param("destinationLocation") String destinationLocation);

    @Query("SELECT m FROM Route r JOIN r.transportationMode m " +
            "WHERE r.departureLocation = :departureLocation " +
            "AND r.destinationLocation = :destinationLocation " +
            "AND r.departTime <= :departTime " +
            "AND r.arrivalTime >= :arrivalTime")
    List<TransportationMode> findAvailableTransportationModes(
            @Param("departureLocation") String departureLocation,
            @Param("destinationLocation") String destinationLocation,
            @Param("departTime") LocalTime departTime,
            @Param("arrivalTime") LocalTime arrivalTime
    );

    List<Route> findByDepartureLocation(String departureLocation);



//    public Route findByDepartureLocation(String departureLocation);
}
