package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.TrafficTicketModel;
import com.lambdateam.mycar.repository.TrafficTicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TrafficTicketService {

    private final TrafficTicketRepository repository;

    private TrafficTicketModel findOrThrow(final Long id) throws NotFoundException {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The traffic ticket id " + id +" was not found.")
                );
    }

    public Iterable<TrafficTicketModel> findAllTrafficTickets() throws NotFoundException {
        try{
            return repository.findAll();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public List<TrafficTicketModel> findAllTrafficTicketsWithDetails() throws NotFoundException {
        try{
            return repository.getTrafficTicketWithDetails();
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public List<TrafficTicketModel> dynamicSearchByDescription(String description) throws NotFoundException {
        try{
            return repository.dynamicSearchByDescription(description);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public TrafficTicketModel findTrafficTicketById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
        return findOrThrow(id);
    }

    public void deleteTrafficTicketById(Long id) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
        repository.deleteById(id);
    }

    public TrafficTicketModel createTrafficTicket(TrafficTicketModel trafficTicket) throws NotFoundException {
        try{
            return repository.save(trafficTicket);
        } catch (NotFoundException e) {
            throw new NotFoundException("No results.");
        }
    }

    public void updateTrafficTicket(Long id, TrafficTicketModel trafficTicket) throws NotFoundException {
        if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
        findOrThrow(id);
        repository.save(trafficTicket);
    }
}