package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.repository.MaintenancesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MaintenancesService {

    private final MaintenancesRepository repository;

    private MaintenancesModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The maintenance id " + id +" was not found.")
                );
    }

    public List<MaintenancesModel> findAllMaintenancesWithDetails() throws NotFoundException {
        try {
            return repository.getMaintenancesWithDetails();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public Iterable<MaintenancesModel> findAllMaintenances() throws NotFoundException{
        try {
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public List<MaintenancesModel> dynamicSearchByNextKm(String nextKm1, String nextKm2) throws NotFoundException {
        try {
            return repository.dynamicSearchByNextKm(nextKm1, nextKm2);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public List<MaintenancesModel> dynamicSearchByComponent(String component) throws NotFoundException {
        try {
            return repository.dynamicSearchByComponent(component);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public MaintenancesModel findMaintenanceById(Long id) throws NotFoundException {
        try {
            return findOrThrow(id);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteMaintenanceById(Long id) throws NotFoundException {
            if(!repository.existsById(id)) throw new NotFoundException("The maintenance id " + id +" was not found.");
            repository.deleteById(id);

    }

    public MaintenancesModel createMaintenance(MaintenancesModel maintenance) {
        return repository.save(maintenance);
    }

    public void updateMaintenance(Long id, MaintenancesModel maintenance) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The maintenance id " + id +" was not found.");
        findOrThrow(id);
        repository.save(maintenance);
    }
}