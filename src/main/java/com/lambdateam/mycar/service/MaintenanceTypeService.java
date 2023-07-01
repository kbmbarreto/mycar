package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.repository.MaintenanceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.lambdateam.mycar.exception.ExpiredJwtException;

import java.util.List;

@AllArgsConstructor
@Service
public class MaintenanceTypeService {

    private final MaintenanceTypeRepository repository;

    private MaintenanceTypeModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The maintenance type id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<MaintenanceTypeModel> findAllMaintenanceTypes() throws ExpiredJwtException{
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public MaintenanceTypeModel findMaintenanceTypeById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<MaintenanceTypeModel> dynamicSearchByMaintenanceType(String maintenanceType) throws ExpiredJwtException {
        try {
            return repository.dynamicSearchByMaintenanceType(maintenanceType);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteMaintenanceTypeById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public MaintenanceTypeModel createMaintenanceType(MaintenanceTypeModel maintenanceType) throws ExpiredJwtException {
        try{
            return repository.save(maintenanceType);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateMaintenanceType(Long id, MaintenanceTypeModel maintenanceType) throws ExpiredJwtException {
        try {
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
            findOrThrow(id);
            repository.save(maintenanceType);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}