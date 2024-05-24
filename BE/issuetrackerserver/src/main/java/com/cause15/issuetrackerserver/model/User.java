package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@Document(collection = "user")  // users 컬렉션에 저장됨
public class User {
    @Id
    private UUID id;

    @Indexed(unique = true)
    private String username;

    private String password;
    private UserType type;

    // For test (dummy data)
    public User() {}

    // For actual use
    public User(String username, String password, UserType type) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.type = type;
    }

    // For test, because normally id is set by random value, so we don't have to give a specific value
    public User(UUID id, String username, String password, UserType type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.type = type;
    }
}
