package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "issue")  // users 컬렉션에 저장됨
public class Issue {
    @Id
    private UUID id;

    private String title;
    private String description;
    private IssueType type;
    private IssueState state;
    private LocalDateTime reportedDate;
    private List<UUID> commentIds;
    private UUID reporterId;
    private UUID fixerId;
    private UUID assigneeId;

    // For test (dummy data)
    public Issue() {}

    // If IssueType is given, set it as given value
    public Issue(
            String title,
            String description,
            IssueType type,
            UUID reporterId
    ) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.type = type;
        this.reporterId = reporterId;
        this.reportedDate = LocalDateTime.now();
        this.state = IssueState.NEW;
        this.assigneeId = null;
        this.fixerId = null;
        this.commentIds = new ArrayList<>();
    }

    // If IssueType isn't given, set it as a default value (MAJOR)
    public Issue(
            String title,
            String description,
            UUID reporterId
    ) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.type = IssueType.MAJOR;
        this.reporterId = reporterId;
        this.reportedDate = LocalDateTime.now();
        this.state = IssueState.NEW;
        this.assigneeId = null;
        this.fixerId = null;
        this.commentIds = new ArrayList<>();
    }
}
