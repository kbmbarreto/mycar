package com.lambdateam.mycar.dto;

import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.model.VehicleModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ShoppingListDto {

    private long id;
    private String notes;
    @NotNull(message = "Fullfiled is required")
    private Boolean fullfiled;
    private VehicleModel vehicle;
    private ComponentModel component;
}