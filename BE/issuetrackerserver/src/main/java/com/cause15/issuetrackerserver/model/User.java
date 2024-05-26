package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    private Set<String> tags;
    // For test (dummy data)
    public User() {}

    // For actual use
    public User(String name, String password, UserType type) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public User(UUID id, String name, String password, UserType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
        this.tags = new HashSet<>();
    }

    public User copy(UUID id, String name, String password, UserType type) {
        return new User(
                id != null ? id : this.id,
                name != null ? name : this.name,
                password != null ? password : this.password,
                type != null ? type : this.type
        );
    }
    public void updateTag(List<String>issueTags){
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        this.tags.addAll(issueTags);
    }
    public float calculateJaccard(Issue issue) {
        Set<String> unionSet = new HashSet<>(this.getTags());
        unionSet.addAll(issue.getTags());
        int union = unionSet.size();

        Set<String> intersectionSet = new HashSet<>(this.getTags());
        intersectionSet.retainAll(issue.getTags());
        int inter = intersectionSet.size();

        return (float) inter / union;
    }
}
