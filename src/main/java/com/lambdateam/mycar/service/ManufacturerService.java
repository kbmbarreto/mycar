package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ManufacturerService {

    private final ManufacturerRepository repository;

    private ManufacturerModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The manufacturer id " + id +" was not found.")
                );
    }

    public Iterable<ManufacturerModel> findAllManufacturers() {
        return repository.findAll();
    }

    public ManufacturerModel findManufacturerById(Long id) {
        return findOrThrow(id);
    }

    public void deleteManufacturerById(Long id) {
        repository.deleteById(id);
    }

    public ManufacturerModel createManufacturer(ManufacturerModel manufacturer) {
        return repository.save(manufacturer);
    }

    public void updateManufacturer(Long id, ManufacturerModel manufacturer) {
        findOrThrow(id);
        repository.save(manufacturer);
    }
}