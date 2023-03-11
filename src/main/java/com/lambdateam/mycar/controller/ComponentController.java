package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ComponentDto;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.service.ComponentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/component")
public class ComponentController {

    private final ComponentService service;
    private final ModelMapper mapper;
    private ComponentDto convertToDto(ComponentModel model) {
        return mapper.map(model, ComponentDto.class);
    }
    private ComponentModel convertToModel(ComponentDto dto) {
        return mapper.map(dto, ComponentModel.class);
    }

    @GetMapping
    public List<ComponentDto> getComponents(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                     pageable.getPageNumber();
        
        var componentList = StreamSupport
                .stream(service.findAllComponents().spliterator(), false)
                .collect(Collectors.toList());

        return componentList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ComponentDto getComponentById(@PathVariable("id") Long id) {
        return convertToDto(service.findComponentById(id));
    }

    @PostMapping
    public ResponseEntity<ComponentDto> postComponent(@Valid @RequestBody ComponentDto componentDto) {
        var model = convertToModel(componentDto);
        var component = service.createComponent(model);

        return new ResponseEntity(convertToDto(component), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putComponent(@PathVariable("id") Long id, @Valid @RequestBody ComponentDto componentDto) {
        if(!id.equals(componentDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var componentModel = convertToModel(componentDto);
        service.updateComponent(id, componentModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteComponentById(@PathVariable("id") Long id) {
        service.deleteComponentById(id);
        return HttpStatus.NO_CONTENT;
    }
}