package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "MaintenanceType")
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceTypeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "maintenance_type", length = 75, columnDefinition = "VARCHAR(75)", nullable = false)
    @NotNull(message = "Maintenance type is required")
    private String maintenanceType;
}