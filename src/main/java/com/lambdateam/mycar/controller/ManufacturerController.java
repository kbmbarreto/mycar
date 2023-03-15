package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ManufacturerDto;
import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.service.ManufacturerService;
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
@RequestMapping(value = "/manufacturer")
@PreAuthorize("isAuthenticated()")
public class ManufacturerController {

    private final ManufacturerService service;
    private final ModelMapper mapper;
    private ManufacturerDto convertToDto(ManufacturerModel model) { return mapper.map(model, ManufacturerDto.class); }
    private ManufacturerModel convertToModel(ManufacturerDto dto) { return mapper.map(dto, ManufacturerModel.class); }


    @GetMapping
    public List<ManufacturerDto> getManufacturers(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                pageable.getPageNumber();

        var manufacturersList = StreamSupport
                .stream(service.findAllManufacturers().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return manufacturersList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ManufacturerDto getManufacturerById(@PathVariable("id") Long id) {
        return convertToDto(service.findManufacturerById(id));
    }

    @PostMapping
    public ResponseEntity<ManufacturerDto> postManufacturer(@Valid @RequestBody ManufacturerDto manufacturerDto) {
        var model = convertToModel(manufacturerDto);
        var manufacturer = service.createManufacturer(model);

        return new ResponseEntity(convertToDto(manufacturer), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putManufacturer(@PathVariable("id") Long id, @Valid @RequestBody ManufacturerDto manufacturerDto) {
        if(!id.equals(manufacturerDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var manufacturerModel = convertToModel(manufacturerDto);
        service.updateManufacturer(id, manufacturerModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteManufacturerById(@PathVariable("id") Long id) {
        service.deleteManufacturerById(id);
        return HttpStatus.NO_CONTENT;
    }
}