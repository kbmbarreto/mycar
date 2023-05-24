package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.MaintenancesDto;
import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.service.MaintenancesService;
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
@RequestMapping(value = "/maintenances")
@PreAuthorize("isAuthenticated()")
public class MaintenancesController {

    private final MaintenancesService service;
    private final ModelMapper mapper;
    private MaintenancesDto convertToDto(MaintenancesModel model) { return mapper.map(model, MaintenancesDto.class); }
    private MaintenancesModel convertToModel(MaintenancesDto dto) { return mapper.map(dto, MaintenancesModel.class); }

    @GetMapping
    public List<MaintenancesDto> getMaintenances(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                pageable.getPageNumber();

        var maintenancesList = StreamSupport
                .stream(service.findAllMaintenances().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return maintenancesList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public MaintenancesDto getMaintenanceById(@PathVariable("id") Long id) {
        return convertToDto(service.findMaintenanceById(id));
    }

//    @GetMapping(value = "/withDetails")
//    public List<Object[]> getAllMaintenancesWithDetails() {
//        return service.findAllMaintenancesWithDetails();
//    }

    @PostMapping
    public ResponseEntity<MaintenancesDto> postMaintenance(@Valid @RequestBody MaintenancesDto maintenancesDto) {
        var model = convertToModel(maintenancesDto);
        var maintenance = service.createMaintenance(model);

        return new ResponseEntity(convertToDto(maintenance), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putMaintenance(@PathVariable("id") Long id, @Valid @RequestBody MaintenancesDto maintenancesDto) {
        if(!id.equals(maintenancesDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var maintenancesModel = convertToModel(maintenancesDto);
        service.updateMaintenance(id, maintenancesModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteMaintenanceById(@PathVariable("id") Long id) {
        service.deleteMaintenanceById(id);
        return HttpStatus.NO_CONTENT;
    }
}