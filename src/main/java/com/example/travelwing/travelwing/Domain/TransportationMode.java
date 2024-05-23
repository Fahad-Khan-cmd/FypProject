package com.example.travelwing.travelwing.Domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class TransportationMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modeName;
    private String description;

    @Builder.Default
    private Double cost = 0.0 ;


    @ManyToOne
    @JoinColumn(name = "provider_id")
    private TransportationProvider transportationProvider;


    @JsonIgnore
    @OneToMany(mappedBy = "transportationMode")
    private List<Route> routeList;

    @JsonBackReference
    @OneToMany(mappedBy = "transportationMode")
    private List<Booking> bookingList;



}
