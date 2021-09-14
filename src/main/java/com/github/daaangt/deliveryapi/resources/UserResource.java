package com.github.daaangt.deliveryapi.resources;

import com.github.daaangt.deliveryapi.entities.User;
import com.github.daaangt.deliveryapi.repositories.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<User> store(@Valid @RequestBody User obj) {
        repository.save(obj);

        System.out.println("A new User was created");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        System.out.println("An User was updated");
        return repository.findById(id)
                .map(record -> {
                    record.setAddress(userDetails.getAddress());
                    record.setEmail(userDetails.getEmail());
                    record.setName(userDetails.getName());
                    record.setPassword(userDetails.getPassword());

                    return ResponseEntity.ok().body(repository.save(record));
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public void destroy(@PathVariable Long id) {
        System.out.println("Deleted an User: ");
        repository.deleteById(id);
    }
}