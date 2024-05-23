package com.example.travelwing.travelwing.Repository;

import com.example.travelwing.travelwing.Domain.TransportationMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransportationModeRepository extends JpaRepository<TransportationMode,Long> {
    public TransportationMode findByModeName(String transportationModeName);

    @Query("SELECT tm FROM TransportationMode tm JOIN tm.routeList r WHERE r.departureLocation = :departureLocation AND r.destinationLocation = :destinationLocation AND r.departTime <= :bookingDate AND r.arrivalTime >= :bookingDate")
    List<TransportationMode> findByRouteAndDate(@Param("departureLocation") String departureLocation, @Param("destinationLocation") String destinationLocation, @Param("bookingDate") LocalDate bookingDate);

//    List<TransportationMode> findByRouteAndDate(String departureLocation, String DestinationLocation, LocalDate bookingDate);

}
