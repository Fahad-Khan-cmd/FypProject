package com.example.travelwing.travelwing.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destinationName;

    private String description;

    private Double averageCost;


    @OneToMany(mappedBy = "destination")
    private List<Recommendations> recommendationsList;

}
