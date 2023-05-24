package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.repository.MaintenanceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MaintenanceTypeService {

    private final MaintenanceTypeRepository repository;

    private MaintenanceTypeModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The maintenance type id " + id +" was not found.")
                );
    }

    public Iterable<MaintenanceTypeModel> findAllMaintenanceTypes() {
        return repository.findAll();
    }

    public MaintenanceTypeModel findMaintenanceTypeById(Long id) {
        return findOrThrow(id);
    }

    public void deleteMaintenanceTypeById(Long id) {
        repository.deleteById(id);
    }

    public MaintenanceTypeModel createMaintenanceType(MaintenanceTypeModel maintenanceType) {
        return repository.save(maintenanceType);
    }

    public void updateMaintenanceType(Long id, MaintenanceTypeModel maintenanceType) {
        findOrThrow(id);
        repository.save(maintenanceType);
    }
}