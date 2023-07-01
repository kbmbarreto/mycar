package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {

    @Query(value = "SELECT v FROM VehicleModel v WHERE upper(trim(v.description)) like %?1%")
    List<VehicleModel> dynamicSearchByDescription(String description);
}
