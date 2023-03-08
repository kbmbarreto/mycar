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

    private VehicleModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The vehicle id " + id +" was not found.")
                );
    }

    public Iterable<VehicleModel> findAllVehicles() {
        return repository.findAll();
    }

    public VehicleModel findVehicleById(Long id) {
        return findOrThrow(id);
    }

    public void deleteVehicleById(Long id) {
        repository.deleteById(id);
    }

    public VehicleModel createVehicle(VehicleModel vehicle) {
        return repository.save(vehicle);
    }

    public void updateVehicle(Long id, VehicleModel vehicle) {
        findOrThrow(id);
        repository.save(vehicle);
    }
}