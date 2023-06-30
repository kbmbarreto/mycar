package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ServiceDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.BadRequestException;
import com.lambdateam.mycar.model.ServiceModel;
import com.lambdateam.mycar.service.ServiceService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/service")
@PreAuthorize("isAuthenticated()")
@RedisHash("Service")
public class ServiceController {

    private final ServiceService serviceService;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceController.class);
    private ServiceDto convertToDto(ServiceModel model) { return mapper.map(model, ServiceDto.class); }
    private ServiceModel convertToModel(ServiceDto dto) { return mapper.map(dto, ServiceModel.class); }

    @GetMapping
    public List<ServiceDto> getServices(Pageable pageable) throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting service list - /service");
        try {
            int toSkip = pageable.getPageSize() *
                    pageable.getPageNumber();

            var servicesList = StreamSupport
                    .stream(serviceService.findAllServices().spliterator(), false)
                    .skip(toSkip).limit(pageable.getPageSize())
                    .collect(Collectors.toList());

            return servicesList
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("Token expired", e);
        }
    }

    @GetMapping(value = "/{id}")
    public ServiceDto getServiceById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting service by id - /service/{id}");
        try{
            return convertToDto(serviceService.findServiceById(id));
        } catch (ExpiredJwtException e) {
            throw new BadRequestException("Token expired", e);
        }
    }

    @GetMapping(value = "/withDetails")
    public List<ServiceModel> getAllServiceWithDetails() throws ExpiredJwtException {
        LOGGER.info("SL4J: Getting service list with details - /service/withDetails");
        return serviceService.findAllServicesWithDetails();
    }

    @PostMapping
    public ResponseEntity<ServiceDto> postService(@Valid @RequestBody ServiceDto serviceDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Creating service - /service");
        var model = convertToModel(serviceDto);
        var service = serviceService.createService(model);

        return new ResponseEntity(convertToDto(service), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putService(@PathVariable("id") Long id, @Valid @RequestBody ServiceDto serviceDto) throws ExpiredJwtException {
        LOGGER.info("SL4J: Updating service - /service/{id}");
        if(!id.equals(serviceDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var serviceModel = convertToModel(serviceDto);
        serviceService.updateService(id, serviceModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteServiceById(@PathVariable("id") Long id) throws ExpiredJwtException {
        LOGGER.info("SL4J: Deleting service by id - /service/{id}");
        serviceService.deleteServiceById(id);
        return HttpStatus.NO_CONTENT;
    }
}