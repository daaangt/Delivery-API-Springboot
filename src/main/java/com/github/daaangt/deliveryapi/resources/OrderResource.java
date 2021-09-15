package com.github.daaangt.deliveryapi.resources;

import com.github.daaangt.deliveryapi.entities.Order;
import com.github.daaangt.deliveryapi.repositories.OrderRepository;

import java.time.Instant;
import java.util.List;

import com.github.daaangt.deliveryapi.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {
    @Autowired
    private OrderRepository repository;

    @GetMapping
    public List<Order> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Order> store(@Valid @RequestBody Order obj) {
        repository.save(obj);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@PathVariable Long id, @Valid @RequestBody Order orderDetails) {
        return repository.findById(id)
                .map(record -> {
                    record.setPayment(orderDetails.getPayment());
                    record.setOrderStatus(orderDetails.getOrderStatus());
                    record.setClient(orderDetails.getClient());
                    record.setMoment(Instant.now());

                    return ResponseEntity.ok().body(repository.save(record));
                }).orElse(ResponseEntity.notFound().build());
    }
}
