package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ShoppingListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListModel, Long> {

//    @Query(value = "SELECT s.id, v.description, c.component, s.notes, s.fullfiled " +
//            "FROM ShoppingListModel s " +
//            "INNER JOIN s.vehicle v " +
//            "INNER JOIN s.component c " +
//            "order by s.id desc")
    @Query(value = "SELECT s FROM ShoppingListModel s order by s.id desc")
    List<ShoppingListModel> getShoppingListWithDetails();

    @Query(value = "SELECT s FROM ShoppingListModel s WHERE upper(trim(s.component)) like %?1%")
    List<ShoppingListModel> dynamicSearchByComponent(String component);
}
