package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ComponentDto;
import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.service.ComponentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lambdateam.mycar.exception.ExpiredJwtException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/component")
@PreAuthorize("isAuthenticated()")
@RedisHash("Component")
public class ComponentController {

    private final ComponentService service;
    private final ModelMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentController.class);
    private ComponentDto convertToDto(ComponentModel model) {
        return mapper.map(model, ComponentDto.class);
    }
    private ComponentModel convertToModel(ComponentDto dto) {
        return mapper.map(dto, ComponentModel.class);
    }

    @GetMapping
    public List<ComponentDto> getComponents(Pageable pageable) throws ExpiredJwtException {
        int toSkip = pageable.getPageSize() *
                     pageable.getPageNumber();

        LOGGER.info("SL4J: Getting component list - /component");

        var componentList = StreamSupport
                .stream(service.findAllComponents().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return componentList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ComponentDto getComponentById(@PathVariable("id") Long id) throws ExpiredJwtException {

        LOGGER.info("SL4J: Getting component item - /component/{id}");

        return convertToDto(service.findComponentById(id));
    }

    @GetMapping(value = "/dynamicSearchByComponent")
    public ResponseEntity<List<ComponentDto>> dynamicSearchByComponent(@RequestParam String component) throws ExpiredJwtException {
        var componentList = service.dynamicSearchByComponent(component);

        LOGGER.info("SL4J: Dynamic search by component - /component/dynamicSearchByComponent");

        return new ResponseEntity(componentList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ComponentDto> postComponent(@Valid @RequestBody ComponentDto componentDto) throws ExpiredJwtException {
        var model = convertToModel(componentDto);
        var component = service.createComponent(model);

        LOGGER.info("SL4J: Post component - /component");

        return new ResponseEntity(convertToDto(component), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putComponent(@PathVariable("id") Long id, @Valid @RequestBody ComponentDto componentDto) throws ExpiredJwtException {
        if(!id.equals(componentDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        LOGGER.info("SL4J: Updating component - /component/{id}");

        var componentModel = convertToModel(componentDto);
        service.updateComponent(id, componentModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteComponentById(@PathVariable("id") Long id) {

        LOGGER.info("SL4J: Deleting component - /component/{id}");

        service.deleteComponentById(id);
        return HttpStatus.NO_CONTENT;
    }
}