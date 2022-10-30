package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.WorkshopModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkshopRepository extends JpaRepository<WorkshopModel, Long> {

}
