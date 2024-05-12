package com.example.travelwing.travelwing.Domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TransportationMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modeName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private TransportationProvider transportationProvider;

    @OneToMany(mappedBy = "transportationMode")
    private List<Route> routeList;


}
