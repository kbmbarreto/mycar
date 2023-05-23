package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.TechnicalVisitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalVisitRepository extends JpaRepository<TechnicalVisitModel, Long> {

}
