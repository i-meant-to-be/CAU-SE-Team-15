package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "project")
public class Project {
    private UUID id;
    private List<UUID> issueIds;
    private List<UUID> userIds;
    private String title;
    private String description;
    private LocalDateTime createdDate;

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.createdDate = LocalDateTime.now();
    }

}
