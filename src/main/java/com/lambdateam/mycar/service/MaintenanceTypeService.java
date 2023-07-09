package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.repository.MaintenanceTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MaintenanceTypeService {

    private final MaintenanceTypeRepository repository;

    private MaintenanceTypeModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The maintenance type id " + id +" was not found.")
                );
    }

    public Iterable<MaintenanceTypeModel> findAllMaintenanceTypes() throws NotFoundException{
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public MaintenanceTypeModel findMaintenanceTypeById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
        return findOrThrow(id);
    }

    public List<MaintenanceTypeModel> dynamicSearchByMaintenanceType(String maintenanceType) throws NotFoundException {
        try {
            return repository.dynamicSearchByMaintenanceType(maintenanceType);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteMaintenanceTypeById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
        repository.deleteById(id);
    }

    public MaintenanceTypeModel createMaintenanceType(MaintenanceTypeModel maintenanceType) {
        return repository.save(maintenanceType);
    }

    public void updateMaintenanceType(Long id, MaintenanceTypeModel maintenanceType) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The maintenance type id " + id +" was not found.");
        findOrThrow(id);
        repository.save(maintenanceType);
    }
}