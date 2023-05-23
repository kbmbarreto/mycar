package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.TechnicalVisitModel;
import com.lambdateam.mycar.repository.TechnicalVisitRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TechnicalVisitService {

    private final TechnicalVisitRepository repository;

    private TechnicalVisitModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The technical visit id " + id + " was not found.")
                );
    }

    public Iterable<TechnicalVisitModel> findAllTechnicalVisits() {
        return repository.findAll();
    }

    public TechnicalVisitModel findTechnicalVisitById(Long id) {
        return findOrThrow(id);
    }

    public void deleteTechnicalVisitById(Long id) {
        repository.deleteById(id);
    }

    public TechnicalVisitModel createTechnicalVisit(TechnicalVisitModel technicalVisit) {
        return repository.save(technicalVisit);
    }

    public void updateTechnicalVisit(Long id, TechnicalVisitModel technicalVisit) {
        findOrThrow(id);
        repository.save(technicalVisit);
    }
}