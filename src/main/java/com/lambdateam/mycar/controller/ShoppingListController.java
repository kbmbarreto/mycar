package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ShoppingListDto;
import com.lambdateam.mycar.model.ShoppingListModel;
import com.lambdateam.mycar.service.ShoppingListService;
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
@RequestMapping(value = "/shoppingList")
@PreAuthorize("isAuthenticated()")
public class ShoppingListController {

    private final ShoppingListService service;
    private final ModelMapper mapper;
    private ShoppingListDto convertToDto(ShoppingListModel model) {
        return mapper.map(model, ShoppingListDto.class);
    }
    private ShoppingListModel convertToModel(ShoppingListDto dto) {
        return mapper.map(dto, ShoppingListModel.class);
    }

    @GetMapping
    public List<ShoppingListDto> getShoppingList(Pageable pageable) {
        int toSkip = pageable.getPageSize() *
                pageable.getPageNumber();

        var shoppingList = StreamSupport
                .stream(service.findAllShoppingListItems().spliterator(), false)
                .skip(toSkip).limit(pageable.getPageSize())
                .collect(Collectors.toList());

        return shoppingList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public ShoppingListDto getShoppingListItemById(@PathVariable("id") Long id) {
        return convertToDto(service.findShoppingListItemById(id));
    }

    @PostMapping
    public ResponseEntity<ShoppingListDto> postShoppingListItem(@Valid @RequestBody ShoppingListDto shoppingListDto) {
        var model = convertToModel(shoppingListDto);
        var shoppingListItem = service.createShoppingListItem(model);

        return new ResponseEntity(convertToDto(shoppingListItem), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public void putShoppingListItem(@PathVariable("id") Long id, @Valid @RequestBody ShoppingListDto shoppingListDto) {
        if(!id.equals(shoppingListDto.getId())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "id does not match."
        );

        var shoppingListModel = convertToModel(shoppingListDto);
        service.updateShoppingListItem(id, shoppingListModel);
    }

    @DeleteMapping(value = "/{id}")
    public HttpStatus deleteShoppingListItemById(@PathVariable("id") Long id) {
        service.deleteShoppingListItemById(id);
        return HttpStatus.NO_CONTENT;
    }
}