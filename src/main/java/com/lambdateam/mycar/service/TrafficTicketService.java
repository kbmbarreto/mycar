package com.lambdateam.mycar.service;

import com.lambdateam.mycar.exception.ExpiredJwtException;
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

    private TrafficTicketModel findOrThrow(final Long id) throws ExpiredJwtException {
        try{
            return repository
                    .findById(id)
                    .orElseThrow(
                            () -> new NotFoundException("The traffic ticket id " + id +" was not found.")
                    );
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public Iterable<TrafficTicketModel> findAllTrafficTickets() throws ExpiredJwtException {
        try{
            return repository.findAll();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public List<TrafficTicketModel> findAllTrafficTicketsWithDetails() throws ExpiredJwtException {
        try{
            return repository.getTrafficTicketWithDetails();
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public TrafficTicketModel findTrafficTicketById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
            return findOrThrow(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void deleteTrafficTicketById(Long id) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public TrafficTicketModel createTrafficTicket(TrafficTicketModel trafficTicket) throws ExpiredJwtException {
        try{
            return repository.save(trafficTicket);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }

    public void updateTrafficTicket(Long id, TrafficTicketModel trafficTicket) throws ExpiredJwtException {
        try{
            if(!repository.existsById(id)) throw new NotFoundException("The traffic ticket id " + id +" was not found.");
            findOrThrow(id);
            repository.save(trafficTicket);
        } catch (Exception e) {
            throw new ExpiredJwtException("You are not authorized to access this resource.");
        }
    }
}