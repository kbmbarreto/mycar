package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.MaintenanceTypeDto;
import com.lambdateam.mycar.model.MaintenanceTypeModel;
import com.lambdateam.mycar.service.MaintenanceTypeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.lambdateam.mycar.exception.ExpiredJwtException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/maintenanceType")
@PreAuthorize("isAuthenticated()")
@RedisHash("MaintenanceType")
public class MaintenanceTypeController {

    private final MaintenanceTypeService service;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenanceTypeController.class);
    private MaintenanceTypeDto convertToDto(MaintenanceTypeModel model) {
        return mapper.map(model, MaintenanceTypeDto.class);
    }
    private MaintenanceTypeModel convertToModel(MaintenanceTypeDto dto) {
        return mapper.map(dto, MaintenanceTypeModel.class);
    }

    @GetMapping
    public List<MaintenanceTypeDto> getMaintenanceType(Pageable pageable) throws ExpiredJwtException {
        int toSkip = pageable.getPageSize() *
                     pageable.getPageNumber();

        LOGGER.info("SL4J: Getting maintenance types list - /maintenanceType");

        var componentList = StreamSupport
                .stream(service.findAllMaintenanceTypes().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return componentList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public MaintenanceTypeDto getMaintennaceTypeById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting maintenance type by id - /maintenanceType/{id}");
        return convertToDto(service.findMaintenanceTypeById(id));
    }

    @GetMapping(value = "/dynamicSearchByMaintenanceType")
    public List<MaintenanceTypeDto> dynamicSearchByMaintenanceType(@RequestParam("maintenanceType") String maintenanceType) throws ExpiredJwtException {
        LOGGER.info("SL4J: Dynamic search by maintenance type - /maintenanceType/dynamicSearchByMaintenanceType");
        var componentList = service.dynamicSearchByMaintenanceType(maintenanceType);

        return componentList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<MaintenanceTypeDto> postMaintenanceType(@Valid @RequestBody MaintenanceTypeDto maintenanceTypeDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Creating maintenance type - /maintenanceType");
        var model = convertToModel(maintenanceTypeDto);
        var maintenanceType = service.createMaintenanceType(model);

        return new ResponseEntity(convertToDto(maintenanceType), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putMaintenanceType(@PathVariable("id") Long id, @Valid @RequestBody MaintenanceTypeDto maintenanceTypeDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Updating maintenance type - /maintenanceType/{id}");
        if(!id.equals(maintenanceTypeDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var maintenanceTypeModel = convertToModel(maintenanceTypeDto);
        service.updateMaintenanceType(id, maintenanceTypeModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteMaintenanceTypeById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Deleting maintenance type by id - /maintenanceType/{id}");
        service.deleteMaintenanceTypeById(id);
        return HttpStatus.NO_CONTENT;
    }
}