package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.VehicleModel;
import com.lambdateam.mycar.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VehicleService {

    private final VehicleRepository repository;

    private VehicleModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The vehicle id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<VehicleModel> findAllVehicles() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public VehicleModel findVehicleById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteVehicleById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public VehicleModel createVehicle(VehicleModel vehicle) throws ExpiredJwtException {
        try{
            return repository.save(vehicle);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateVehicle(Long id, VehicleModel vehicle) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The vehicle id " + id +" was not found.");
            findOrThrow(id);
            repository.save(vehicle);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}