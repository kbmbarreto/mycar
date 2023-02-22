package com.lambdateam.mycar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "User")
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    @NotNull(message = "Name is required")
    private String username;

    @Column(name = "email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    @NotNull(message = "E-mail is required")
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    @NotNull(message = "Password is required")
    private String password;
}