package com.example.travelwing.travelwing.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

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
