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

    public Comment(String body, UUID authorId) {
        this.id = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.body = body.trim();
        this.authorId = authorId;
    }

    public Comment(UUID id, String body, UUID authorId, LocalDateTime date) {
        this.id = id;
        this.body = body.trim();
        this.authorId = authorId;
        this.date = date;
    }

    public Comment copy(
            UUID id,
            String body,
            UUID authorId,
            LocalDateTime date
    ) {
        return new Comment(
                id != null ? id : this.id,
                body != null ? body : this.body,
                authorId != null ? authorId : this.authorId,
                date != null ? date : this.date
        );
    }
}
