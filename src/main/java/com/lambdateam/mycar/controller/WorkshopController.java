package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.WorkshopDto;
import com.lambdateam.mycar.model.WorkshopModel;
import com.lambdateam.mycar.service.WorkshopService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/workshop")
public class WorkshopController {

    private final WorkshopService service;
    private final ModelMapper mapper;
    private WorkshopDto convertToDto(WorkshopModel model) {
        return mapper.map(model, WorkshopDto.class);
    }
    private WorkshopModel convertToModel(WorkshopDto dto) {
        return mapper.map(dto, WorkshopModel.class);
    }

    @GetMapping
    public List<WorkshopDto> getWorkshops() {
        var workshopsList = StreamSupport
                .stream(service.findAllWorkshops().spliterator(), false)
                .collect(Collectors.toList());

        return workshopsList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public WorkshopDto getWorkshopById(@PathVariable("id") Long id) {
        return convertToDto(service.findWorkshopById(id));
    }

    @PostMapping
    public ResponseEntity<WorkshopDto> postWorkshop(@Valid @RequestBody WorkshopDto workshopDto) {
        var model = convertToModel(workshopDto);
        var workshop = service.createWorkshop(model);

        return new ResponseEntity(convertToDto(workshop), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putWorkshop(@PathVariable("id") Long id, @Valid @RequestBody WorkshopDto workshopDto) {
        if(!id.equals(workshopDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var workshopModel = convertToModel(workshopDto);
        service.updateWorkshop(id, workshopModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteWorkshopById(@PathVariable("id") Long id) {
        service.deleteWorkshopById(id);
        return HttpStatus.NO_CONTENT;
    }
}