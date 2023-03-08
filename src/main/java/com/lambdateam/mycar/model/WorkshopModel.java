package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Workshop")
@AllArgsConstructor
@NoArgsConstructor
public class WorkshopModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "workshop", length = 45, columnDefinition = "VARCHAR(45)", nullable = false)
    @NotNull(message = "Workshop is required")
    private String workshop;

    @Column(name = "contact", length = 255, columnDefinition = "VARCHAR(255)")
    private String contact;
}