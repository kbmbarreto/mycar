package com.lambdateam.mycar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.model.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaintenancesDto {

    private long id;
    @NotNull(message = "KM is required")
    private int km;
    @NotNull(message = "Maintenance date is required")
    private Date maintenanceDate;
    private int nextKm;
    private double amount;
    @NotNull(message = "Description is required")
    private ManufacturerModel manufacturer;
    @NotNull(message = "Description is required")
    private VehicleModel vehicle;
    @NotNull(message = "Description is required")
    private ComponentModel component;
    @NotNull(message = "Description is required")
    private MaintenanceTypeModel maintenanceType;
}