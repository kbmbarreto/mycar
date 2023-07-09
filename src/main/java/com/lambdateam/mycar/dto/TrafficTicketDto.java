package com.lambdateam.mycar.dto;

import com.lambdateam.mycar.model.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class TrafficTicketDto {

    private long id;
    @NotNull(message = "Description is required")
    private String description;
    @NotNull(message = "Date is required")
    private Date date;
    private String notes;
    @NotNull(message = "Vehicle is required")
    private VehicleModel vehicle;
}
