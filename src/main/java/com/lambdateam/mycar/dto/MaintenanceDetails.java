package com.lambdateam.mycar.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceDetails {
    private Long id;
    private Date maintenanceDate;
    private BigDecimal km;
    private BigDecimal amount;
    private String manufacturer;
    private String component;
    private String maintenanceType;
    private BigDecimal nextKm;
    private String description;

    // Construtores, getters e setters
}
