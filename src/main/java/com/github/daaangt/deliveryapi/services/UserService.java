package com.github.daaangt.deliveryapi.services;

import com.github.daaangt.deliveryapi.entities.User;
import com.github.daaangt.deliveryapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.get();
    }

    public User store(User obj) {
        return repository.save(obj);
    }

    public User update(Long id, User userDetails) {
        User updateUser = findById(id);

        updateUser.setAddress(userDetails.getAddress());
        updateUser.setEmail(userDetails.getEmail());
        updateUser.setName(userDetails.getName());
        updateUser.setPassword(userDetails.getPassword());

        repository.save(updateUser);

        return updateUser;
    }

    public void destroy(Long id) {
        repository.deleteById(id);
    }
}
