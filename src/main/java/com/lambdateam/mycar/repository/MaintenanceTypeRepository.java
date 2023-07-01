package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.MaintenanceTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceTypeRepository extends JpaRepository<MaintenanceTypeModel, Long> {

    @Query(value = "SELECT c FROM MaintenanceTypeModel c WHERE upper(trim(c.maintenanceType)) like %?1%")
    List<MaintenanceTypeModel> dynamicSearchByMaintenanceType(String maintenanceType);
}
