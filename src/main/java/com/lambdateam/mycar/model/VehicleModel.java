package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Vehicle")
@AllArgsConstructor
@NoArgsConstructor
public class VehicleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "description", length = 45, columnDefinition = "VARCHAR(45)", nullable = false)
    @NotNull(message = "Description is required")
    public String description;
}