package io.thecreators.carworkshops.controller;

import io.thecreators.carworkshops.dto.CarWorkshopDto;
import io.thecreators.carworkshops.entity.CarWorkshop;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.service.CarWorkshopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarWorkshopController {

    public final CarWorkshopService carWorkshopService;

    public CarWorkshopController(CarWorkshopService carWorkshopService) {
        this.carWorkshopService = carWorkshopService;
    }

    @GetMapping(value = "/car_workshop/{id}")
    ResponseEntity<CarWorkshop> getById(@PathVariable("id") @Min(1) int id) {
        CarWorkshop carWorkshop = carWorkshopService.findById(id).orElseThrow(()->new NotFoundException("No CarWorkshop with ID : "+id));
        return ResponseEntity.ok().body(carWorkshop);
    }

    @GetMapping(value = "/car_workshop/{city}")
    ResponseEntity<List<CarWorkshop>> findByCity(@PathVariable("city") @NotBlank String city) {
        List<CarWorkshop> carWorkshops = carWorkshopService.findByCity(city);
        return ResponseEntity.ok().body(carWorkshops);
    }

    @PostMapping(value = "/car_workshop")
    public ResponseEntity<?> create(@Valid @RequestBody CarWorkshopDto carWorkshopDto) {
        CarWorkshop carWorkshopPersisted = carWorkshopService.save(carWorkshopDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(carWorkshopPersisted.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/car_workshop/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id){
        carWorkshopService.delete(id);
        return ResponseEntity.ok().body("CarWorkshop with ID : "+id+" deleted with success!");
    }


}
