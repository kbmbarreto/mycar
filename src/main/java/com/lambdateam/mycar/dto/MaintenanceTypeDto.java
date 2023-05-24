package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MaintenanceTypeDto {

    private long id;
    @NotNull(message = "Maintenance type is required")
    private String maintenanceType;
}