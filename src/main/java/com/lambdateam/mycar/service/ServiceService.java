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

    private ServiceModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The service id " + id +" was not found.")
                );
    }

    public List<ServiceModel> findAllServicesWithDetails() {
        return repository.getServicesWithDetails();
    }

    public Iterable<ServiceModel> findAllServices() {
        return repository.findAll();
    }

    public ServiceModel findServiceById(Long id) {
        return findOrThrow(id);
    }

    public void deleteServiceById(Long id) {
        repository.deleteById(id);
    }

    public ServiceModel createService(ServiceModel service) {
        return repository.save(service);
    }

    public void updateService(Long id, ServiceModel service) {
        findOrThrow(id);
        repository.save(service);
    }
}