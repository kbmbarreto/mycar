package com.lambdateam.mycar.dto;

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
public class MaintenancesDto {

    private long id;
    @NotNull(message = "KM is required")
    private double km;
    @NotNull(message = "Maintenance date is required")
    private Date maintenanceDate;
    private double nextKm;
    private double amount;
    private ManufacturerModel manufacturer;
    private VehicleModel vehicle;
    private ComponentModel component;
    private MaintenanceTypeModel maintenanceType;
}
