package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class WorkshopDto {

    private long id;
    @NotNull(message = "Workshop is required")
    private String workshop;
    @Column(name = "contact", length = 255, columnDefinition = "VARCHAR(255)")
    private String contact;
}