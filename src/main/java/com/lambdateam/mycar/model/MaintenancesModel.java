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

    public MaintenancesModel() {

    }

    public MaintenancesModel(long id, double km, Date maintenanceDate, double nextKm, double amount, ManufacturerModel manufacturer, VehicleModel vehicle, MaintenanceTypeModel maintenanceType) {
        this.id = id;
        this.km = km;
        this.maintenanceDate = maintenanceDate;
        this.nextKm = nextKm;
        this.amount = amount;
        this.manufacturer = manufacturer;
        this.vehicle = vehicle;
        this.maintenanceType = maintenanceType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public Date getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(Date maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public double getNextKm() {
        return nextKm;
    }

    public void setNextKm(double nextKm) {
        this.nextKm = nextKm;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ManufacturerModel getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerModel manufacturer) {
        this.manufacturer = manufacturer;
    }

    public VehicleModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleModel vehicle) {
        this.vehicle = vehicle;
    }

    public MaintenanceTypeModel getMaintenanceType() {
        return maintenanceType;
    }

    public void setMaintenanceType(MaintenanceTypeModel maintenanceType) {
        this.maintenanceType = maintenanceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaintenancesModel)) return false;
        MaintenancesModel that = (MaintenancesModel) o;
        return getId() == that.getId() && Double.compare(that.getKm(), getKm()) == 0 && Double.compare(that.getNextKm(), getNextKm()) == 0 && Double.compare(that.getAmount(), getAmount()) == 0 && getMaintenanceDate().equals(that.getMaintenanceDate()) && getManufacturer().equals(that.getManufacturer()) && getVehicle().equals(that.getVehicle()) && getMaintenanceType() == that.getMaintenanceType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKm(), getMaintenanceDate(), getNextKm(), getAmount(), getManufacturer(), getVehicle(), getMaintenanceType());
    }
}
