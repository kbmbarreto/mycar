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

    @Column(name = "user", length = 60, columnDefinition = "VARCHAR(60)", nullable = false)
    @NotNull(message = "Name is required")
    private String user;

    @Column(name = "email", length = 60, columnDefinition = "VARCHAR(60)", nullable = false, unique = true)
    @NotNull(message = "E-mail is required")
    private String email;

    private String mobileNumber;
    private byte[] storedHash;
    private byte[] storedSalt;

    public UserModel(String username, String email, String mobileNumber) {
        this.user = username;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }
}