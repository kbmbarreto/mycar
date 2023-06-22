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

    private TrafficTicketModel findOrThrow(final Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new NotFoundException("The traffic ticket id " + id +" was not found.")
                );
    }

    public Iterable<TrafficTicketModel> findAllTrafficTickets() {
        return repository.findAll();
    }

    public List<TrafficTicketModel> findAllTrafficTicketsWithDetails() {
        return repository.getTrafficTicketWithDetails();
    }

    public TrafficTicketModel findTrafficTicketById(Long id) {
        return findOrThrow(id);
    }

    public void deleteTrafficTicketById(Long id) {
        repository.deleteById(id);
    }

    public TrafficTicketModel createTrafficTicket(TrafficTicketModel trafficTicket) {
        return repository.save(trafficTicket);
    }

    public void updateTrafficTicket(Long id, TrafficTicketModel trafficTicket) {
        findOrThrow(id);
        repository.save(trafficTicket);
    }
}