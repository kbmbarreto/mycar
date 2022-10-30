package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.ComponentModel;
import com.lambdateam.mycar.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/component")
public class ComponentController {

    @Autowired
    private ComponentRepository componentRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ComponentModel> getComponentById(@PathVariable(value = "id") Long id) {

        Optional<ComponentModel> componentModel = componentRepository.findById(id);

        return new ResponseEntity(componentModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ComponentModel>> listAllComponents() {

        List<ComponentModel> list = (List<ComponentModel>) componentRepository.findAll();

        return new ResponseEntity<List<ComponentModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<ComponentModel> createComponent(@RequestBody ComponentModel componentModel) {

        ComponentModel componentSaved = componentRepository.save(componentModel);

        return new ResponseEntity<ComponentModel>(componentSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ComponentModel> fullUpdateComponent(@RequestBody ComponentModel componentModel) {

        ComponentModel componentSaved = componentRepository.save(componentModel);

        return new ResponseEntity<ComponentModel>(componentSaved, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteComponent(@PathVariable("id") Long id) {

        componentRepository.deleteById(id);

        return "Successfully deleted";
    }
}
