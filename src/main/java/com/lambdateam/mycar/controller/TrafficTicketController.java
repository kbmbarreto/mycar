package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.TrafficTicketDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.TrafficTicketModel;
import com.lambdateam.mycar.service.TrafficTicketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/trafficTicket")
@PreAuthorize("isAuthenticated()")
@RedisHash("TrafficTicket")
public class TrafficTicketController {

    private final TrafficTicketService service;
    private final ModelMapper mapper;
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(TrafficTicketController.class);
    private TrafficTicketDto convertToDto(TrafficTicketModel model) { return mapper.map(model, TrafficTicketDto.class); }
    private TrafficTicketModel convertToModel(TrafficTicketDto dto) { return mapper.map(dto, TrafficTicketModel.class); }

    @GetMapping
    public List<TrafficTicketDto> getTrafficTickets(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) throws ExpiredJwtException, ResponseStatusException, NotFoundException {
        LOGGER.info("SL4J: Getting traffic ticket list - /trafficTicket");

        try{
            int toSkip = pageable.getPageSize() *
                    pageable.getPageNumber();

            var trafficTicketsList = StreamSupport
                    .stream(service.findAllTrafficTickets().spliterator(), false)
                    .skip(toSkip).limit(pageable.getPageSize())
                    .collect(Collectors.toList());

            return trafficTicketsList
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (ResponseStatusException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid page number or page size."
            );
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "No traffic tickets found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @GetMapping(value = "/{id}")
    public TrafficTicketDto getTrafficTicketById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Getting traffic ticket by id - /trafficTicket/{id}");
        try{
            return convertToDto(service.findTrafficTicketById(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Traffic ticket not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @GetMapping(value = "/withDetails")
    public List<TrafficTicketModel> getAllTrafficTicketsWithDetails() throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Getting traffic ticket list with details - /trafficTicket/withDetails");
        try{
            return service.findAllTrafficTicketsWithDetails();
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "No traffic tickets found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @GetMapping(value = "/dynamicSearchByDescription")
    public List<TrafficTicketModel> dynamicSearchByDescription(@RequestParam("description") String description) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Getting traffic ticket list by description - /trafficTicket/dynamicSearchByDescription");
        try{
            return service.dynamicSearchByDescription(description);
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "No traffic tickets found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @PostMapping
    public ResponseEntity<TrafficTicketDto> postTrafficTicket(@Valid @RequestBody TrafficTicketDto trafficTicketDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Posting traffic ticket - /trafficTicket");
        try{
            var model = convertToModel(trafficTicketDto);
            var trafficTicket = service.createTrafficTicket(model);

            return new ResponseEntity(convertToDto(trafficTicket), HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @PutMapping(value = "/{id}")
    public void putTrafficTicket(@PathVariable("id") Long id, @Valid @RequestBody TrafficTicketDto trafficTicketDto) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Putting traffic ticket - /trafficTicket/{id}");
        try{
            if(!id.equals(trafficTicketDto.getId())) throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id does not match."
            );

            var trafficTicketModel = convertToModel(trafficTicketDto);
            service.updateTrafficTicket(id, trafficTicketModel);
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Traffic ticket not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteTrafficTicketById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Deleting traffic ticket by id - /trafficTicket/{id}");
        try{
            service.deleteTrafficTicketById(id);
            return HttpStatus.NO_CONTENT;
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Traffic ticket not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }
}