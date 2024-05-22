package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Comment {
    private UUID id;
    private String body;
    private UUID authorId;
    private LocalDateTime date;

    // For test (dummy data)
    public Comment() {}

    public Comment(String body, User author) {
        this.date = LocalDateTime.now();
        this.body = body.trim();
        this.authorId = author.getId();
    }
}
