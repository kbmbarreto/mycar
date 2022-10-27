package com.lambdateam.mycar.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "User")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "UserName", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String UserName;

    @Column(name = "Email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    private String Email;

    @Column(name = "Password", length = 255, columnDefinition = "VARCHAR(255)", nullable = false)
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
}