package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.dto.MaintenancesDto;
import com.lambdateam.mycar.model.MaintenancesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenancesRepository extends JpaRepository<MaintenancesModel, Long> {

    @Query(value = "SELECT c.id, c.maintenanceDate, c.km, c.amount," +
            " m.manufacturer, co.component, mt.maintenanceType, c.nextKm, v.description " +
            "FROM MaintenancesModel c " +
            "INNER JOIN c.manufacturer m " +
            "INNER JOIN c.vehicle v " +
            "INNER JOIN c.component co " +
            "INNER JOIN c.maintenanceType mt " +
            "order by c.id desc")
    List<Object[]> getMaintenancesWithDetails();
}