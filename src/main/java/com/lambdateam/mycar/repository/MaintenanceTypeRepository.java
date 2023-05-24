package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.MaintenanceTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTypeRepository extends JpaRepository<MaintenanceTypeModel, Long> {

}
