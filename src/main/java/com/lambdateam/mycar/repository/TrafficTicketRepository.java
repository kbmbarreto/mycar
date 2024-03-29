package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.TrafficTicketModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrafficTicketRepository extends JpaRepository<TrafficTicketModel, Long> {

    @Query(value = "SELECT t FROM TrafficTicketModel t order by t.id desc")
    List<TrafficTicketModel> getTrafficTicketWithDetails();

    @Query(value = "SELECT t FROM TrafficTicketModel t WHERE upper(trim(t.description)) like %?1%")
    List<TrafficTicketModel> dynamicSearchByDescription(String description);
}