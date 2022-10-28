package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User")
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String username;

    @Column(name = "email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        username = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String emailString) {
        email = emailString;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordString) {
        password = passwordString;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(getClass() != obj.getClass())
            return false;
        UserModel other = (UserModel) obj;
        if(id == null) {
            if(other.id != null)
                return false;
        } else if(!id.equals(other.id))
            return false;
        return true;
    }
}