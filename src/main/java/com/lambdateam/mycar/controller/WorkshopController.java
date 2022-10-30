package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.WorkshopModel;
import com.lambdateam.mycar.repository.WorkshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/workshop")
public class WorkshopController {

    @Autowired
    private WorkshopRepository workshopRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<WorkshopModel> getWorkshopById(@PathVariable(value = "id") Long id) {

        Optional<WorkshopModel> workshopModel = workshopRepository.findById(id);

        return new ResponseEntity(workshopModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<WorkshopModel>> listAllWorkshops() {

        List<WorkshopModel> list = (List<WorkshopModel>) workshopRepository.findAll();

        return new ResponseEntity<List<WorkshopModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<WorkshopModel> createWorkshop(@RequestBody WorkshopModel workshopModel) {

        WorkshopModel workshopSaved = workshopRepository.save(workshopModel);

        return new ResponseEntity<WorkshopModel>(workshopSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<WorkshopModel> fullUpdateWorkshop(@RequestBody WorkshopModel workshopModel) {

        WorkshopModel workshopSaved = workshopRepository.save(workshopModel);

        return new ResponseEntity<WorkshopModel>(workshopSaved, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public WorkshopModel incrementalUpdateWorkshop(@PathVariable("id") Long id, @RequestBody WorkshopModel workshopModel) {

        WorkshopModel foundWorkshop = getWorkshopById(id).getBody();
        assert foundWorkshop != null;
        foundWorkshop.setWorkshop(Optional.ofNullable(workshopModel.getWorkshop()).orElse(foundWorkshop.getWorkshop()));
        foundWorkshop.setContact(Optional.ofNullable(workshopModel.getContact()).orElse(foundWorkshop.getContact()));

        return workshopRepository.save(foundWorkshop);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteWorkshop(@PathVariable("id") Long id) {

        workshopRepository.deleteById(id);

        return "Successfully deleted";
    }
}