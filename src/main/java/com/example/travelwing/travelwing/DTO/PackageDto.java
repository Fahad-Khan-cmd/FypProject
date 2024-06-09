package com.example.travelwing.travelwing.DTO;


import jdk.jshell.execution.LocalExecutionControlProvider;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PackageDto {
    private String name;
    private String description;
    private Double price;
    private Integer duration; // Duration in days
    private String includedServices;
    private String imageUrl; // URL of the package ima
    private LocalDate startDate;
    private LocalDate endDate;
}
