package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.UserDto;
import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService service;
    private final ModelMapper mapper;

    @GetMapping
    public Iterable<UserDto> getUsers() {

        return service.findAllUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return service.findUserById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto postUser(@Valid @RequestBody UserDto
                                    userDto)
            throws NoSuchAlgorithmException {
        return service.createUser(userDto,
                userDto.getPassword());
    }

    @PutMapping(value = "/{id}")
    public void putUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto)
            throws NoSuchAlgorithmException {

        service.updateUser(id, userDto, userDto.getPassword());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") Long id) {
        service.removeUserById(id);
    }
}