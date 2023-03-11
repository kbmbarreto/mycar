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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService service;
    private final ModelMapper mapper;
    private UserDto convertToDto(UserModel model) {
        return mapper.map(model, UserDto.class);
    }
    private UserModel convertToModel(UserDto dto) {
        return mapper.map(dto, UserModel.class);
    }

    @GetMapping
    public List<UserDto> getUsers(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                pageable.getPageNumber();

        var usersList = StreamSupport
                .stream(service.findAllUsers().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return usersList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable("id") Long id) {
        return convertToDto(service.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@Valid @RequestBody UserDto userDto) {
        var model = convertToModel(userDto);
        var user = service.createUser(model);

        return new ResponseEntity(convertToDto(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putUser(@PathVariable("id") Long id, @Valid @RequestBody UserDto userDto) {
        if(!id.equals(userDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var userModel = convertToModel(userDto);
        service.updateUser(id, userModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteUserById(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        return HttpStatus.NO_CONTENT;
    }

//    @PatchMapping(value = "/{id}", produces = "application/json")
//    public UserModel incrementalUpdateUser(@PathVariable("id") Long id, @RequestBody UserModel userModel) {
//
//        UserModel foundUser = getUserById(id).getBody();
//        assert foundUser != null;
//        foundUser.setEmail(Optional.ofNullable(userModel.getEmail()).orElse(foundUser.getEmail()));
//        foundUser.setUserName(Optional.ofNullable(userModel.getUserName()).orElse(foundUser.getUserName()));
//        foundUser.setPassword(Optional.ofNullable(userModel.getPassword()).orElse(foundUser.getPassword()));
//
//        return userRepository.save(foundUser);
//    }
}