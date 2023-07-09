package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.MaintenancesDto;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.service.MaintenancesService;
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

import com.lambdateam.mycar.exception.ExpiredJwtException;;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/maintenances")
@PreAuthorize("isAuthenticated()")
@RedisHash("maintenances")
public class MaintenancesController {

    private final MaintenancesService service;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(MaintenancesController.class);
    private MaintenancesDto convertToDto(MaintenancesModel model) { return mapper.map(model, MaintenancesDto.class); }
    private MaintenancesModel convertToModel(MaintenancesDto dto) { return mapper.map(dto, MaintenancesModel.class); }

    @GetMapping
    public List<MaintenancesDto> getMaintenances(Pageable pageable) throws ExpiredJwtException, NotFoundException {
        try{
            int toSkip = pageable.getPageSize() *
                    pageable.getPageNumber();

            LOGGER.info("SL4J: Getting maintenances list - /maintenances");

            var maintenancesList = StreamSupport
                    .stream(service.findAllMaintenances().spliterator(), false)
                    .skip(toSkip).limit(pageable.getPageSize())
                    .collect(Collectors.toList());

            return maintenancesList
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenances not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @GetMapping(value = "/{id}")
    public MaintenancesDto getMaintenanceById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        try{
            LOGGER.info("SL4J: Getting maintenance item - /maintenances/{id}");
            return convertToDto(service.findMaintenanceById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @GetMapping(value = "/withDetails")
    public List<MaintenancesModel> getAllMaintenancesWithDetails() throws ExpiredJwtException, NotFoundException {
        try{
            LOGGER.info("SL4J: Getting maintenance item - /maintenances/withDetails");
            return service.findAllMaintenancesWithDetails();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @GetMapping(value = "/dynamicSearchByNextKm")
    public List<MaintenancesModel> dynamicSearchByNextKm(@RequestParam("nextKm1") String nextKm1, @RequestParam("nextKm2") String nextKm2) throws ExpiredJwtException, NotFoundException {
        try{
            LOGGER.info("SL4J: Getting maintenance item - /maintenances/dynamicSearchByNextKm");
            return service.dynamicSearchByNextKm(nextKm1, nextKm2);
        }  catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @GetMapping(value = "/dynamicSearchByComponent")
    public List<MaintenancesModel> dynamicSearchByComponent(@RequestParam("component") String component) throws ExpiredJwtException, NotFoundException {
        try{
            LOGGER.info("SL4J: Getting maintenance item - /maintenances/dynamicSearchByComponent");
            return service.dynamicSearchByComponent(component);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @PostMapping
    public ResponseEntity<MaintenancesDto> postMaintenance(@Valid @RequestBody MaintenancesDto maintenancesDto) throws ExpiredJwtException {
        try{
            var model = convertToModel(maintenancesDto);
            var maintenance = service.createMaintenance(model);

            LOGGER.info("SL4J: Creating maintenance item - /maintenances");

            return new ResponseEntity(convertToDto(maintenance), HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @PutMapping(value = "/{id}")
    public void putMaintenance(@PathVariable("id") Long id, @Valid @RequestBody MaintenancesDto maintenancesDto) throws ExpiredJwtException, NotFoundException {
        try{
            if(!id.equals(maintenancesDto.getId())) throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id does not match."
            );

            LOGGER.info("SL4J: Updating maintenance item - /maintenances/{id}");

            var maintenancesModel = convertToModel(maintenancesDto);
            service.updateMaintenance(id, maintenancesModel);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteMaintenanceById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        try{
            LOGGER.info("SL4J: Deleting maintenance item - /maintenances/{id}");
            service.deleteMaintenanceById(id);
            return HttpStatus.NO_CONTENT;
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Maintenance not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }
}