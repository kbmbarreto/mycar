package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserModel> init(@PathVariable (value = "id") Long id) {

        Optional<UserModel> userModel = userRepository.findById(id);

        return new ResponseEntity(userModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<UserModel>> user() {

        List<UserModel> list = (List<UserModel>) userRepository.findAll();

        return new ResponseEntity<List<UserModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<UserModel> newUser(@RequestBody UserModel userModel) {

        UserModel userSaved = userRepository.save(userModel);

        return new ResponseEntity<UserModel>(userSaved, HttpStatus.CREATED);
    }
}