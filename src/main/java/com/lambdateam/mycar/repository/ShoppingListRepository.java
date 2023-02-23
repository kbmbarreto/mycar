package com.lambdateam.mycar.repository;

import com.lambdateam.mycar.model.ShoppingListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingListModel, Long> {

}
