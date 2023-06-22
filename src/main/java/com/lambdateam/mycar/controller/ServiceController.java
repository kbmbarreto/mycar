package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ServiceDto;
import com.lambdateam.mycar.model.ServiceModel;
import com.lambdateam.mycar.service.ServiceService;
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
@RequestMapping(value = "/service")
@PreAuthorize("isAuthenticated()")
public class ServiceController {

    private final ServiceService serviceService;
    private final ModelMapper mapper;
    private ServiceDto convertToDto(ServiceModel model) { return mapper.map(model, ServiceDto.class); }
    private ServiceModel convertToModel(ServiceDto dto) { return mapper.map(dto, ServiceModel.class); }

    @GetMapping
    public List<ServiceDto> getServices(Pageable pageable) {
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
    }

    @GetMapping(value = "/{id}")
    public ServiceDto getServiceById(@PathVariable("id") Long id) {
        return convertToDto(serviceService.findServiceById(id));
    }

    @GetMapping(value = "/withDetails")
    public List<ServiceModel> getAllServiceWithDetails() {
        return serviceService.findAllServicesWithDetails();
    }

    @PostMapping
    public ResponseEntity<ServiceDto> postService(@Valid @RequestBody ServiceDto serviceDto) {
        var model = convertToModel(serviceDto);
        var service = serviceService.createService(model);

        return new ResponseEntity(convertToDto(service), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putService(@PathVariable("id") Long id, @Valid @RequestBody ServiceDto serviceDto) {
        if(!id.equals(serviceDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var serviceModel = convertToModel(serviceDto);
        serviceService.updateService(id, serviceModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteServiceById(@PathVariable("id") Long id) {
        serviceService.deleteServiceById(id);
        return HttpStatus.NO_CONTENT;
    }

//    @PatchMapping(value = "/{id}", produces = "application/json")
//    public ServiceModel incrementalUpdateService(@PathVariable("id") Long id, @RequestBody ServiceModel serviceModel) {
//
//        ServiceModel foundService = getServiceById(id).getBody();
//        assert foundService != null;
//        foundService.setScheduling(Optional.ofNullable(serviceModel.getScheduling()).orElse(foundService.getScheduling()));
//        foundService.setDescription(Optional.ofNullable(serviceModel.getDescription()).orElse(foundService.getDescription()));
//        foundService.setOrderService(Optional.ofNullable(serviceModel.getOrderService()).orElse(foundService.getOrderService()));
//        foundService.setVehicle(Optional.ofNullable(serviceModel.getVehicle()).orElse(foundService.getVehicle()));
//        foundService.setWorkshop(Optional.ofNullable(serviceModel.getWorkshop()).orElse(foundService.getWorkshop()));
//
//        return serviceRepository.save(foundService);
//    }
}
