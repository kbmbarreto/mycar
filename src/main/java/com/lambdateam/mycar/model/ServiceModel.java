package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
@Entity
@Table(name = "Service")
@AllArgsConstructor
@NoArgsConstructor
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "scheduling", nullable = false)
    @NotNull(message = "Scheduling is required")
    private Date scheduling;

    @Column(name = "description", length = 128, columnDefinition = "VARCHAR(128)", nullable = false)
    @NotNull(message = "Description is required")
    private String description;

    @Column(name = "order_service", length = 128, columnDefinition = "VARCHAR(128)", nullable = true)
    private String orderService;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private VehicleModel vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_workshop")
    private WorkshopModel workshop;
}