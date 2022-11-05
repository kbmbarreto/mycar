package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.MaintenancesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenancesRepository extends JpaRepository<MaintenancesModel, Long> {

}
