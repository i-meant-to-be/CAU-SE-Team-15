package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Person {
    private UUID id;
    private String name;

    public Person() {
        this.id = UUID.randomUUID();
    }

    public Person(String name) {
        this.name = name;
        this.id = UUID.randomUUID();
    }

    public Person(UUID id, String name) {
        this.name = name;
        this.id = id;
    };
}
