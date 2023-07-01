package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.lambdateam.mycar.exception.ExpiredJwtException;;import java.util.List;

@AllArgsConstructor
@Service
public class ComponentService {

    private final ComponentRepository repository;

    private ComponentModel findOrThrow(final Long id) throws ExpiredJwtException {
        try {
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The component id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<ComponentModel> findAllComponents() throws ExpiredJwtException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ComponentModel findComponentById(Long id) throws ExpiredJwtException {
        if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
        return findOrThrow(id);
    }

    public List<ComponentModel> dynamicSearchByComponent(String component) throws ExpiredJwtException {
        try {
            return repository.dynamicSearchByComponent(component);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteComponentById(Long id) {
        if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
        repository.deleteById(id);
    }

    public ComponentModel createComponent(ComponentModel component) throws ExpiredJwtException {
        try{
            return repository.save(component);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateComponent(Long id, ComponentModel component) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
            findOrThrow(id);
            repository.save(component);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}