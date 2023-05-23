package com.lambdateam.mycar.dto;

import com.lambdateam.mycar.model.VehicleModel;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
public class TechnicalVisitDto {

    private Long id;
    @NotNull(message = "Maintenance is required")
    private Date maintenanceDate;
    @NotNull(message = "Description is required")
    private String description;
    private String localeDoc;
    @NotNull(message = "Conclude is required")
    private boolean conclude;
    private VehicleModel vehicle;
}
