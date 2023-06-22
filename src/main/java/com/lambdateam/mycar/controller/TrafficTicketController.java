package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.TrafficTicketDto;
import com.lambdateam.mycar.model.TrafficTicketModel;
import com.lambdateam.mycar.service.TrafficTicketService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
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
public class TrafficTicketController {

    private final TrafficTicketService service;
    private final ModelMapper mapper;
    private TrafficTicketDto convertToDto(TrafficTicketModel model) { return mapper.map(model, TrafficTicketDto.class); }
    private TrafficTicketModel convertToModel(TrafficTicketDto dto) { return mapper.map(dto, TrafficTicketModel.class); }

    @GetMapping
    public List<TrafficTicketDto> getTrafficTickets(Pageable pageable) {
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
    }

    @GetMapping(value = "/{id}")
    public TrafficTicketDto getTrafficTicketById(@PathVariable("id") Long id) {
        return convertToDto(service.findTrafficTicketById(id));
    }

    @GetMapping(value = "/withDetails")
    public List<TrafficTicketModel> getAllTrafficTicketsWithDetails() {
        return service.findAllTrafficTicketsWithDetails();
    }

    @PostMapping
    public ResponseEntity<TrafficTicketDto> postTrafficTicket(@Valid @RequestBody TrafficTicketDto trafficTicketDto) {
        var model = convertToModel(trafficTicketDto);
        var trafficTicket = service.createTrafficTicket(model);

        return new ResponseEntity(convertToDto(trafficTicket), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putTrafficTicket(@PathVariable("id") Long id, @Valid @RequestBody TrafficTicketDto trafficTicketDto) {
        if(!id.equals(trafficTicketDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var trafficTicketModel = convertToModel(trafficTicketDto);
        service.updateTrafficTicket(id, trafficTicketModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteTrafficTicketById(@PathVariable("id") Long id) {
        service.deleteTrafficTicketById(id);
        return HttpStatus.NO_CONTENT;
    }
}