package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.TechnicalVisitDto;
import com.lambdateam.mycar.model.TechnicalVisitModel;
import com.lambdateam.mycar.service.TechnicalVisitService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/technicalVisit")
@PreAuthorize("isAuthenticated()")
public class TechnicalVisitController {

    private final TechnicalVisitService service;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(TechnicalVisitController.class);
    private TechnicalVisitDto convertToDto(TechnicalVisitModel model) {
        return mapper.map(model, TechnicalVisitDto.class);
    }
    private TechnicalVisitModel convertToModel(TechnicalVisitDto dto) {
        return mapper.map(dto, TechnicalVisitModel.class);
    }

    @GetMapping
    public List<TechnicalVisitDto> getTechnicalVisit(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                     pageable.getPageNumber();

        LOGGER.info("SL4J: Getting technical visits list - getTechnicalVisits()");

        var technicalVisitsList = StreamSupport
                .stream(service.findAllTechnicalVisits().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return technicalVisitsList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public TechnicalVisitDto getTechnicalVisitById(@PathVariable("id") Long id) {
        return convertToDto(service.findTechnicalVisitById(id));
    }

    @PostMapping
    public ResponseEntity<TechnicalVisitDto> postTechnicalVisit(@Valid @RequestBody TechnicalVisitDto technicalVisitDto) {
        var model = convertToModel(technicalVisitDto);
        var technicalVisit = service.createTechnicalVisit(model);

        return new ResponseEntity(convertToDto(technicalVisit), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putTechnicalVisit(@PathVariable("id") Long id, @Valid @RequestBody TechnicalVisitDto technicalVisitDto) {
        if (!id.equals(technicalVisitDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "id does not match."
        );

        var technicalVisitModel = convertToModel((technicalVisitDto));
        service.updateTechnicalVisit(id, technicalVisitModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteTechnicalVisitById(@PathVariable("id") Long id) {
        service.deleteTechnicalVisitById(id);
        return HttpStatus.NO_CONTENT;
    }
}