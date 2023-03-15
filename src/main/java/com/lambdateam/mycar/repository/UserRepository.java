package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    @Query("" + "select case when count(u) > 0 then true else false end from UserModel u where u.email = ?1")
    Boolean selectExistsEmail(String email);
    UserModel findByEmail(String email);
}