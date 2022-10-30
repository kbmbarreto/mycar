package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Component")
public class ComponentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "component", length = 75, columnDefinition = "VARCHAR(75)", nullable = false)
    private String component;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComponentModel)) return false;
        ComponentModel that = (ComponentModel) o;
        return getId() == that.getId() && getComponent().equals(that.getComponent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getComponent());
    }
}
