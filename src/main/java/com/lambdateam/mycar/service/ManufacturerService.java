package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.repository.ManufacturerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ManufacturerService {

    private final ManufacturerRepository repository;

    private ManufacturerModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The manufacturer id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<ManufacturerModel> findAllManufacturers() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<ManufacturerModel> dynamicSearchByManufacturer(String manufacturer) throws ExpiredJwtException {
        try{
            return repository.dynamicSearchByManufacturer(manufacturer);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ManufacturerModel findManufacturerById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteManufacturerById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ManufacturerModel createManufacturer(ManufacturerModel manufacturer) throws ExpiredJwtException {
        try{
            return repository.save(manufacturer);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateManufacturer(Long id, ManufacturerModel manufacturer) throws ExpiredJwtException {
        try {
            if(!repository.existsById(id)) throw new NotFoundException("The manufacturer id " + id +" was not found.");
            findOrThrow(id);
            repository.save(manufacturer);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}