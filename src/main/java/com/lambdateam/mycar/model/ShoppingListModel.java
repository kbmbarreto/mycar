package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ShoppingList")
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "notes", length = 255, columnDefinition = "VARCHAR(255)")
    private String notes;

    @Column(name = "fullfiled", columnDefinition = "BOOLEAN", nullable = false)
    @NotNull(message = "Fullfiled is required")
    private Boolean fullfiled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehicle")
    private VehicleModel vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_component")
    private ComponentModel component;
}