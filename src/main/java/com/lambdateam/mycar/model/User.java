package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "UserName", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String UserName;

    @Column(name = "Email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String Email;

    @Column(name = "Password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String Password;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
        User other = (User) obj;
        if(Id == null) {
            if(other.Id != null)
                return false;
        } else if(!Id.equals(other.Id))
            return false;
        return true;
    }
}