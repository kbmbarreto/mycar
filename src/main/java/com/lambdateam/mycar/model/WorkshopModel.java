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

    public WorkshopModel() {

    }

    public WorkshopModel(long id, String workshop, String contact) {
        this.id = id;
        this.workshop = workshop;
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkshopModel)) return false;
        WorkshopModel that = (WorkshopModel) o;
        return getId() == that.getId() && getWorkshop().equals(that.getWorkshop()) && Objects.equals(getContact(), that.getContact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getWorkshop(), getContact());
    }
}
