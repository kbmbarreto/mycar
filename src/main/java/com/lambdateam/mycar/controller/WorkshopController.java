package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.WorkshopDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.model.WorkshopModel;
import com.lambdateam.mycar.service.WorkshopService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisHash;
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
@RequestMapping(value = "/workshop")
@PreAuthorize("isAuthenticated()")
@RedisHash("Workshop")
public class WorkshopController {

    private final WorkshopService service;
    private final ModelMapper mapper;
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(WorkshopController.class);
    private WorkshopDto convertToDto(WorkshopModel model) {
        return mapper.map(model, WorkshopDto.class);
    }
    private WorkshopModel convertToModel(WorkshopDto dto) {
        return mapper.map(dto, WorkshopModel.class);
    }

    @GetMapping
    public List<WorkshopDto> getWorkshops(Pageable pageable) throws ExpiredJwtException {

        LOGGER.info("SL4J: Getting workshop list - /workshop");

        int toSkip = pageable.getPageSize() *
                pageable.getPageNumber();

        var workshopsList = StreamSupport
                .stream(service.findAllWorkshops().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return workshopsList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public WorkshopDto getWorkshopById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting workshop by id - /workshop/{id}");
        return convertToDto(service.findWorkshopById(id));
    }

    @GetMapping(value = "/dynamicSearchByWorkshop")
    public List<WorkshopDto> dynamicSearchByWorkshop(@RequestParam("workshop") String workshop) throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting workshop by workshop - /workshop/dynamicSearchByWorkshop");
        return StreamSupport
                .stream(service.dynamicSearchByWorkshop(workshop).spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<WorkshopDto> postWorkshop(@Valid @RequestBody WorkshopDto workshopDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Posting workshop - /workshop");
        var model = convertToModel(workshopDto);
        var workshop = service.createWorkshop(model);

        return new ResponseEntity(convertToDto(workshop), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putWorkshop(@PathVariable("id") Long id, @Valid @RequestBody WorkshopDto workshopDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Putting workshop - /workshop/{id}");
        if(!id.equals(workshopDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var workshopModel = convertToModel(workshopDto);
        service.updateWorkshop(id, workshopModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteWorkshopById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Deleting workshop by id - /workshop/{id}");
        service.deleteWorkshopById(id);
        return HttpStatus.NO_CONTENT;
    }
}