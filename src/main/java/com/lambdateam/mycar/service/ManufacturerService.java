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

    private ManufacturerModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The manufacturer id " + id +" was not found.")
                );
    }

    public Iterable<ManufacturerModel> findAllManufacturers() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public Iterable<ManufacturerModel> dynamicSearchByManufacturer(String manufacturer) throws NotFoundException {
        try{
            return repository.dynamicSearchByManufacturer(manufacturer);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public ManufacturerModel findManufacturerById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
        return findOrThrow(id);
    }

    public void deleteManufacturerById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
        repository.deleteById(id);
    }

    public ManufacturerModel createManufacturer(ManufacturerModel manufacturer) {
        return repository.save(manufacturer);
    }

    public void updateManufacturer(Long id, ManufacturerModel manufacturer) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
        findOrThrow(id);
        repository.save(manufacturer);
    }
}