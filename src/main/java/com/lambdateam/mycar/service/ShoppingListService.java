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

    private ShoppingListModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The shopping list item " + id +" was not found.")
                );
    }

    public List<ShoppingListModel> findAllShoppingListWithDetails() throws NotFoundException {
        try{
            return repository.getShoppingListWithDetails();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public Iterable<ShoppingListModel> findAllShoppingListItems() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public ShoppingListModel findShoppingListItemById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
        return findOrThrow(id);
    }

    public List<ShoppingListModel> dynamicSearchByComponent(String component) throws NotFoundException {
        try{
            return repository.dynamicSearchByComponent(component);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteShoppingListItemById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
        repository.deleteById(id);
    }

    public ShoppingListModel createShoppingListItem(ShoppingListModel shoppingListItem) {
        return repository.save(shoppingListItem);
    }

    public void updateShoppingListItem(Long id, ShoppingListModel shoppingListItem) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
        findOrThrow(id);
        repository.save(shoppingListItem);
    }
}