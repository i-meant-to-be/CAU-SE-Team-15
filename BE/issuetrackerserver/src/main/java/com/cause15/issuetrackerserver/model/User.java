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
    private String name;

    private String password;
    private UserType type;

    // For test (dummy data)
    public User() {}

    // For actual use
    public User(String name, String password, UserType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public User(UUID id, String name, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }

    public User copy(UUID id, String name, String password, UserType type) {
        return new User(
                id != null ? id : this.id,
                name != null ? name : this.name,
                password != null ? password : this.password,
                type != null ? type : this.type
        );
    }
}
