package com.lambdateam.mycar.service;

import com.lambdateam.mycar.dto.UserDto;
import com.lambdateam.mycar.exception.BadRequestException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.UserModel;
import com.lambdateam.mycar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;

    private UserDto convertToDto(UserModel model) {
        return mapper.map(model, UserDto.class);
    }
    private UserModel convertToEntity(UserDto dto) {
        return mapper.map(dto, UserModel.class);
    }

    public List<UserDto> findAllUsers() {

        var userEntityList =
                new ArrayList<>(repository.findAll());
        return userEntityList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDto findUserById(final Long id) {

        var user = repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id " + id + " was not found")
                );

        return convertToDto(user);
    }

    private byte[] createSalt() {

        var random = new SecureRandom();
        var salt = new byte[128];
        random.nextBytes(salt);

        return salt;
    }

    private byte[] createPasswordHash(String password, byte[] salt) throws NoSuchAlgorithmException {

        var md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        return md.digest(password.getBytes(StandardCharsets.UTF_8));
    }

    public UserDto createUser(UserDto userDto, String password) throws NoSuchAlgorithmException {

        var user = convertToEntity(userDto);
        if (password.isBlank()) throw new IllegalArgumentException(
                "Password is required."
        );

        var existsEmail = repository.selectExistsEmail(user.getEmail());
        if (existsEmail) throw new BadRequestException(
                "Email " + user.getEmail() + " taken"
        );

        byte[] salt = createSalt();
        byte[] hashedPassword = createPasswordHash(password, salt);
        user.setStoredSalt(salt);
        user.setStoredHash(hashedPassword);
        repository.save(user);

        return convertToDto(user);
    }

    public void updateUser(Long id, UserDto userDto, String password) throws NoSuchAlgorithmException {

        var user = findOrThrow(id);
        var userParam = convertToEntity(userDto);
        user.setUsername(userParam.getUsername());
        user.setEmail(userParam.getEmail());
        user.setMobileNumber(userParam.getMobileNumber());
        if (!password.isBlank()) {
            byte[] salt = createSalt();
            byte[] hashedPassword = createPasswordHash(password, salt);
            user.setStoredSalt(salt);
            user.setStoredHash(hashedPassword);
        }

        repository.save(user);
    }

    public void removeUserById(Long id) {
        findOrThrow(id);
        repository.deleteById(id);
    }

    private UserModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("User by id " + id +
                                " was not found")
                );
    }
}