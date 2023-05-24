package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "TraficTicket")
@AllArgsConstructor
@NoArgsConstructor
public class TrafficTicketModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "description", length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
    @NotNull(message = "Description is required")
    private String description;

    @Column(name = "date", nullable = false)
    @NotNull(message = "Date is required")
    private Date date;

    @Column(name = "notes", length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private VehicleModel vehicle;
}