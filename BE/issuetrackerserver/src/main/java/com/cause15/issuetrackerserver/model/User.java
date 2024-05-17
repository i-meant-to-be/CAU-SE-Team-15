package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class User {
    private UUID id;
    private String name;
    private String password;

    // For actual use
    public User(String name, String password) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.password = password;
    }

    // For test, because normally id is set by random value, so we don't have to give a specific value
    public User(UUID id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    };
}
