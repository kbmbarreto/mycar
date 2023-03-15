package com.lambdateam.mycar.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDto {

    private Long id;
    @NotNull(message = "Name is required")
    private String user;
    @NotNull(message = "E-mail is required")
    private String email;
    private String mobileNumber;
    @NotNull(message = "Password is required")
    private String password;
}
