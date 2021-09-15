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
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/name/{name}")
    public User findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @Valid @RequestBody User userDetails) {
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
        repository.deleteById(id);
    }
}