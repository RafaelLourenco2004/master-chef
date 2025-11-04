package com.example.demo.auth.services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.auth.entities.Session;
import com.example.demo.auth.exceptions.InvalidAuthenticationTokenException;
import com.example.demo.data.repositories.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repository;

    public void createSession(String token) {
        Session session = new Session(token);
        repository.createSession(session);
    }

    public Session getSession(String token) throws InvalidAuthenticationTokenException {
        Optional<Session> session = repository.getSession(token);
        if (!session.isPresent())
            throw new InvalidAuthenticationTokenException("Token de autentificação invalido");
        return session.get();
    }

    public boolean hasSessionExpired(Session session) {
        LocalDateTime createdAt = session.getCreatedAt();
        Duration diff = Duration.between(LocalDateTime.now(), createdAt);
        if (diff.toMinutes() >= 2) {
            deleteSession(session.getToken());
            return true;
        }
        return false;
    }

    private void deleteSession(String token) {
        repository.deleteSession(null);
    }
}
