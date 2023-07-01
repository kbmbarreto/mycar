package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.repository.MaintenancesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.lambdateam.mycar.exception.ExpiredJwtException;
import java.util.List;

@AllArgsConstructor
@Service
public class MaintenancesService {

    private final MaintenancesRepository repository;

    private MaintenancesModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The maintenance id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<MaintenancesModel> findAllMaintenancesWithDetails() throws ExpiredJwtException {
        try {
            return repository.getMaintenancesWithDetails();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<MaintenancesModel> findAllMaintenances() throws ExpiredJwtException{
        try {
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<MaintenancesModel> dynamicSearchByNextKm(String nextKm1, String nextKm2) throws ExpiredJwtException {
        try {
            return repository.dynamicSearchByNextKm(nextKm1, nextKm2);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<MaintenancesModel> dynamicSearchByComponent(String component) throws ExpiredJwtException {
        try {
            return repository.dynamicSearchByComponent(component);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public MaintenancesModel findMaintenanceById(Long id) throws ExpiredJwtException {
        try {
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteMaintenanceById(Long id) throws ExpiredJwtException {
        try {
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public MaintenancesModel createMaintenance(MaintenancesModel maintenance) throws ExpiredJwtException {
        try {
            return repository.save(maintenance);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateMaintenance(Long id, MaintenancesModel maintenance) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance id " + id +" was not found.");
            findOrThrow(id);
            repository.save(maintenance);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}