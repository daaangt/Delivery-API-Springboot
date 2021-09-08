package com.github.daaangt.deliveryapi.repositories;

import com.github.daaangt.deliveryapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
