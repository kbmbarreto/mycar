package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.repository.MaintenancesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MaintenancesService {

    private final MaintenancesRepository repository;

    private MaintenancesModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The maintenance id " + id +" was not found.")
                );
    }

    public Iterable<MaintenancesModel> findAllMaintenances() {
        return repository.findAll();
    }

    public MaintenancesModel findMaintenanceById(Long id) {
        return findOrThrow(id);
    }

    public void deleteMaintenanceById(Long id) {
        repository.deleteById(id);
    }

    public MaintenancesModel createMaintenance(MaintenancesModel maintenance) {
        return repository.save(maintenance);
    }

    public void updateMaintenance(Long id, MaintenancesModel maintenance) {
        findOrThrow(id);
        repository.save(maintenance);
    }
}