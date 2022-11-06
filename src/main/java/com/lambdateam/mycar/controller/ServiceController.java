package com.lambdateam.mycar.controller;


import com.lambdateam.mycar.model.ServiceModel;
import com.lambdateam.mycar.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/service")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ServiceModel> getServiceById(@PathVariable(value = "id") Long id) {

        Optional<ServiceModel> serviceModel = serviceRepository.findById(id);

        return new ResponseEntity(serviceModel.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<ServiceModel>> listAllServices() {

        List<ServiceModel> list = (List<ServiceModel>) serviceRepository.findAll();

        return new ResponseEntity<List<ServiceModel>>(list, HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<ServiceModel> createService(@RequestBody ServiceModel serviceModel) {

        ServiceModel serviceSaved = serviceRepository.save(serviceModel);

        return new ResponseEntity<ServiceModel>(serviceSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ServiceModel> fullUpdateService(@RequestBody ServiceModel serviceModel) {

        ServiceModel serviceSaved = serviceRepository.save(serviceModel);

        return new ResponseEntity<ServiceModel>(serviceSaved, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ServiceModel incrementalUpdateService(@PathVariable("id") Long id, @RequestBody ServiceModel serviceModel) {

        ServiceModel foundService = getServiceById(id).getBody();
        assert foundService != null;
        foundService.setScheduling(Optional.ofNullable(serviceModel.getScheduling()).orElse(foundService.getScheduling()));
        foundService.setDescription(Optional.ofNullable(serviceModel.getDescription()).orElse(foundService.getDescription()));
        foundService.setOrderService(Optional.ofNullable(serviceModel.getOrderService()).orElse(foundService.getOrderService()));
        foundService.setVehicle(Optional.ofNullable(serviceModel.getVehicle()).orElse(foundService.getVehicle()));
        foundService.setWorkshop(Optional.ofNullable(serviceModel.getWorkshop()).orElse(foundService.getWorkshop()));

        return serviceRepository.save(foundService);
    }

    @DeleteMapping(value = "/{id}", produces = "application/text")
    public String deleteService(@PathVariable("id") Long id) {

        serviceRepository.deleteById(id);

        return "Successfully deleted";
    }
}
