package com.example.travelwing.travelwing.Domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "transport_mode_id")
    private TransportationMode transportationMode;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Packages travelPackage;


    private LocalDate bookingDate;
    private LocalDate bookingEndDate;
    private String bookingStatus;

    private Integer numTravelers;
    private Double totalCost;



}
