package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class Comment {
    private UUID id;
    private String text;
    private User author;
    private LocalDateTime date;

    // For test (dummy data)
    public Comment() {}

    public Comment(String text, User author) {
        this.date = LocalDateTime.now();
        this.text = text.trim();
        this.author = author;
    }
}
