package io.thecreators.carworkshops.controller;

import io.thecreators.carworkshops.dto.UserDto;
import io.thecreators.carworkshops.entity.User;
import io.thecreators.carworkshops.exception.NotFoundException;
import io.thecreators.carworkshops.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user/{id}")
    ResponseEntity<User> getById(@PathVariable("id") @Min(1) int id) {
        User user = userService.findById(id).orElseThrow(()->new NotFoundException("No User with ID : "+id));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping(value = "/user")
    ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> create(@Valid @RequestBody UserDto userDto) {
        User userPersisted = userService.save(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userPersisted.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") @Min(1) int id){
        userService.delete(id);
        return ResponseEntity.ok().body("User with ID : "+id+" deleted with success!");
    }

}
