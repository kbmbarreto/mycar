package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "Service")
public class ServiceModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "scheduling", nullable = false)
    private Date scheduling;

    @Column(name = "description", length = 128, columnDefinition = "VARCHAR(128)", nullable = false)
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