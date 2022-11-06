package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.MaintenancesModel;
import com.lambdateam.mycar.repository.MaintenancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/maintenances")
public class MaintenancesController {

    @Autowired
    private MaintenancesRepository maintenancesRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MaintenancesModel> getMaintenanceById(@PathVariable(value = "id") Long id) {

        Optional<MaintenancesModel> maintenancesModel = maintenancesRepository.findById(id);

        return new ResponseEntity(maintenancesModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<MaintenancesModel>> listAllMaintenances() {

        List<MaintenancesModel> list = (List<MaintenancesModel>) maintenancesRepository.findAll();

        return new ResponseEntity<List<MaintenancesModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<MaintenancesModel> createMaintenance(@RequestBody MaintenancesModel maintenancesModel) {

        MaintenancesModel maintenancesSaved = maintenancesRepository.save(maintenancesModel);

        return new ResponseEntity<MaintenancesModel>(maintenancesSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<MaintenancesModel> fullUpdateMaintenance(@RequestBody MaintenancesModel maintenancesModel) {

        MaintenancesModel maintenancesSaved = maintenancesRepository.save(maintenancesModel);

        return new ResponseEntity<MaintenancesModel>(maintenancesSaved, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public MaintenancesModel incrementalUpdateMaintenance(@PathVariable("id") Long id, @RequestBody MaintenancesModel maintenancesModel) {

        MaintenancesModel foundMaintenance = getMaintenanceById(id).getBody();
        assert foundMaintenance != null;
        foundMaintenance.setMaintenanceDate(Optional.ofNullable(maintenancesModel.getMaintenanceDate()).orElse(foundMaintenance.getMaintenanceDate()));
        foundMaintenance.setVehicle(Optional.ofNullable(maintenancesModel.getVehicle()).orElse(foundMaintenance.getVehicle()));
        foundMaintenance.setAmount(Optional.ofNullable(maintenancesModel.getAmount()).orElse(foundMaintenance.getAmount()));
        foundMaintenance.setMaintenanceType(Optional.ofNullable(maintenancesModel.getMaintenanceType()).orElse(foundMaintenance.getMaintenanceType()));
        foundMaintenance.setManufacturer(Optional.ofNullable(maintenancesModel.getManufacturer()).orElse(foundMaintenance.getManufacturer()));
        foundMaintenance.setNextKm(Optional.ofNullable(maintenancesModel.getNextKm()).orElse(foundMaintenance.getNextKm()));

        return maintenancesRepository.save(foundMaintenance);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteMaintenance(@PathVariable("id") Long id) {

        maintenancesRepository.deleteById(id);

        return "Successfully deleted";
    }
}
