package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.WorkshopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkshopRepository extends JpaRepository<WorkshopModel, Long> {

    @Query(value = "SELECT w FROM WorkshopModel w WHERE upper(trim(w.workshop)) like %?1%")
    List<WorkshopModel> dynamicSearchByWorkshop(String workshop);
}
