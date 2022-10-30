package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentModel, Long> {

}
