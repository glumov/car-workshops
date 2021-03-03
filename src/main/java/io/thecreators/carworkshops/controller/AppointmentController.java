package io.thecreators.carworkshops.controller;

import io.thecreators.carworkshops.dto.AppointmentDto;
import io.thecreators.carworkshops.entity.Appointment;
import io.thecreators.carworkshops.entity.User;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping(value = "/appointment")
    ResponseEntity<List<Appointment>> findAll() {
        List<Appointment> appointments = appointmentService.findAll();
        return ResponseEntity.ok().body(appointments);
    }

    @GetMapping(value = "/appointment/{id}")
    ResponseEntity<Appointment> getById(@PathVariable("id") @Min(1) int id) {
        Appointment appointment = appointmentService.findById(id).orElseThrow(() -> new NotFoundException("No Appointment with ID : " + id));
        return ResponseEntity.ok().body(appointment);
    }

    @PostMapping(value = "/appointment")
    public ResponseEntity<?> create(@Valid @RequestBody AppointmentDto appointmentDto) {
        Appointment appointmentPersisted = appointmentService.save(appointmentDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(appointmentPersisted.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/appointment/{id}")
    public ResponseEntity<Appointment> update(@PathVariable("id") @Min(1) int id, @Valid AppointmentDto appointmentDto) {
        Appointment updateAppointment = appointmentService.update(id, appointmentDto);
        return ResponseEntity.ok().body(updateAppointment);
    }

    @PutMapping(value = "/appointment/{id}/update_date_time")
    public ResponseEntity<Appointment> updateDateTime(@PathVariable("id") @Min(1) int id, @RequestParam("dateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") String dateTime) {
        Appointment updateAppointment = appointmentService.update(id, dateTime);
        return ResponseEntity.ok().body(updateAppointment);
    }

    @DeleteMapping(value = "/appointment/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id) {
        appointmentService.delete(id);
        return ResponseEntity.ok().body("Appointment with ID : " + id + " deleted with success!");
    }


}
