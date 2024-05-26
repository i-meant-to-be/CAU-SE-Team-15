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
    private List<String> tags;

    // For test (dummy data)
    public Issue() {}

    // If IssueType is given, set it as given value
    public Issue(
            String title,
            String description,
            IssueType type,
            UUID reporterId,
            List<String>tags
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
        this.tags = tags;
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

    public Issue(
            UUID id,
            String title,
            String description,
            IssueType type,
            IssueState state,
            LocalDateTime reportedDate,
            List<UUID> commentIds,
            UUID reporterId,
            UUID fixerId,
            UUID assigneeId
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.state = state;
        this.reportedDate = reportedDate;
        this.commentIds = commentIds;
        this.reporterId = reporterId;
        this.fixerId = fixerId;
        this.assigneeId = assigneeId;
    }

    public Issue copy(
            UUID id,
            String title,
            String description,
            IssueType type,
            IssueState state,
            LocalDateTime reportedDate,
            List<UUID> commentIds,
            UUID reporterId,
            UUID fixerId,
            UUID assigneeId
    ) {
        return new Issue(
                id != null ? id : this.id,
                title != null ? title : this.title,
                description != null ? description : this.description,
                type != null ? type : this.type,
                state != null ? state : this.state,
                reportedDate != null ? reportedDate : this.reportedDate,
                commentIds != null ? commentIds : this.commentIds,
                reporterId != null ? reporterId : this.reporterId,
                fixerId != null ? fixerId : this.fixerId,
                assigneeId != null ? assigneeId : this.assigneeId
        );
    }
}
