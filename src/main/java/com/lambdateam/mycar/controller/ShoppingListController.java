package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.dto.ShoppingListDto;
import com.lambdateam.mycar.exception.ExpiredJwtException;
import com.lambdateam.mycar.exception.NotFoundException;
import com.lambdateam.mycar.model.ShoppingListModel;
import com.lambdateam.mycar.service.ShoppingListService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(ShoppingListController.class);

    private ShoppingListDto convertToDto(ShoppingListModel model) {
        return mapper.map(model, ShoppingListDto.class);
    }

    private ShoppingListModel convertToModel(ShoppingListDto dto) {
        return mapper.map(dto, ShoppingListModel.class);
    }

    @GetMapping
    public List<ShoppingListDto> getShoppingList(@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable) throws ExpiredJwtException, NotFoundException {

        LOGGER.info("SL4J: Getting shopping list - /shoppingList");

        try {
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
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Data not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

    @GetMapping(value = "/{id}")
    public ShoppingListDto getShoppingListItemById(@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
        LOGGER.info("SL4J: Getting shopping list item by id - /shoppingList/{id}");
        try {
            return convertToDto(service.findShoppingListItemById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Data not found", e);
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
        }
    }

        @GetMapping(value = "/withDetails")
        public List<ShoppingListModel> getAllShoppingListWithDetails () throws ExpiredJwtException, NotFoundException {
            LOGGER.info("SL4J: Getting shopping list with details - /shoppingList/withDetails");
            try{
                return service.findAllShoppingListWithDetails();
            } catch (NotFoundException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Data not found", e);
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
            }
        }

        @GetMapping(value = "/dynamicSearchByComponent")
        public List<ShoppingListModel> dynamicSearchByComponent (@RequestParam("component") String component) throws
        ExpiredJwtException, NotFoundException {
            LOGGER.info("SL4J: Dynamic search by component - /shoppingList/dynamicSearchByComponent");
            try{
                return service.dynamicSearchByComponent(component);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Data not found", e);
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
            }
        }

        @PostMapping
        public ResponseEntity<ShoppingListDto> postShoppingListItem (@Valid @RequestBody ShoppingListDto shoppingListDto) throws
        ExpiredJwtException {
            LOGGER.info("SL4J: Posting shopping list item - /shoppingList");
            try{
                var model = convertToModel(shoppingListDto);
                var shoppingListItem = service.createShoppingListItem(model);

                return new ResponseEntity(convertToDto(shoppingListItem), HttpStatus.CREATED);
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
            }
        }

        @PutMapping(value = "/{id}")
        public void putShoppingListItem (@PathVariable("id") Long id, @Valid @RequestBody ShoppingListDto
        shoppingListDto) throws ExpiredJwtException, NotFoundException {
            LOGGER.info("SL4J: Putting shopping list item - /shoppingList/{id}");
            try{
                if (!id.equals(shoppingListDto.getId())) throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "id does not match."
                );

                var shoppingListModel = convertToModel(shoppingListDto);
                service.updateShoppingListItem(id, shoppingListModel);
            } catch (NotFoundException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Data not found", e);
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
            }
        }

        @DeleteMapping(value = "/{id}")
        public HttpStatus deleteShoppingListItemById (@PathVariable("id") Long id) throws ExpiredJwtException, NotFoundException {
            LOGGER.info("SL4J: Deleting shopping list item by id - /shoppingList/{id}");
            try{
                service.deleteShoppingListItemById(id);
                return HttpStatus.NO_CONTENT;
            } catch (NotFoundException e) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Data not found", e);
            } catch (ExpiredJwtException e) {
                throw new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "JWT token has expired", e);
            }
        }
    }