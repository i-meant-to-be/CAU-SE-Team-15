package com.cause15.issuetrackerserver.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "issue")  // users 컬렉션에 저장됨
public class Issue {
    private UUID id;
    private String title;
    private String description;
    private IssueType type;
    private IssueState state;
    private LocalDateTime reportedDate;
    private Tester reporter;
    private Developer assignee;
    private Developer fixer;
    private Comment[] comments;

    // For test (dummy data)
    public Issue() {}

    // If IssueType is given, set it as given value
    public Issue(
            String title,
            String description,
            IssueType type,
            Tester reporter
    ) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.type = type;
        this.reporter = reporter;
        this.reportedDate = LocalDateTime.now();
    }

    // If IssueType isn't given, set it as a default value (MAJOR)
    public Issue(
            String title,
            String description,
            Tester reporter
    ) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.type = IssueType.MAJOR;
        this.reporter = reporter;
        this.reportedDate = LocalDateTime.now();
    }
}
