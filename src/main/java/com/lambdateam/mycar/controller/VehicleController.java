package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.VehicleDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.VehicleModel;
import com.lambdateam.mycar.service.VehicleService;
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
@RequestMapping(value = "/vehicle")
@PreAuthorize("isAuthenticated()")
@RedisHash("Vehicle")
public class VehicleController {

    private final VehicleService service;
    private final ModelMapper mapper;
    private final static Logger LOGGER = org.slf4j.LoggerFactory.getLogger(VehicleController.class);
    private VehicleDto convertToDto(VehicleModel model) {
        return mapper.map(model, VehicleDto.class);
    }
    private VehicleModel convertToModel(VehicleDto dto) {
        return mapper.map(dto, VehicleModel.class);
    }

    @GetMapping
    public List<VehicleDto> getVehicles(Pageable pageable) throws ExpiredJwtException, NotFoundException, ResponseStatusException {
        LOGGER.info("SL4J: Getting vehicle list - /vehicle");
        try{
            int toSkip = pageable.getPageSize() *
                    pageable.getPageNumber();

            var vehiclesList = StreamSupport
                    .stream(service.findAllVehicles().spliterator(), false)
                    .skip(toSkip).limit(pageable.getPageSize())
                    .collect(Collectors.toList());

            return vehiclesList
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
                    "No vehicles found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @GetMapping(value = "/{id}")
    public VehicleDto getVehicleById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Getting vehicle by id - /vehicle/{id}");
        try{
            return convertToDto(service.findVehicleById(id));
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @GetMapping(value = "/dynamicSearchByDescription")
    public List<VehicleDto> dynamicSearchByDescription(@RequestParam String description) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Dynamic search by description - /vehicle/dynamicSearchByDescription");
        try{
            var vehiclesList = StreamSupport
                    .stream(service.dynamicSearchByDescription(description).spliterator(), false)
                    .collect(Collectors.toList());

            return vehiclesList
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "No vehicles found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @PostMapping
    public ResponseEntity<VehicleDto> postVehicle(@Valid @RequestBody VehicleDto vehicleDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Posting vehicle - /vehicle");
        try{
            var model = convertToModel(vehicleDto);
            var vehicle = service.createVehicle(model);

            return new ResponseEntity(convertToDto(vehicle), HttpStatus.CREATED);
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @PutMapping(value = "/{id}")
    public void putVehicle(@PathVariable("id") Long id, @Valid @RequestBody VehicleDto vehicleDto) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Putting vehicle - /vehicle/{id}");
        try{
            if(!id.equals(vehicleDto.getId())) throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "id does not match."
            );

            var vehicleModel = convertToModel(vehicleDto);
            service.updateVehicle(id, vehicleModel);
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteVehicleById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Deleting vehicle by id - /vehicle/{id}");
        try{
            service.deleteVehicleById(id);
            return HttpStatus.NO_CONTENT;
        } catch (NotFoundException e) {
            throw new NotFoundException(
                    HttpStatus.NOT_FOUND,
                    "Vehicle not found."
            );
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(
                    HttpStatus.UNAUTHORIZED,
                    "Expired JWT token."
            );
        }
    }
}