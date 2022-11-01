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
    private VehicleModel idVehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_workshop")
    private WorkshopModel idWorkshop;

    public ServiceModel() {

    }

    public ServiceModel(long id, Date scheduling, String description, String orderService, VehicleModel idVehicle, WorkshopModel idWorkshop) {
        this.id = id;
        this.scheduling = scheduling;
        this.description = description;
        this.orderService = orderService;
        this.idVehicle = idVehicle;
        this.idWorkshop = idWorkshop;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getScheduling() {
        return scheduling;
    }

    public void setScheduling(Date scheduling) {
        this.scheduling = scheduling;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrderService() {
        return orderService;
    }

    public void setOrderService(String orderService) {
        this.orderService = orderService;
    }

    public VehicleModel getIdVehicle() {
        return idVehicle;
    }

    public void setIdVehicle(VehicleModel idVehicle) {
        this.idVehicle = idVehicle;
    }

    public WorkshopModel getIdWorkshop() {
        return idWorkshop;
    }

    public void setIdWorkshop(WorkshopModel idWorkshop) {
        this.idWorkshop = idWorkshop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceModel)) return false;
        ServiceModel that = (ServiceModel) o;
        return getId() == that.getId() && getScheduling().equals(that.getScheduling()) && getDescription().equals(that.getDescription()) && Objects.equals(getOrderService(), that.getOrderService()) && Objects.equals(getIdVehicle(), that.getIdVehicle()) && Objects.equals(getIdWorkshop(), that.getIdWorkshop());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScheduling(), getDescription(), getOrderService(), getIdVehicle(), getIdWorkshop());
    }
}
