package com.github.daaangt.deliveryapi.repositories;

import com.github.daaangt.deliveryapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByName(String name);
}
