package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Maintenances")
public class MaintenancesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "km", columnDefinition = "DECIMAL(7,3)", nullable = false)
    private double km;

    @Column(name = "maintenance_date", nullable = false)
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

    @Column(name = "maintenance_type")
    @Enumerated(value = EnumType.STRING)
    private MaintenanceTypeModel maintenanceType;
}