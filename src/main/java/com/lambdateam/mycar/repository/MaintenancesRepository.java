package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.MaintenancesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenancesRepository extends JpaRepository<MaintenancesModel, Long> {

//    @Query(value = "SELECT c.id, c.maintenanceDate, c.km, c.amount," +
//            " m.manufacturer, co.component, mt.maintenanceType, c.nextKm, v.description " +
//            "FROM MaintenancesModel c " +
//            "INNER JOIN c.manufacturer m " +
//            "INNER JOIN c.vehicle v " +
//            "INNER JOIN c.component co " +
//            "INNER JOIN c.maintenanceType mt " +
//            "order by c.id desc")
    @Query(value = "SELECT c FROM MaintenancesModel c order by c.id desc")
    List<MaintenancesModel> getMaintenancesWithDetails();

    @Query(value = "SELECT c FROM MaintenancesModel c WHERE upper(trim(c.nextKm)) between ?1 and ?2 order by c.id desc")
    List<MaintenancesModel> dynamicSearchByNextKm(String nextKm1, String nextKm2);

    @Query(value = "SELECT c FROM MaintenancesModel c WHERE upper(trim(c.component)) like %?1%")
    List<MaintenancesModel> dynamicSearchByComponent(String component);
}