package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.WorkshopModel;
import com.lambdateam.mycar.repository.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorkshopService {

    private final WorkshopRepository repository;

    private WorkshopModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The workshop id " + id +" was not found.")
                );
    }

    public Iterable<WorkshopModel> findAllWorkshops() {
        return repository.findAll();
    }

    public WorkshopModel findWorkshopById(Long id) {
        return findOrThrow(id);
    }

    public void deleteWorkshopById(Long id) {
        repository.deleteById(id);
    }

    public WorkshopModel createWorkshop(WorkshopModel workshop) {
        return repository.save(workshop);
    }

    public void updateWorkshop(Long id, WorkshopModel workshop) {
        findOrThrow(id);
        repository.save(workshop);
    }
}