package com.example.demo.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.data.entities.User;
import com.example.demo.data.interfaces.UserRepositoryI;

@Service
public class UserRepository {

    @Autowired
    private UserRepositoryI repository;

    public User createUser(User user) {
        return repository.save(user);
    }

    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }
}