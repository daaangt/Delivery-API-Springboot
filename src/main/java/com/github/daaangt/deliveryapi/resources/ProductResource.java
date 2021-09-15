package com.github.daaangt.deliveryapi.resources;

import com.github.daaangt.deliveryapi.entities.Order;
import com.github.daaangt.deliveryapi.entities.Product;

import java.time.Instant;
import java.util.List;

import com.github.daaangt.deliveryapi.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> store(@Valid @RequestBody Product obj) {
        repository.save(obj);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/name/{name}")
    public Product findByName(@PathVariable String name) {
        return repository.findByName(name);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody Product productDetails) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(productDetails.getName());
                    record.setPrice(productDetails.getPrice());
                    record.setStock(productDetails.getStock());

                    return ResponseEntity.ok().body(repository.save(record));
                }).orElse(ResponseEntity.notFound().build());
    }
}