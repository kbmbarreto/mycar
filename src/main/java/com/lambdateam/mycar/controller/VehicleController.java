package com.lambdateam.mycar.controller;

import com.lambdateam.mycar.model.VehicleModel;
import com.lambdateam.mycar.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/vehicle")
public class VehicleController {

    @Autowired
    private VehicleRepository vehicleRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<VehicleModel> getVehicleById(@PathVariable(value = "id") Long id) {

        Optional<VehicleModel> vehicleModel = vehicleRepository.findById(id);

        return new ResponseEntity(vehicleModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<VehicleModel>> listAllVehicles() {

        List<VehicleModel> list = (List<VehicleModel>) vehicleRepository.findAll();

        return new ResponseEntity<List<VehicleModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<VehicleModel> createVehicle(@RequestBody VehicleModel vehicleModel) {

        VehicleModel vehicleSaved = vehicleRepository.save(vehicleModel);

        return new ResponseEntity<VehicleModel>(vehicleSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<VehicleModel> fullUpdateVehicle(@RequestBody VehicleModel vehicleModel) {

        VehicleModel vehicleSaved = vehicleRepository.save(vehicleModel);

        return new ResponseEntity<VehicleModel>(vehicleSaved, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteVehicle(@PathVariable("id") Long id) {

        vehicleRepository.deleteById(id);

        return "Successfully deleted";
    }
}
