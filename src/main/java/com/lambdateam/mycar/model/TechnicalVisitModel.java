package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "TechnicalVisit")
@AllArgsConstructor
@NoArgsConstructor
public class TechnicalVisitModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "maintenance_date", nullable = false)
    @NotNull(message = "Maintenance is required")
    private Date maintenanceDate;
    @Column(name = "description", nullable = false)
    @NotNull(message = "Description is required")
    private String description;
    @Column(name = "locale_doc", nullable = true)
    private String localeDoc;
    @Column(name = "conclude", nullable = false)
    @NotNull(message = "Conclude is required")
    private boolean conclude;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private VehicleModel vehicle;
}