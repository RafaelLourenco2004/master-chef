package com.example.demo.data.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.entities.Session;
import com.example.demo.data.interfaces.AuthRepositoryI;

@Service
public class AuthRepository {
    
    @Autowired
    private AuthRepositoryI repository;

    public Session createSession(Session session){
        return repository.save(session);
    }

    public void deleteSession(Session session){
        repository.delete(session);
    }
}
