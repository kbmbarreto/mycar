package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ComponentService {

    private final ComponentRepository repository;

    private ComponentModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The component id " + id +" was not found.")
                );
    }

    public Iterable<ComponentModel> findAllComponents() throws NotFoundException {
        try {
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public ComponentModel findComponentById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
        return findOrThrow(id);
    }

    public List<ComponentModel> dynamicSearchByComponent(String component) throws NotFoundException {
        try {
            return repository.dynamicSearchByComponent(component);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteComponentById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
        repository.deleteById(id);
    }

    public ComponentModel createComponent(ComponentModel component) {
        return repository.save(component);
    }

    public void updateComponent(Long id, ComponentModel component) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The component id " + id +" was not found.");
        findOrThrow(id);
        repository.save(component);
    }
}