package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Project() {}

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.createdDate = LocalDateTime.now();
        this.userIds = new ArrayList<>();
        this.issueIds = new ArrayList<>();
    }

    public Project(String title, String description, List<UUID> userIds) {
        this.title = title;
        this.description = description;
        this.id = UUID.randomUUID();
        this.createdDate = LocalDateTime.now();
        this.userIds = userIds != null ? userIds : new ArrayList<>();
        this.issueIds = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, createdDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id) &&
                Objects.equals(title, project.title) &&
                Objects.equals(description, project.description) &&
                Objects.equals(createdDate, project.createdDate);
    }
}
