package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("select u from UserModel u where u.username = ?1")
    UserModel findUserByLogin(String userName);
}