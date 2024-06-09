package com.example.travelwing.travelwing.Domain;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Packages {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Integer duration; // Duration in days
    private String includedServices;
    private String imageUrl; // URL of the package image


    private LocalDate startDate;




    private LocalDate endDate;

    @OneToMany(mappedBy = "travelPackage")
    private List<Booking> bookings;


}
