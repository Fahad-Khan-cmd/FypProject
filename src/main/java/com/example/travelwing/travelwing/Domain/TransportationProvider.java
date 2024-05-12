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
public class TransportationProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;
    private String contactInfo;
    private String address;

    @OneToMany(mappedBy = "transportationProvider")
    private List<TransportationMode> transportationModeList;




}
