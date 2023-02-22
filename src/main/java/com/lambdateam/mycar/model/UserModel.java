package com.lambdateam.mycar.model;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class UserModel {

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
}