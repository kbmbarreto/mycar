package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.VehicleModel;
import com.lambdateam.mycar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository repository;

    private VehicleModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The vehicle id " + id +" was not found.")
                );
    }

    public Iterable<VehicleModel> findAllVehicles() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public VehicleModel findVehicleById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
        return findOrThrow(id);
    }

    public Iterable<VehicleModel> dynamicSearchByDescription(String description) throws NotFoundException {
        try{
            return repository.dynamicSearchByDescription(description);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteVehicleById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
    }

    public VehicleModel createVehicle(VehicleModel vehicle) {
        return repository.save(vehicle);
    }

    public void updateVehicle(Long id, VehicleModel vehicle) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
        findOrThrow(id);
        repository.save(vehicle);
    }
}