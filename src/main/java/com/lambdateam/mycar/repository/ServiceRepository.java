package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {

//    @Query(value = "SELECT s.id, v.description, s.scheduling, w.workshop, s.description " +
//            "FROM ServiceModel s " +
//            "INNER JOIN s.workshop w " +
//            "INNER JOIN s.vehicle v " +
//            "order by s.id desc")
    @Query(value = "SELECT s FROM ServiceModel s order by s.id desc")
    List<ServiceModel> getServicesWithDetails();

    @Query(value = "SELECT s FROM ServiceModel s WHERE upper(trim(s.description)) like %?1%")
    List<ServiceModel> dynamicSearchByDescription(String description);
}