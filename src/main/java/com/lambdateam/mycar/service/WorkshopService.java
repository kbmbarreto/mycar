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

    private WorkshopModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The workshop id " + id +" was not found.")
                );
    }

    public Iterable<WorkshopModel> findAllWorkshops() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public WorkshopModel findWorkshopById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
        return findOrThrow(id);
    }

    public Iterable<WorkshopModel> dynamicSearchByWorkshop(String workshop) throws NotFoundException {
        try{
            return repository.dynamicSearchByWorkshop(workshop);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void deleteWorkshopById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
        repository.deleteById(id);
    }

    public WorkshopModel createWorkshop(WorkshopModel workshop) {
        return repository.save(workshop);
    }

    public void updateWorkshop(Long id, WorkshopModel workshop) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
        findOrThrow(id);
        repository.save(workshop);
    }
}