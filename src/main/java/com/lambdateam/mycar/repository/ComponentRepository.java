package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ComponentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComponentRepository extends JpaRepository<ComponentModel, Long> {

    @Query(value = "SELECT c FROM ComponentModel c WHERE upper(trim(c.component)) like %?1%")
    List<ComponentModel> dynamicSearchByComponent(String component);
}