package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ComponentService {

    private final ComponentRepository repository;

    private ComponentModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The component id " + id +" was not found.")
                );
    }

    public Iterable<ComponentModel> findAllComponents() {
        return repository.findAll();
    }

    public ComponentModel findComponentById(Long id) {
        return findOrThrow(id);
    }

    public void deleteComponentById(Long id) {
        repository.deleteById(id);
    }

    public ComponentModel createComponent(ComponentModel component) {
        return repository.save(component);
    }

    public void updateComponent(Long id, ComponentModel component) {
        findOrThrow(id);
        repository.save(component);
    }
}