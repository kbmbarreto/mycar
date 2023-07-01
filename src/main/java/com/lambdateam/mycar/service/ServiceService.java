package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ServiceModel;
import com.lambdateam.mycar.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ServiceService {

    private final ServiceRepository repository;

    private ServiceModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The service id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<ServiceModel> findAllServicesWithDetails() throws ExpiredJwtException {
        try{
            return repository.getServicesWithDetails();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<ServiceModel> findAllServices() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<ServiceModel> dynamicSearchByDescription(String description) throws ExpiredJwtException {
        try{
            return repository.dynamicSearchByDescription(description);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ServiceModel findServiceById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteServiceById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public ServiceModel createService(ServiceModel service) throws ExpiredJwtException {
        try{
            return repository.save(service);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateService(Long id, ServiceModel service) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
            findOrThrow(id);
            repository.save(service);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}