package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ComponentDto {

    private long id;
    @NotNull(message = "Component is required")
    private String component;
}