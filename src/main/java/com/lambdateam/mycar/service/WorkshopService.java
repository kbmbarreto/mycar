package com.lambdateam.mycar.service;

import com.lambdateam.mycar.dto.WorkshopDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.WorkshopModel;
import com.lambdateam.mycar.repository.WorkshopRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WorkshopService {

    private final WorkshopRepository repository;

    private WorkshopModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The workshop id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<WorkshopModel> findAllWorkshops() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public WorkshopModel findWorkshopById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<WorkshopModel> dynamicSearchByWorkshop(String workshop) throws ExpiredJwtException {
        try{
            return repository.dynamicSearchByWorkshop(workshop);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteWorkshopById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public WorkshopModel createWorkshop(WorkshopModel workshop) throws ExpiredJwtException {
        try{
            return repository.save(workshop);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateWorkshop(Long id, WorkshopModel workshop) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The workshop id " + id +" was not found.");
            findOrThrow(id);
            repository.save(workshop);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}