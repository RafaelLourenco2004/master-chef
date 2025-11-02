package com.example.demo.data.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
// @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;
    private String login;
    private String password;
    private String name;
    @OneToMany(mappedBy = "user", cascade = { CascadeType.REMOVE })
    private List<Recipe> recipes;

    public User(String login, String password, String name) {
        // this.userId = UUID.randomUUID();
        this.login = login;
        this.password = password;
        this.name = name;
    }
}