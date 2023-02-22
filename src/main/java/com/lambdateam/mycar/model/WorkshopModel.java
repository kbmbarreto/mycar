package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Workshop")
public class WorkshopModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "workshop", length = 45, columnDefinition = "VARCHAR(45)", nullable = false)
    private String workshop;

    @Column(name = "contact", length = 255, columnDefinition = "VARCHAR(255)")
    private String contact;

}