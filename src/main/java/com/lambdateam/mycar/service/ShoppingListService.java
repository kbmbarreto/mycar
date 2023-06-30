package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.ExpiredJwtException;
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

    private ShoppingListModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The shopping list item " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<ShoppingListModel> findAllShoppingListWithDetails() throws ExpiredJwtException {
        try{
            return repository.getShoppingListWithDetails();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<ShoppingListModel> findAllShoppingListItems() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ShoppingListModel findShoppingListItemById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteShoppingListItemById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ShoppingListModel createShoppingListItem(ShoppingListModel shoppingListItem) throws ExpiredJwtException {
        try{
            return repository.save(shoppingListItem);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateShoppingListItem(Long id, ShoppingListModel shoppingListItem) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The shopping list item " + id +" was not found.");
            findOrThrow(id);
            repository.save(shoppingListItem);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}