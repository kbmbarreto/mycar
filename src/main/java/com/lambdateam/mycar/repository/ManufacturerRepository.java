package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ManufacturerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<ManufacturerModel, Long> {

    @Query(value = "SELECT c FROM ManufacturerModel c WHERE upper(trim(c.manufacturer)) like %?1%")
    List<ManufacturerModel> dynamicSearchByManufacturer(String manufacturer);
}
