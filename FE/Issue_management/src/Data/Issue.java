package Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private List<Comment> comments;

    public Issue(String title, String reporter_id, LocalDateTime reported_date, String description, UUID assignee_id, IssueType type, IssueState state) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.reporter_id = reporter_id;
        this.reported_date = reported_date;
        this.description = description;
        this.assignee_id = assignee_id;
        this.type = type;
        this.state = state;
        this.comments = new ArrayList<>();
    }

    // Getter 및 setter 메서드

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

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(String text) {
        this.comments.add(new Comment(text));
    }
}
