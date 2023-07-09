package com.lambdateam.mycar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.model.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MaintenancesDto {

    private long id;
    @NotNull(message = "KM is required")
    private double km;
    @NotNull(message = "Maintenance date is required")
    private Date maintenanceDate;
    private double nextKm;
    private double amount;
    @NotBlank(message = "Manufacturer is required")
    private ManufacturerModel manufacturer;
    @NotBlank(message = "Vehicle is required")
    private VehicleModel vehicle;
    @NotBlank(message = "Component is required")
    private ComponentModel component;
    @NotBlank(message = "Maintenance type is required")
    private MaintenanceTypeModel maintenanceType;
}
