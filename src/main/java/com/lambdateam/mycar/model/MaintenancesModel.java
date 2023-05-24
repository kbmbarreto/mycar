package com.lambdateam.mycar.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "Maintenances")
@AllArgsConstructor
@NoArgsConstructor
public class MaintenancesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "km", columnDefinition = "DECIMAL(7,3)", nullable = false)
    @NotNull(message = "KM is required")
    private double km;

    @Column(name = "maintenance_date", nullable = false)
    @NotNull(message = "Maintenance date is required")
    private Date maintenanceDate;

    @Column(name = "next_km", columnDefinition = "DECIMAL(7,2)")
    private double nextKm;

    @Column(name = "amount", columnDefinition = "DECIMAL(7,2)")
    private double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_manufacturer")
    private ManufacturerModel manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private VehicleModel vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_component")
    private ComponentModel component;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_maintenance_type")
    private MaintenanceTypeModel maintenanceType;
}