package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Manufacturer")
public class ManufacturerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "manufacturer", length = 45, columnDefinition = "VARCHAR(45)", nullable = false)
    private String manufacturer;

    public ManufacturerModel() {

    }

    public ManufacturerModel(long id, String manufacturer) {
        this.id = id;
        this.manufacturer = manufacturer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManufacturerModel)) return false;
        ManufacturerModel that = (ManufacturerModel) o;
        return getId() == that.getId() && getManufacturer().equals(that.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getManufacturer());
    }
}