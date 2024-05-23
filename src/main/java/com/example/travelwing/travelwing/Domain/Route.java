package com.example.travelwing.travelwing.Domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonBackReference
    @OneToMany(mappedBy = "route")
    private List<Booking> bookings;


    @ManyToOne
    @JoinColumn(name = "mode_id")
    private TransportationMode transportationMode;

    private String departureLocation;
    private String destinationLocation;


    private LocalTime departTime;
    private LocalTime arrivalTime;

    private Double ticketPrice;

    private Long totalDuration;

}
