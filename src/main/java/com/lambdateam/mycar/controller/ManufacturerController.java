package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.ManufacturerModel;
import com.lambdateam.mycar.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/manufacturer")
public class ManufacturerController {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ManufacturerModel> getManufacturerById(@PathVariable(value = "id") Long id) {

        Optional<ManufacturerModel> manufacturerModel = manufacturerRepository.findById(id);

        return new ResponseEntity(manufacturerModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ManufacturerModel>> listAllManufacturer() {

        List<ManufacturerModel> list = (List<ManufacturerModel>) manufacturerRepository.findAll();

        return new ResponseEntity<List<ManufacturerModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<ManufacturerModel> createManufacturer(@RequestBody ManufacturerModel manufacturerModel) {

        ManufacturerModel manufacturerSaved = manufacturerRepository.save(manufacturerModel);

        return new ResponseEntity<ManufacturerModel>(manufacturerSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ManufacturerModel> fullUpdateManufacturer(@RequestBody ManufacturerModel manufacturerModel) {

        ManufacturerModel manufacturerSaved = manufacturerRepository.save(manufacturerModel);

        return new ResponseEntity<ManufacturerModel>(manufacturerSaved, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteManufacturer(@PathVariable("id") Long id) {

        manufacturerRepository.deleteById(id);

        return "Successfully deleted";
    }
}
