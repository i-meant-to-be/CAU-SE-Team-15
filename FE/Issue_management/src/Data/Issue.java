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

    public String getTitle(){return title;}
}