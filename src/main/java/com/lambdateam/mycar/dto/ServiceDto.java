package com.lambdateam.mycar.dto;

import com.lambdateam.mycar.model.VehicleModel;
import com.lambdateam.mycar.model.WorkshopModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class ServiceDto {

    private long id;
    @NotNull(message = "Scheduling is required")
    private Date scheduling;
    @NotNull(message = "Description is required")
    private String description;
    private String orderService;
    private VehicleModel vehicle;
    private WorkshopModel workshop;
}
