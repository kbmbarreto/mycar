package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ShoppingListModel;
import com.lambdateam.mycar.repository.ShoppingListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ShoppingListService {

    private final ShoppingListRepository repository;

    private ShoppingListModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The shopping list item " + id +" was not found.")
                );
    }

    public List<ShoppingListModel> findAllShoppingListWithDetails() {
        return repository.getShoppingListWithDetails();
    }

    public Iterable<ShoppingListModel> findAllShoppingListItems() {
        return repository.findAll();
    }

    public ShoppingListModel findShoppingListItemById(Long id) {
        return findOrThrow(id);
    }

    public void deleteShoppingListItemById(Long id) {
        repository.deleteById(id);
    }

    public ShoppingListModel createShoppingListItem(ShoppingListModel shoppingListItem) {
        return repository.save(shoppingListItem);
    }

    public void updateShoppingListItem(Long id, ShoppingListModel shoppingListItem) {
        findOrThrow(id);
        repository.save(shoppingListItem);
    }
}
