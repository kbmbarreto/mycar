package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ManufacturerDto {

    private long id;
    @NotNull(message = "Manufacturer is required")
    private String manufacturer;
}
