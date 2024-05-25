package Data;

import java.time.LocalDateTime;
import java.util.UUID;

public class Issue {
    private UUID id;
    private String title;
    private String reporter_id;
    private LocalDateTime reported_date;
    private String description;
    private UUID assignee_id;
    private IssueType type;
    private IssueState state;
    private UUID[] comments;

    public Issue(String title, String reporter_id, LocalDateTime reported_date, String description, UUID assignee_id, IssueType type, IssueState state, UUID[] comments) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.reporter_id = reporter_id;
        this.reported_date = reported_date;
        this.description = description;
        this.assignee_id = assignee_id;
        this.type = type;
        this.state = state;
        this.comments = comments;
    }

    // Getter 메소드들
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getReporterId() {
        return reporter_id;
    }

    public LocalDateTime getReportedDate() {
        return reported_date;
    }

    public String getDescription() {
        return description;
    }

    public UUID getAssigneeId() {
        return assignee_id;
    }

    public IssueType getType() {
        return type;
    }

    public IssueState getState() {
        return state;
    }

    public UUID[] getComments() {
        return comments;
    }
}
