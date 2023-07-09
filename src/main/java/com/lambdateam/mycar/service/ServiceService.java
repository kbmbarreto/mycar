package com.lambdateam.mycar.service;

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

    private ServiceModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The service id " + id +" was not found.")
                );
    }

    public List<ServiceModel> findAllServicesWithDetails() throws NotFoundException {
        try{
            return repository.getServicesWithDetails();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public Iterable<ServiceModel> findAllServices() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public List<ServiceModel> dynamicSearchByDescription(String description) throws NotFoundException {
        try{
            return repository.dynamicSearchByDescription(description);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public ServiceModel findServiceById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
        return findOrThrow(id);
    }

    public void deleteServiceById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
        repository.deleteById(id);
    }

    public ServiceModel createService(ServiceModel service) throws NotFoundException {
        try{
            return repository.save(service);
        } catch (NotFoundException e) {
            throw new NotFoundException("You are not authorized to access this resource.");
        }
    }

    public void updateService(Long id, ServiceModel service) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The service id " + id +" was not found.");
        findOrThrow(id);
        repository.save(service);
    }
}