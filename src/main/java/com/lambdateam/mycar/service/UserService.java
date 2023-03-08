package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    private UserModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The user id " + id +" was not found.")
                );
    }

    public Iterable<UserModel> findAllUsers() {
        return repository.findAll();
    }

    public UserModel findUserById(Long id) {
        return findOrThrow(id);
    }

    public void deleteUserById(Long id) {
        repository.deleteById(id);
    }

    public UserModel createUser(UserModel user) {
        return repository.save(user);
    }

    public void updateUser(Long id, UserModel user) {
        findOrThrow(id);
        repository.save(user);
    }
}
