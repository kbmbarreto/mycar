package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VehicleDto {

    public long id;
    @NotNull(message = "Description is required")
    private String description;
}
